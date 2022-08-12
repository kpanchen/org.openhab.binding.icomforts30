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

package org.openhab.binding.icomforts30.internal.api;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.FANMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.HUMIDMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.HVACMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.PeriodExceptionType;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.PeriodExpirationMode;
import org.openhab.binding.icomforts30.internal.api.models.iComfortS30System;
import org.openhab.binding.icomforts30.internal.api.models.request.AdditionalParameters;
import org.openhab.binding.icomforts30.internal.api.models.request.CommandData;
import org.openhab.binding.icomforts30.internal.api.models.request.RequestData;
import org.openhab.binding.icomforts30.internal.api.models.request.RetrieveData;
import org.openhab.binding.icomforts30.internal.api.models.request.SetFanModeRequest;
import org.openhab.binding.icomforts30.internal.api.models.request.SetHVACModeRequest;
import org.openhab.binding.icomforts30.internal.api.models.request.SetManualAwayRequest;
import org.openhab.binding.icomforts30.internal.api.models.request.SetScheduleHoldRequest;
import org.openhab.binding.icomforts30.internal.api.models.request.SetSchedulePeriodRequest;
import org.openhab.binding.icomforts30.internal.api.models.request.SetSchedulePeriodSetPointRequest;
import org.openhab.binding.icomforts30.internal.api.models.request.SetScheduleRequest;
import org.openhab.binding.icomforts30.internal.api.models.response.Messages;
import org.openhab.binding.icomforts30.internal.api.models.response.Occupancy;
import org.openhab.binding.icomforts30.internal.api.models.response.System;
import org.openhab.binding.icomforts30.internal.api.models.response.ZoneList;
import org.openhab.binding.icomforts30.internal.configuration.iComfortS30BridgeConfiguration;
import org.openhab.binding.icomforts30.internal.exceptions.iComfortS30ApiClientException;
import org.openhab.binding.icomforts30.internal.iComfortS30BindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the iComfortS30 client api
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class iComfortS30ApiClient {

    // Connection URL constants
    // private final String LOCAL_URL_AUTHENTICATE = null;
    private final String LOCAL_URL_LOGIN = "%s://%s:%s/Endpoints/%s/Connect";
    // private final String LOCAL_URL_NEGOTIATE = null;
    private final String LOCAL_URL_RETRIEVE = "%s://%s:%s/Messages/%s/Retrieve";
    private final String LOCAL_URL_REQUESTDATA = "%s://%s:%s/Messages/%s/RequestData";
    private final String LOCAL_URL_PUBLISH = "%s://%s:%s/Messages/%s/Publish";
    private final String LOCAL_URL_LOGOUT = "%s://%s:%s/Endpoints/%s/Disconnect";

    private final Logger logger = LoggerFactory.getLogger(iComfortS30ApiClient.class);
    private final HttpClient httpClient;
    private final iComfortS30BridgeConfiguration configuration;
    private final ApiAccess apiAccess;
    private final String applicationID;

    // Connection URLs
    // private final String urlAuthenticate;
    private final String urlLogin;
    // private final String urlNegotiate;
    private final String urlRetrieve;
    private final String urlRequestdata;
    private final String urlPublish;
    private final String urlLogout;

    private iComfortS30System system = new iComfortS30System();

    /**
     * Creates a new API client based on the V1 API interface
     *
     * @param configuration The configuration of the account to use
     * @param httpClient SSL Enabled HTTP Client
     * @throws Exception
     */
    public iComfortS30ApiClient(iComfortS30BridgeConfiguration configuration, HttpClient httpClient) throws Exception {
        this(configuration, httpClient, null);
    }

    /**
     * Creates a new API client based on the V1 API interface
     *
     * @param configuration The configuration of the account to use
     * @param httpClient SSL Enabled HTTP Client
     * @param appID Application ID to use.
     * @throws Exception
     */
    public iComfortS30ApiClient(iComfortS30BridgeConfiguration configuration, HttpClient httpClient, String appID)
            throws Exception {
        logger.debug("Initialising API Client");
        if (appID == null) {
            logger.info("Generating temporary unique APP ID");
            String epochTime = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
            String appPrefix = iComfortS30BindingConstants.APPLICATION_ID.substring(0, epochTime.length());
            this.applicationID = appPrefix + epochTime;
            logger.info("Unique APP ID generated: " + this.applicationID);
        } else {
            this.applicationID = appID;
            logger.info("Used pre-generated APP ID: " + this.applicationID);
        }

        this.configuration = configuration;
        this.httpClient = httpClient;
        this.system.setSysID(configuration.getLennoxSystemName());

        // Creating connection URLs
        // urlAuthenticate = LOCAL_URL_AUTHENTICATE; // Not used for local connection
        urlLogin = String.format(LOCAL_URL_LOGIN, this.configuration.getProtocol(), this.configuration.getHostname(),
                this.configuration.getPort(), this.applicationID);
        // urlNegotiate = LOCAL_URL_NEGOTIATE; // Not used for local connection
        urlRetrieve = String.format(LOCAL_URL_RETRIEVE, this.configuration.getProtocol(),
                this.configuration.getHostname(), this.configuration.getPort(), this.applicationID);
        urlRequestdata = String.format(LOCAL_URL_REQUESTDATA, this.configuration.getProtocol(),
                this.configuration.getHostname(), this.configuration.getPort(), this.applicationID);
        urlPublish = String.format(LOCAL_URL_PUBLISH, this.configuration.getProtocol(),
                this.configuration.getHostname(), this.configuration.getPort(), this.applicationID);
        urlLogout = String.format(LOCAL_URL_LOGOUT, this.configuration.getProtocol(), this.configuration.getHostname(),
                this.configuration.getPort(), this.applicationID);

        try {
            httpClient.start();
        } catch (Exception e) {
            logger.error("Could not start http client", e);
            throw new iComfortS30ApiClientException("Could not start http client", e);
        }

        apiAccess = new ApiAccess(httpClient);
    }

    /**
     * Closes the current connection to the API
     */
    public void close() {
        // apiAccess.setUserCredentials(null);
        // ownerProfileInfo = null;
        // buildingsInfo = null;
        // systemsInfo = null;

        if (httpClient.isStarted()) {
            try {
                httpClient.stop();
            } catch (Exception e) {
                logger.error("Could not stop http client.", e);
            }
        }
    }

    // Initial talk to iComfort S30 API service
    public boolean login() {

        // return true in case of success or false if failed;
        try {
            Boolean loginResult = apiAccess.doUnAuthenticatedPost(urlLogin, null, Boolean.class);
            if (loginResult != null) {
                if (loginResult == true) {
                    Boolean requestResult = requestDataHelper(system);
                    if (requestResult != null) {
                        if (requestResult == true) {
                            return true;
                        } else {
                            throw new Exception("Data request attempt was unsuccessful");
                        }
                    } else {
                        throw new Exception("Fatal error during data request attempt");
                    }
                } else {
                    throw new Exception("Logon attempt was unsuccessful");
                }
            } else {
                throw new Exception("Fatal error during logon attempt");
            }
        } catch (Exception e) {
            logger.error("Error: ", e);
        }

        return false;
    }

    public void logout() {
        try {
            Boolean logoutResult = apiAccess.doUnAuthenticatedPost(urlLogout, null, Boolean.class);
            if (logoutResult != null) {
                if (logoutResult == true) {
                    logger.info("Successfully logged off from Lennox S30 thermostat");
                } else {
                    throw new Exception("Failed to log off from Lennox S30 thermostat");
                }
            } else {
                throw new Exception("Fatal error during logout attempt from Lennox S30 thermostat");
            }

        } catch (TimeoutException e) {
            logger.error("Timeout exception during log out: ", e.getMessage());
        } catch (Exception e) {
            logger.error("Logout error: ", e.getMessage());
        }
        close();
    }

    public void messagePump() throws Exception {
        try {
            // Request time out set one seconds less then pump interval
            apiAccess.setTimeout((configuration.getPumpInterval() - 1) * 1000);
            // Requesting new message
            Messages out = apiAccess.doAuthenticatedGet(urlRetrieve, null, new RetrieveData(), Messages.class);
            if (out == null) {
                // Request haven't returned any data
                // Possibly add relogon here
                return;
            }

            for (int i = 0; i < out.getNumberOfMessages(); i++) {
                if (out.getMessage(i).data == null) {
                    // Message has no data, go to the next message
                    continue;
                }
                // Setting returned message into system object
                if (out.getMessage(i).getData().getSystem() != null) {
                    if (out.getMessage(i).getData().getSystem().getClock() != null) {
                        if (system.getSystem().getClock() == null) {
                            system.getSystem().setClock(out.getMessage(i).getData().getSystem().getClock());
                        } else {
                            updateIfNotNull(system.getSystem().getClock(),
                                    out.getMessage(i).getData().getSystem().getClock());
                        }
                    }
                    if (out.getMessage(i).getData().getSystem().getTime() != null) {
                        if (system.getSystem().getTime() == null) {
                            system.getSystem().setTime(out.getMessage(i).getData().getSystem().getTime());
                        } else {
                            updateIfNotNull(system.getSystem().getTime(),
                                    out.getMessage(i).getData().getSystem().getTime());
                        }
                    }
                    if (out.getMessage(i).getData().getSystem().getStatus() != null) {
                        if (system.getSystem().getStatus() == null) {
                            system.getSystem().setStatus(out.getMessage(i).getData().getSystem().getStatus());
                        } else {
                            updateIfNotNull(system.getSystem().getStatus(),
                                    out.getMessage(i).getData().getSystem().getStatus());
                        }
                    }
                    if (out.getMessage(i).getData().getSystem().getRegistration() != null) {
                        if (system.getSystem().getRegistration() == null) {
                            system.getSystem()
                                    .setRegistration(out.getMessage(i).getData().getSystem().getRegistration());
                        } else {
                            updateIfNotNull(system.getSystem().getRegistration(),
                                    out.getMessage(i).getData().getSystem().getRegistration());
                        }
                    }
                    if (out.getMessage(i).getData().getSystem().getTest() != null) {
                        if (system.getSystem().getTest() == null) {
                            system.getSystem().setTest(out.getMessage(i).getData().getSystem().getTest());
                        } else {
                            updateIfNotNull(system.getSystem().getTest(),
                                    out.getMessage(i).getData().getSystem().getTest());
                        }

                    }
                    if (out.getMessage(i).getData().getSystem().getConfig() != null) {
                        if (system.getSystem().getConfig() == null) {
                            system.getSystem().setConfig(out.getMessage(i).getData().getSystem().getConfig());
                        } else {
                            updateIfNotNull(system.getSystem().getConfig(),
                                    out.getMessage(i).getData().getSystem().getConfig());
                        }

                    }
                    if (out.getMessage(i).getData().getSystem().getPorts() != null) {
                        if (system.getSystem().getPorts() == null) {
                            system.getSystem().setPorts(out.getMessage(i).getData().getSystem().getPorts());
                        } else {
                            updateIfNotNull(system.getSystem().getPorts(),
                                    out.getMessage(i).getData().getSystem().getPorts());
                        }

                    }
                    if (out.getMessage(i).getData().getSystem().getPublisher() != null) {
                        if (system.getSystem().getPublisher() == null) {
                            system.getSystem().setPublisher(out.getMessage(i).getData().getSystem().getPublisher());
                        } else {
                            updateIfNotNull(system.getSystem().getPublisher(),
                                    out.getMessage(i).getData().getSystem().getPublisher());
                        }
                    }
                    if (out.getMessage(i).getData().getSystem().getInternalStatus() != null) {
                        if (system.getSystem().getInternalStatus() == null) {
                            system.getSystem()
                                    .setInternalStatus(out.getMessage(i).getData().getSystem().getInternalStatus());
                        } else {
                            updateIfNotNull(system.getSystem().getInternalStatus(),
                                    out.getMessage(i).getData().getSystem().getInternalStatus());
                        }

                    }

                } else if (out.getMessage(i).getData().getZones() != null) {
                    for (int z = 0; z < out.getMessage(i).getData().getNumberOfZones(); z++) {
                        for (ZoneList zl : out.getMessage(i).getData().getZones()) {
                            if (zl.getId() == z) {
                                if (system.zoneExist(z)) {
                                    if (zl.getPublisher() != null) {
                                        updateIfNotNull(system.getZone(z).getPublisher(), zl.getPublisher());
                                    }
                                    if (zl.getStatus() != null) {

                                        updateIfNotNull(system.getZone(z).getStatus(), zl.getStatus());
                                    }
                                    if (zl.getConfig() != null) {

                                        updateIfNotNull(system.getZone(z).getConfig(), zl.getConfig());
                                    }
                                    if (zl.getSchedule() != null) {

                                        updateIfNotNull(system.getZone(z).getSchedule(), zl.getSchedule());
                                    }
                                    if (zl.getSensors() != null) {

                                        updateIfNotNull(system.getZone(z).getSensors(), zl.getSensors());
                                    }
                                    if (zl.getInputs() != null) {

                                        updateIfNotNull(system.getZone(z).getInputs(), zl.getInputs());
                                    }
                                } else {
                                    system.setZone(z, zl);
                                }
                                break;
                            }
                        }

                    }
                } else if (out.getMessage(i).getData().getDevices() != null) {
                    if (system.getDevices().isEmpty()) {
                        system.setDevices(out.getMessage(i).getData().getDevices());
                    } else {
                        updateIfNotNull(system.getDevices(), out.getMessage(i).getData().getDevices());
                    }

                } else if (out.getMessage(i).getData().getInterfaces() != null) {
                    if (system.getInterfaces().isEmpty()) {
                        system.setInterfaces(out.getMessage(i).getData().getInterfaces());
                    } else {
                        updateIfNotNull(system.getInterfaces(), out.getMessage(i).getData().getInterfaces());
                    }

                } else if (out.getMessage(i).getData().getOccupancy() != null) {
                    if (system.getOccupancy() == null) {
                        system.setOccupancy(out.getMessage(i).getData().getOccupancy());
                    } else {
                        updateIfNotNull(system.getOccupancy(), out.getMessage(i).getData().getOccupancy());
                    }

                } else if (out.getMessage(i).getData().getEquipments() != null) {
                    if (system.getEquipments().isEmpty()) {
                        system.setEquipments(out.getMessage(i).getData().getEquipments());
                    } else {
                        updateIfNotNull(system.getEquipments(), out.getMessage(i).getData().getEquipments());
                    }

                } else if (out.getMessage(i).getData().getSchedules() != null) {
                    if (system.getSchedules().isEmpty()) {
                        system.setSchedules(out.getMessage(i).getData().getSchedules());
                    } else {
                        updateIfNotNull(system.getSchedules(), out.getMessage(i).getData().getSchedules());
                    }

                } else if (out.getMessage(i).getData().getLogs() != null) {
                    if (system.getLogs() == null) {
                        system.setLogs(out.getMessage(i).getData().getLogs());
                    } else {
                        updateIfNotNull(system.getLogs(), out.getMessage(i).getData().getLogs());
                    }

                }
            }

        } catch (TimeoutException e) {
            logger.debug("Timeout Exception occured in the message pump, will retry");

        } catch (Exception e) {
            logger.debug("Message pump error: {}", e.getMessage());
            Throwable exceptionCause = e.getCause();
            if (exceptionCause != null) {
                logger.debug(exceptionCause.toString());
            }
            throw new Exception(e);
        }
    }

    // Update system object with returned data from the device
    private void updateIfNotNull(Object toSetInto, Object toSet) {

        Field[] fields = toSet.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                if (java.lang.reflect.Modifier.toString(field.getModifiers()) == "final") {
                    continue;
                }
                Field field2update = toSetInto.getClass().getDeclaredField(field.getName());
                field2update.setAccessible(true);
                if (field.get(toSet) != null) {
                    field2update.set(toSetInto, field.get(toSet));
                }
            } catch (Exception e) {

                // Ignoring exception
            }
        }
    }

    public void update() {
    }

    public System getSystemsInfo() {
        return system.getSystem();
    }

    public Occupancy getOccupancyInfo() {
        return system.getOccupancy();
    }

    public ZoneList getZone(Integer iD) {
        return system.getZone(iD);
    }

    public void setSystemManualAwayMode(Boolean awayMode) throws TimeoutException {
        SetManualAwayRequest data = new SetManualAwayRequest(awayMode);
        if (publishMessageHelper(this.system, data)) {
            this.system.getOccupancy().manualAway = awayMode;
        }
    }

    public void setZoneOperationMode(ZoneList heatingZone, HVACMode hvacMode, Integer scheduleID)
            throws TimeoutException {
        SetHVACModeRequest data = new SetHVACModeRequest(hvacMode, scheduleID);
        if (publishMessageHelper(this.system, data)) {
            this.system.getZone(heatingZone.id).getStatus().period.systemMode = hvacMode;
        }
    }

    public void setZoneFanMode(ZoneList heatingZone, FANMode fanMode, Integer scheduleID) throws TimeoutException {
        SetFanModeRequest data = new SetFanModeRequest(fanMode, scheduleID);
        if (publishMessageHelper(this.system, data)) {
            this.system.getZone(heatingZone.id).getStatus().period.fanMode = fanMode;
        }
    }

    public void setSchedule(ZoneList heatingZone, Integer scheduleId) {
        SetScheduleRequest data = new SetScheduleRequest(heatingZone.id, scheduleId);
        if (publishMessageHelper(this.system, data)) {
            this.system.getZone(heatingZone.id).config.scheduleId = scheduleId;
        }
    }

    public void setSchedulePeriod(ZoneList heatingZone, Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF,
            Float spC, Integer husp, Integer desp, Integer scheduleId) {
        SetSchedulePeriodSetPointRequest data = new SetSchedulePeriodSetPointRequest(hspF, cspC, hspC, cspF, spF, spC,
                husp, desp, scheduleId);
        // Disabled for testing
        if (publishMessageHelper(this.system, data)) {
            this.system.getZone(heatingZone.id).getStatus().period.hspF = hspF;
            this.system.getZone(heatingZone.id).getStatus().period.cspC = cspC;
            this.system.getZone(heatingZone.id).getStatus().period.hspC = hspC;
            this.system.getZone(heatingZone.id).getStatus().period.cspF = cspF;
            this.system.getZone(heatingZone.id).getStatus().period.spF = spF;
            this.system.getZone(heatingZone.id).getStatus().period.husp = husp;
            this.system.getZone(heatingZone.id).getStatus().period.desp = desp;
        }
    }

    public void setScheduleOverridePeriod(ZoneList heatingZone, Integer hspF, Float cspC, Float hspC, Integer cspF,
            Integer spF, Float spC, Integer husp, Integer desp, HUMIDMode humidityMode, HVACMode systemMode,
            Integer startTime, FANMode fanMode, Integer scheduleId) {
        SetSchedulePeriodRequest data = new SetSchedulePeriodRequest(hspF, cspC, hspC, cspF, spF, spC, husp, desp,
                humidityMode, systemMode, startTime, fanMode, scheduleId);
        if (publishMessageHelper(this.system, data)) {
            this.system.getZone(heatingZone.id).getStatus().period.hspF = hspF;
            this.system.getZone(heatingZone.id).getStatus().period.cspC = cspC;
            this.system.getZone(heatingZone.id).getStatus().period.hspC = hspC;
            this.system.getZone(heatingZone.id).getStatus().period.cspF = cspF;
            this.system.getZone(heatingZone.id).getStatus().period.spF = spF;
            this.system.getZone(heatingZone.id).getStatus().period.husp = husp;
            this.system.getZone(heatingZone.id).getStatus().period.desp = desp;
            this.system.getZone(heatingZone.id).getStatus().period.humidityMode = humidityMode;
            this.system.getZone(heatingZone.id).getStatus().period.systemMode = systemMode;
            this.system.getZone(heatingZone.id).getStatus().period.startTime = startTime;
            this.system.getZone(heatingZone.id).getStatus().period.fanMode = fanMode;
        }
    }

    public void setScheduleHold(ZoneList heatingZone, PeriodExceptionType exceptionType, Boolean scheduleHold,
            String expiresOn, PeriodExpirationMode expirationMode, Integer scheduleId) {
        SetScheduleHoldRequest data = new SetScheduleHoldRequest(heatingZone.id, exceptionType, scheduleHold, expiresOn,
                expirationMode, scheduleId);
        // Disabled for testing
        if (publishMessageHelper(this.system, data)) {
            this.system.getZone(heatingZone.id).config.scheduleHold.enabled = scheduleHold;
            this.system.getZone(heatingZone.id).config.scheduleHold.exceptionType = exceptionType;
            this.system.getZone(heatingZone.id).config.scheduleHold.expiresOn = expiresOn;
            this.system.getZone(heatingZone.id).config.scheduleHold.expirationMode = expirationMode;
            this.system.getZone(heatingZone.id).config.scheduleHold.scheduleID = scheduleId;
        }
        ;
    }

    // ToDo
    /*
     * 
     * 
     * private OwnerProfileInfo requestUserAccount(String userName) throws TimeoutException {
     * String url = iComfortWiFiApiCommands.getCommandGetOwnerProfileInfo(userName);
     * return apiAccess.doAuthenticatedGet(url, OwnerProfileInfo.class);
     * }
     * 
     * private BuildingsInfo requestBuildingsInfo(String userName) throws TimeoutException {
     * String url = iComfortWiFiApiCommands.getCommandGetBuildingsInfo(userName);
     * return apiAccess.doAuthenticatedGet(url, BuildingsInfo.class);
     * }
     * 
     * private SystemsInfo requestSystemsInfo(String userName) throws TimeoutException {
     * String url = iComfortWiFiApiCommands.getCommandGetSystemsInfo(userName);
     * return apiAccess.doAuthenticatedGet(url, SystemsInfo.class);
     * }
     * 
     * private ZonesStatus requestZonesStatus(String gatewaySN, TempUnits tempUnit) throws TimeoutException {
     * String url = iComfortWiFiApiCommands.getCommandGetTStatInfoList(gatewaySN, tempUnit.getTempUnitsValue());
     * return apiAccess.doAuthenticatedGet(url, ZonesStatus.class);
     * }
     * 
     * private GatewayInfo requestGatewayInfo(String gatewaySN, TempUnits tempUnit) throws TimeoutException {
     * String url = iComfortWiFiApiCommands.getCommandGetGatewayInfo(gatewaySN, tempUnit.getTempUnitsValue());
     * return apiAccess.doAuthenticatedGet(url, GatewayInfo.class);
     * }
     * 
     * private GatewaysAlerts requestGatewaysAlerts(String gatewaySN, PrefferedLanguage languageNbr, Integer count)
     * throws TimeoutException {
     * String url = iComfortWiFiApiCommands.getCommandGetGatewaysAlerts(gatewaySN,
     * languageNbr.getPrefferedLanguageValue().toString(), count.toString());
     * return apiAccess.doAuthenticatedGet(url, GatewaysAlerts.class);
     * }
     * 
     * // User name and Password from configuration validation
     * private boolean validateUsername() {
     * UserValidation validation = null;
     * String basicAuthentication = null;
     * 
     * try {
     * 
     * Map<String, String> headers = new HashMap<>();
     * 
     * // basicAuthentication = "Basic " + B64Code.encode(URLEncoder.encode(configuration.userName, "UTF-8") + ":"
     * // + URLEncoder.encode(configuration.password, "UTF-8"), StringUtil.__ISO_8859_1);
     * 
     * basicAuthentication = String.format("Basic %s",
     * new String(
     * Base64.getEncoder()
     * .encode((URLEncoder.encode(configuration.userName, "UTF-8") + ":"
     * + URLEncoder.encode(configuration.password, "UTF-8")).getBytes()),
     * StandardCharsets.ISO_8859_1));
     * 
     * headers.put("Authorization", basicAuthentication);
     * headers.put("Accept",
     * "application/json, application/xml, text/json, text/x-json, text/javascript, text/xml");
     * 
     * validation = apiAccess.doRequest(
     * HttpMethod.PUT, iComfortWiFiApiCommands
     * .getCommandValidateUser((URLEncoder.encode(configuration.userName, "UTF-8")), 0),
     * headers, null, "application/x-www-form-urlencoded", UserValidation.class);
     * 
     * } catch (TimeoutException e) {
     * // A timeout is not a successful login as well
     * logger.error("Request timeout during user validation", e);
     * } catch (UnsupportedEncodingException e) {
     * logger.error("Credential conversion failed", e);
     * }
     * 
     * if (validation != null && validation.msgCode.equals(RequestStatus.SUCCESS)) {
     * apiAccess.setUserCredentials(basicAuthentication);
     * return true;
     * } else {
     * apiAccess.setUserCredentials(null);
     * return false;
     * }
     * 
     * }
     */

    public Boolean requestDataHelper(iComfortS30System system) {
        logger.debug("Enter - Request Data Helper");
        String addParam = "1;/devices;/zones;/equipments;/schedules;/occupancy;/system";

        try {
            return apiAccess.doAuthenticatedPost(urlRequestdata, null, new RequestData(applicationID, getNewMessageID(),
                    system.getSysID(), new AdditionalParameters(addParam)), Boolean.class);

        } catch (Exception e) {
            logger.debug("Failed to Request Data from the device: ", e.getMessage());
            return false;
        }
    }

    public Boolean publishMessageHelper(iComfortS30System system, Object data) {
        logger.debug("Enter - Publish Message Helper");

        try {
            Boolean ret = apiAccess.doAuthenticatedPost(urlPublish, null,
                    new CommandData(applicationID, getNewMessageID(), system.getSysID(), data), Boolean.class);
            if (ret != null) {
                if (ret == true) {
                    return true;
                } else {
                    throw new Exception("Attempt to publish Message to device was unsuccessful");
                }

            } else {
                throw new Exception("Fatal error during attempt to Post data to device");
            }
        } catch (Exception e) {
            logger.error("Failed to Publish Message to the device: ", e.getMessage());
            return false;
        }
    }

    // Messages seem to use unique GUIDS, here we create one
    private String getNewMessageID() {
        return UUID.randomUUID().toString();
    }
}
