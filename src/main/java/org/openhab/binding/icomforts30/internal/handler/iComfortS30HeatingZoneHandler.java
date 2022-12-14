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

import javax.measure.Unit;
import javax.measure.quantity.Temperature;

import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.FANMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.HUMIDMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.HVACMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.IndoorUnit;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.OutdoorUnit;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.PeriodExceptionType;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.PeriodExpirationMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.TemperatureUnit;
import org.openhab.binding.icomforts30.internal.api.models.response.System;
import org.openhab.binding.icomforts30.internal.api.models.response.ZoneList;
import org.openhab.binding.icomforts30.internal.iComfortS30BindingConstants;
import org.openhab.core.library.types.QuantityType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.library.unit.ImperialUnits;
import org.openhab.core.library.unit.SIUnits;
import org.openhab.core.library.unit.Units;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link iComfortS30HeatingZoneHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Konstantin Panchenko - Initial contribution
 */
public class iComfortS30HeatingZoneHandler extends BaseiComfortS30Handler {

    private final Logger logger = LoggerFactory.getLogger(iComfortS30HeatingZoneHandler.class);

    private ZoneList heatingZone;
    private System systemInfo;

    public iComfortS30HeatingZoneHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    public void update(ZoneList heatingZone, System systemInfo) {
        this.heatingZone = heatingZone;
        this.systemInfo = systemInfo;

        updateiComfortS30ThingStatus(ThingStatus.ONLINE);

        if (systemInfo.getConfig().temperatureUnit == TemperatureUnit.CELSIUS) {
            updateState(iComfortS30BindingConstants.ZONE_TEMPERATURE_CHANNEL, new QuantityType<>(
                    heatingZone.getStatus().temperatureC, systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
            updateState(iComfortS30BindingConstants.ZONE_COOL_SET_POINT_CHANNEL, new QuantityType<>(
                    heatingZone.getStatus().period.cspC, systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
            updateState(iComfortS30BindingConstants.ZONE_HEAT_SET_POINT_CHANNEL, new QuantityType<>(
                    heatingZone.getStatus().period.hspC, systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
            updateState(iComfortS30BindingConstants.ZONE_SET_POINT_CHANNEL, new QuantityType<>(
                    heatingZone.getStatus().period.spC, systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
        } else {
            updateState(iComfortS30BindingConstants.ZONE_TEMPERATURE_CHANNEL, new QuantityType<>(
                    heatingZone.getStatus().temperature, systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
            updateState(iComfortS30BindingConstants.ZONE_COOL_SET_POINT_CHANNEL, new QuantityType<>(
                    heatingZone.getStatus().period.cspF, systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
            updateState(iComfortS30BindingConstants.ZONE_HEAT_SET_POINT_CHANNEL, new QuantityType<>(
                    heatingZone.getStatus().period.hspF, systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
            updateState(iComfortS30BindingConstants.ZONE_SET_POINT_CHANNEL, new QuantityType<>(
                    heatingZone.getStatus().period.spF, systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
        }

        updateState(iComfortS30BindingConstants.ZONE_HUMIDITY_CHANNEL,
                new QuantityType<>(heatingZone.getStatus().humidity, Units.PERCENT));
        updateState(iComfortS30BindingConstants.ZONE_SYSTEM_STATUS_CHANNEL,
                new StringType(heatingZone.getStatus().tempOperation.toString()));
        updateState(iComfortS30BindingConstants.ZONE_OPERATION_MODE_CHANNEL,
                new StringType(heatingZone.getStatus().period.systemMode.toString()));
        updateState(iComfortS30BindingConstants.ZONE_FAN_MODE_CHANNEL,
                new StringType(heatingZone.getStatus().period.fanMode.toString()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("Entering Heating Zone Handler for zone {}", heatingZone.id);
        logger.debug("Executing command {}", command.toString());

        if (command == RefreshType.REFRESH) {
            update(heatingZone, systemInfo);
        } else {
            iComfortS30ThermostatBridgeHandler bridge = getiComfortS30Bridge();
            String channelId = channelUID.getId();
            // ToDo
            if (iComfortS30BindingConstants.ZONE_OPERATION_MODE_CHANNEL.equals(channelId)) {
                if (command.toString() == HVACMode.HVAC_COOL.toString() && heatingZone.config.coolingOption == false) {
                    logger.warn("Operation mode {} is not available for zone {}", command.toString(),
                            heatingZone.config.name);
                    return;
                } else if (command.toString() == HVACMode.HVAC_HEAT.toString()
                        && heatingZone.config.heatingOption == false) {

                    logger.warn("Operation mode {} is not available for zone {}", command.toString(),
                            heatingZone.config.name);
                    return;
                } else if (command.toString() == HVACMode.HVAC_HEAT_COOL.toString()
                        && (heatingZone.config.heatingOption == false || heatingZone.config.coolingOption == false)) {
                    logger.warn("Operation mode {} is not available for zone {}", command.toString(),
                            heatingZone.config.name);
                    return;

                } else if (command.toString() == HVACMode.HVAC_EMERGENCY_HEAT.toString()
                        && (heatingZone.config.heatingOption == false || hasEmergencyHeat() == false)) {
                    logger.warn("Operation mode {} is not available for zone {}", command.toString(),
                            heatingZone.config.name);
                    return;
                }
                if (isZoneManualMode() == false) {
                    bridge.setZoneSchedule(heatingZone, getManualModeScheduleId());
                }

                bridge.setZoneOperationMode(heatingZone, CustomTypes.HVACMode.valueOf(command.toString()),
                        getManualModeScheduleId());
            } else if (iComfortS30BindingConstants.ZONE_FAN_MODE_CHANNEL.equals(channelId)) {
                if (isZoneManualMode() == true) {
                    bridge.setZoneFanMode(heatingZone, CustomTypes.FANMode.valueOf(command.toString()),
                            getManualModeScheduleId());
                }

                // ToDo
                // If not in manual mode set to override mode

            } else if (iComfortS30BindingConstants.ZONE_COOL_SET_POINT_CHANNEL.equals(channelId)) {

                // ToDo
                // Validate manual and overide mode
                Unit<Temperature> tempUnit = ((QuantityType<Temperature>) command).getUnit();
                if (tempUnit == SIUnits.CELSIUS) {
                    // Command is in Celsius
                    if ((((QuantityType<Temperature>) command).floatValue() > heatingZone.getConfig().minCspC)
                            || (((QuantityType<Temperature>) command).floatValue() < heatingZone.getConfig().maxCspC)) {

                        QuantityType<Temperature> setPointF = ((QuantityType<Temperature>) command)
                                .toUnit(ImperialUnits.FAHRENHEIT);
                        if (setPointF != null) {
                            Integer cspF = Math.round(setPointF.floatValue());
                            performSetPoints(null, ((QuantityType<Temperature>) command).floatValue(), null, cspF, null,
                                    null, null, null);
                        }
                    } else {
                        logger.warn(
                                "Desired set temperature {} is outside of set parameters Min({}) - Max({}), ignoring command",
                                ((QuantityType<Temperature>) command).floatValue(), heatingZone.getConfig().minCspC,
                                heatingZone.getConfig().maxCspC);
                    }
                } else {
                    // Command is in Fahrenheit
                    if ((((QuantityType<Temperature>) command).intValue() > heatingZone.getConfig().minCsp)
                            || (((QuantityType<Temperature>) command).intValue() < heatingZone.getConfig().maxCsp)) {

                        QuantityType<Temperature> setPoint?? = ((QuantityType<Temperature>) command)
                                .toUnit(SIUnits.CELSIUS);
                        if (setPoint?? != null) {
                            Float cspC = Math.round(setPoint??.floatValue() * 10f) / 10f;
                            performSetPoints(null, cspC, null, ((QuantityType<Temperature>) command).intValue(), null,
                                    null, null, null);
                        }
                    } else {
                        logger.warn(
                                "Desired set temperature {} is outside of set parameters Min({}) - Max({}), ignoring command",
                                ((QuantityType<Temperature>) command).intValue(), heatingZone.getConfig().minCsp,
                                heatingZone.getConfig().maxCsp);
                    }
                }

            } else if (iComfortS30BindingConstants.ZONE_HEAT_SET_POINT_CHANNEL.equals(channelId)) {

                Unit<Temperature> tempUnit = ((QuantityType<Temperature>) command).getUnit();
                if (tempUnit == SIUnits.CELSIUS) {
                    // Command is in Celsius
                    if ((((QuantityType<Temperature>) command).floatValue() > heatingZone.getConfig().minHspC)
                            || (((QuantityType<Temperature>) command).floatValue() < heatingZone.getConfig().maxHspC)) {

                        QuantityType<Temperature> setPointF = ((QuantityType<Temperature>) command)
                                .toUnit(ImperialUnits.FAHRENHEIT);

                        if (setPointF != null) {
                            Integer hspF = Math.round(setPointF.floatValue());
                            performSetPoints(hspF, null, ((QuantityType<Temperature>) command).floatValue(), null, null,
                                    null, null, null);
                        }
                    } else {
                        logger.warn(
                                "Desired set temperature {} is outside of set parameters Min({}) - Max({}), ignoring command",
                                ((QuantityType<Temperature>) command).floatValue(), heatingZone.getConfig().minHspC,
                                heatingZone.getConfig().maxHspC);
                    }
                } else {
                    // Command is in Fahrenheit
                    if ((((QuantityType<Temperature>) command).intValue() > heatingZone.getConfig().minHsp)
                            || (((QuantityType<Temperature>) command).intValue() < heatingZone.getConfig().maxHsp)) {

                        QuantityType<Temperature> setPoint?? = ((QuantityType<Temperature>) command)
                                .toUnit(SIUnits.CELSIUS);
                        if (setPoint?? != null) {
                            Float hspC = Math.round(setPoint??.floatValue() * 2f) / 2f;
                            performSetPoints(((QuantityType<Temperature>) command).intValue(), null, hspC, null, null,
                                    null, null, null);
                        }
                    } else {
                        logger.warn(
                                "Desired set temperature {} is outside of set parameters Min({}) - Max({}), ignoring command",
                                ((QuantityType<Temperature>) command).intValue(), heatingZone.getConfig().minHsp,
                                heatingZone.getConfig().maxHsp);
                    }
                }

            }

        }
    }

    private void performSetPoints(Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF, Float spC,
            Integer husp, Integer desp) {

        if (hspF == null) {
            hspF = heatingZone.getSchedule().periods.get(0).period.hspF;
        }
        if (cspC == null) {
            cspC = heatingZone.getSchedule().periods.get(0).period.cspC;
        }
        if (hspC == null) {
            hspC = heatingZone.getSchedule().periods.get(0).period.hspC;
        }
        if (cspF == null) {
            cspF = heatingZone.getSchedule().periods.get(0).period.cspF;
        }
        if (spF == null) {
            spF = heatingZone.getSchedule().periods.get(0).period.spF;
        }
        if (spC == null) {
            spC = heatingZone.getSchedule().periods.get(0).period.spC;
        }
        if (husp == null) {
            husp = heatingZone.getSchedule().periods.get(0).period.husp;
        }
        if (desp == null) {
            desp = heatingZone.getSchedule().periods.get(0).period.desp;
        }

        if (isZoneManualMode() == true) {
            logger.debug("Zone {} already in Manual Mode, setting Set Points", heatingZone.getId());
            getiComfortS30Bridge().setSchedulePeriod(heatingZone, hspF, cspC, hspC, cspF, spF, spC, husp, desp,
                    getManualModeScheduleId());

        } else if (isZoneOveride() == true) {
            logger.debug("Zone {} already in Override Mode, setting Set Points", heatingZone.getId());
            getiComfortS30Bridge().setSchedulePeriod(heatingZone, hspF, cspC, hspC, cspF, spF, spC, husp, desp,
                    getOverrideScheduleId());
        } else {
            logger.debug("Zone {} is following the schedule, setting Override schedule", heatingZone.getId());
            HUMIDMode humidityMode = heatingZone.getSchedule().periods.get(0).period.humidityMode;
            HVACMode systemMode = heatingZone.getSchedule().periods.get(0).period.systemMode;
            Integer startTime = heatingZone.getSchedule().periods.get(0).period.startTime;
            FANMode fanMode = heatingZone.getSchedule().periods.get(0).period.fanMode;

            // ToDo
            // Possible error handling
            getiComfortS30Bridge().setScheduleOverridePeriod(heatingZone, hspF, cspC, hspC, cspF, spF, spC, husp, desp,
                    humidityMode, systemMode, startTime, fanMode, getOverrideScheduleId());
            // Possible error handling
            getiComfortS30Bridge().setScheduleHold(heatingZone, PeriodExceptionType.HOLD, true, "0",
                    PeriodExpirationMode.NEXTPERIOD, getOverrideScheduleId());
        }
    }

    private boolean hasEmergencyHeat() {
        // Returns True is the system has emergency heat
        // Emergency heat is defined as a system with a heat pump that also has an indoor furnace
        if (systemInfo.config.options.outdoorUnitType == OutdoorUnit.HEAT_PUMP
                && systemInfo.config.options.indoorUnitType == IndoorUnit.FURNACE) {
            return true;
        } else {
            return false;
        }
    }

    private Integer getManualModeScheduleId() {
        return 16 + heatingZone.id;
    }

    private Integer getOverrideScheduleId() {
        return 32 + heatingZone.id;
    }

    private Boolean isZoneManualMode() {
        if (heatingZone.config.scheduleId == getManualModeScheduleId()) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean isZoneOveride() {
        if (heatingZone.config.scheduleId == getOverrideScheduleId()) {
            return true;
        } else {
            return false;
        }
    }

    public Integer getZoneID() {
        return getId();
    }
}
