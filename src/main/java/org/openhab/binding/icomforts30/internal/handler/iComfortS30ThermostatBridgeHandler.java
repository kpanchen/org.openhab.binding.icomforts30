/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.openhab.binding.icomforts30.internal.handler;

import static org.openhab.binding.icomforts30.internal.iComfortS30BindingConstants.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.openhab.binding.icomforts30.internal.RunnableWithTimeout;
import org.openhab.binding.icomforts30.internal.api.iComfortS30ApiClient;
import org.openhab.binding.icomforts30.internal.api.iComfortS30SSLUtil;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.FANMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.HUMIDMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.HVACMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.PeriodExceptionType;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.PeriodExpirationMode;
import org.openhab.binding.icomforts30.internal.api.models.response.System;
import org.openhab.binding.icomforts30.internal.api.models.response.ZoneList;
import org.openhab.binding.icomforts30.internal.configuration.iComfortS30BridgeConfiguration;
import org.openhab.binding.icomforts30.internal.exceptions.iComfortS30ConnectionFailedException;
import org.openhab.binding.icomforts30.internal.iComfortS30BindingConstants;
import org.openhab.core.config.core.Configuration;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseBridgeHandler;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link iComfortS30ThermostatBridgeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Konstantin Panchenko - Initial contribution
 */
@NonNullByDefault
public class iComfortS30ThermostatBridgeHandler extends BaseBridgeHandler {

    private final Logger logger = LoggerFactory.getLogger(iComfortS30ThermostatBridgeHandler.class);

    private @NonNullByDefault({}) HttpClient httpClient;
    private @NonNullByDefault({}) iComfortS30BridgeConfiguration configuration = null;
    private @NonNullByDefault({}) iComfortS30ApiClient apiClient = null;

    protected @Nullable ScheduledFuture<?> refreshTask;
    protected @Nullable ScheduledFuture<?> messagePump;

    public iComfortS30ThermostatBridgeHandler(Bridge bridge) {
        super(bridge);
        // this.httpClient = httpClient;
    }

    @Override
    public void initialize() {
        logger.debug("Initialize {} Version {}", FrameworkUtil.getBundle(getClass()).getSymbolicName(),
                FrameworkUtil.getBundle(getClass()).getVersion());
        configuration = getConfigAs(iComfortS30BridgeConfiguration.class);

        updateStatus(ThingStatus.UNKNOWN);

        if (checkConfig()) {

            if (apiClient == null) {
                SslContextFactory sslContextFactory;

                if (iComfortS30BridgeConfiguration.HTTPS.equals(configuration.getProtocol())) {
                    // Getting SSL Context Factory for local HTTPS connection
                    try {
                        sslContextFactory = new iComfortS30SSLUtil(configuration.getHostname()).getSslContextFactory();
                    } catch (iComfortS30ConnectionFailedException e) {
                        logger.debug("Failed to configure SSL connection");
                        this.updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.OFFLINE.CONFIGURATION_ERROR,
                                "@text/offline.conf-error-ssl");
                        return;
                    }
                } else {
                    this.updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.OFFLINE.CONFIGURATION_ERROR,
                            "@text/offline.conf-error-https");
                    return;
                }

                this.httpClient = new HttpClient(sslContextFactory);

                // Set Application ID and store it in configuration
                if (configuration.getApplicationID() == "") {
                    logger.debug("Generating unique APP ID");
                    String epochTime = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
                    String appPrefix = iComfortS30BindingConstants.APPLICATION_ID.substring(0, epochTime.length());
                    Configuration editedConfig = editConfiguration();
                    editedConfig.put(iComfortS30BridgeConfiguration.THERMOSTAT_CONFIG_APP_ID, appPrefix + epochTime);
                    updateConfiguration(editedConfig);
                    logger.debug("Unique APP ID generated: " + configuration.getApplicationID());
                }

                try {
                    apiClient = new iComfortS30ApiClient(configuration, this.httpClient,
                            configuration.getApplicationID());
                    // apiClient.setTimeout(5000);
                } catch (Exception e) {
                    logger.error("Could not start API client", e);
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                            "Could not create iComfortS30 API client");
                }
                if (apiClient != null) {
                    // Initialization can take a while, so kick it off on a separate thread
                    scheduler.schedule(() -> {
                        if (apiClient.login()) {
                            startMessagePump();
                            startRefreshTask();
                        } else {
                            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                                    "Authentication failed");
                        }
                    }, 0, TimeUnit.SECONDS);
                }
            }

        }

        // These logging types should be primarily used by bindings
        // logger.trace("Example trace message");
        // logger.debug("Example debug message");
        // logger.warn("Example warn message");

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    @Override
    public void dispose() {
        disposeMessagePump();
        disposeRefreshTask();
        disposeApiClient();
        // listeners.clear();
        logger.debug("Thermostat Bridge: Disposing");
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (ZONE_TEMPERATURE_CHANNEL.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {
                // TODO: handle data refresh
            }

            // TODO: handle command

            // Note: if communication with thing fails for some reason,
            // indicate that by setting the status with detail information:
            // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
            // "Could not control device at IP address x.x.x.x");
        }
    }

    private boolean checkConfig() {
        String errorMessage = "";

        if (configuration.getLocalConnection() == true) {
            if (StringUtils.isEmpty(configuration.getHostname())) {
                errorMessage = "Thermostat Hostname or IP address is not provided";
            } else {
                return true;
            }
        } else {
            errorMessage = "Only local connections supported";
        }

        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, errorMessage);
        return false;
    }

    private void startMessagePump() {
        disposeMessagePump();

        messagePump = scheduler.scheduleWithFixedDelay(this::messagePump, 0, configuration.getPumpInterval(),
                TimeUnit.SECONDS);
    }

    private void messagePump() {
        try {
            apiClient.messagePump();
        } catch (Exception e) {
            logger.debug("Error occured in the message pump, logging and ignoring: ", e);
        }
    }

    private void startRefreshTask() {
        disposeRefreshTask();

        refreshTask = scheduler.scheduleWithFixedDelay(this::update, configuration.getRefreshDelay(),
                configuration.getPollingInterval(), TimeUnit.SECONDS);
    }

    private void update() {
        try {
            // apiClient.update(); //Replaced by Message Pump

            // iComfortS30ApiClient apiClient2 = apiClient; #Testing!
            updateThings();
            updateStatus(ThingStatus.ONLINE);
            setDeviceProperties(apiClient.getSystemsInfo());
        } catch (NullPointerException e) {
            updateStatus(ThingStatus.OFFLINE);
            logger.debug("Failed to update system status, probably system is not ready yet");

        } catch (Exception e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
            logger.debug("Failed to update system status {}", e.getMessage());
        }
    }

    private void setDeviceProperties(System systemInfo) {
        Map<String, String> properties = editProperties();
        if (systemInfo.getConfig() != null) {
            properties.put(iComfortS30BindingConstants.TCS_PROPERTY_SYSTEM_NAME, systemInfo.getConfig().name);
            properties.put(iComfortS30BindingConstants.TCS_PROPERTY_PRODUCT_TYPE,
                    systemInfo.getConfig().options.productType);
            properties.put(iComfortS30BindingConstants.TCS_PROPERTY_LANGUAGE, systemInfo.getConfig().language);
        }
        // properties.put(iComfortWiFiBindingConstants.TCS_PROPERTY_GATEWAY_SN, systemInfo.gatewaySN);
        // properties.put(iComfortWiFiBindingConstants.TCS_PROPERTY_FIRMWARE_VERSION, systemInfo.firmwareVersion);
        updateProperties(properties);
    }

    private void disposeRefreshTask() {
        ScheduledFuture<?> rT = refreshTask;
        if (rT != null) {
            rT.cancel(true);
            refreshTask = rT;
        }
    }

    private void disposeMessagePump() {
        ScheduledFuture<?> mP = messagePump;
        if (mP != null) {
            mP.cancel(true);
            messagePump = mP;
        }
    }

    private void disposeApiClient() {
        if (apiClient != null) {
            // apiClient.logout();
        }
        apiClient = null;
    }

    private void updateThings() {
        for (Thing thing : getThing().getThings()) {
            ThingHandler thingHandler = thing.getHandler();

            if (thingHandler instanceof iComfortS30HeatingZoneHandler) {
                iComfortS30HeatingZoneHandler zoneHandler = (iComfortS30HeatingZoneHandler) thingHandler;
                zoneHandler.update(apiClient.getZone(zoneHandler.getId()), apiClient.getSystemsInfo());
            } else if (thingHandler instanceof iComfortS30SystemHandler) {
                iComfortS30SystemHandler systemHandler = (iComfortS30SystemHandler) thingHandler;
                systemHandler.update(apiClient.getSystemsInfo(), apiClient.getOccupancyInfo());
            }

        }
    }

    public void setSystemManualAwayMode(Boolean awayMode) {
        tryToCall(() -> apiClient.setSystemManualAwayMode(awayMode));
        updateThings();
    }

    public void setZoneOperationMode(ZoneList heatingZone, HVACMode hvacMode, Integer scheduleID) {
        tryToCall(() -> apiClient.setZoneOperationMode(heatingZone, hvacMode, scheduleID));
        updateThings();
    }

    public void setZoneFanMode(ZoneList heatingZone, FANMode fanMode, Integer scheduleID) {
        tryToCall(() -> apiClient.setZoneFanMode(heatingZone, fanMode, scheduleID));
        updateThings();
    }

    public void setZoneSchedule(ZoneList heatingZone, Integer scheduleId) {
        tryToCall(() -> apiClient.setSchedule(heatingZone, scheduleId));
        updateThings();
    }

    public void setSchedulePeriod(ZoneList heatingZone, Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF,
            Float spC, Integer husp, Integer desp, Integer scheduleId) {
        tryToCall(() -> apiClient.setSchedulePeriod(heatingZone, hspF, cspC, hspC, cspF, spF, spC, husp, desp,
                scheduleId));
        updateThings();
    }

    public void setScheduleOverridePeriod(ZoneList heatingZone, Integer hspF, Float cspC, Float hspC, Integer cspF,
            Integer spF, Float spC, Integer husp, Integer desp, HUMIDMode humidityMode, HVACMode systemMode,
            Integer startTime, FANMode fanMode, Integer scheduleId) {
        tryToCall(() -> apiClient.setScheduleOverridePeriod(heatingZone, hspF, cspC, hspC, cspF, spF, spC, husp, desp,
                humidityMode, systemMode, startTime, fanMode, scheduleId));
        updateThings();
    }

    public void setScheduleHold(ZoneList heatingZone, PeriodExceptionType exceptionType, Boolean scheduleHold,
            String expiresOn, PeriodExpirationMode expirationMode, Integer scheduleId) {
        tryToCall(() -> apiClient.setScheduleHold(heatingZone, exceptionType, scheduleHold, expiresOn, expirationMode,
                scheduleId));
        updateThings();
    }

    private void tryToCall(RunnableWithTimeout action) {
        try {
            action.run();
        } catch (TimeoutException e) {
            updateBridgeStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                    "Timeout on executing request");
        }
    }

    // Not used yet
    // private void updateBridgeStatus(ThingStatus newStatus) {
    // updateBridgeStatus(newStatus, ThingStatusDetail.NONE, null);
    // }

    private void updateBridgeStatus(ThingStatus newStatus, ThingStatusDetail detail, @Nullable String message) {
        // Prevent spamming the log file
        if (!newStatus.equals(getThing().getStatus())) {
            updateStatus(newStatus, detail, message);
            // updateListeners(newStatus);
        }
    }
}
