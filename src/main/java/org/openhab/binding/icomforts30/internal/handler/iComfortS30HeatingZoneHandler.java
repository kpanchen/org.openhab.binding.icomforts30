/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
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
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Temperature;

import org.eclipse.jdt.annotation.NonNull;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.FANMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.HUMIDMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.HVACMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.IndoorUnit;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.OutdoorUnit;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.PeriodExceptionType;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.PeriodExpirationMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.TemperatureUnit;
import org.openhab.binding.icomforts30.internal.api.models.response.Period;
import org.openhab.binding.icomforts30.internal.api.models.response.System;
import org.openhab.binding.icomforts30.internal.api.models.response.ZoneList;
import org.openhab.binding.icomforts30.internal.iComfortS30BindingConstants;
import org.openhab.core.library.types.OnOffType;
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
            updateState(iComfortS30BindingConstants.ZONE_COOL_SET_POINT_CHANNEL,
                    new QuantityType<>(heatingZone.getStatus().getPeriod().cspC,
                            systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
            updateState(iComfortS30BindingConstants.ZONE_HEAT_SET_POINT_CHANNEL,
                    new QuantityType<>(heatingZone.getStatus().getPeriod().hspC,
                            systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
            updateState(iComfortS30BindingConstants.ZONE_SET_POINT_CHANNEL,
                    new QuantityType<>(heatingZone.getStatus().getPeriod().spC,
                            systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
        } else {
            updateState(iComfortS30BindingConstants.ZONE_TEMPERATURE_CHANNEL, new QuantityType<>(
                    heatingZone.getStatus().temperature, systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
            updateState(iComfortS30BindingConstants.ZONE_COOL_SET_POINT_CHANNEL,
                    new QuantityType<>(heatingZone.getStatus().getPeriod().cspF,
                            systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
            updateState(iComfortS30BindingConstants.ZONE_HEAT_SET_POINT_CHANNEL,
                    new QuantityType<>(heatingZone.getStatus().getPeriod().hspF,
                            systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
            updateState(iComfortS30BindingConstants.ZONE_SET_POINT_CHANNEL,
                    new QuantityType<>(heatingZone.getStatus().getPeriod().spF,
                            systemInfo.getConfig().temperatureUnit.getTemperatureUnit()));
        }

        updateState(iComfortS30BindingConstants.ZONE_HUMIDITY_CHANNEL,
                new QuantityType<>(heatingZone.getStatus().humidity, Units.PERCENT));
        updateState(iComfortS30BindingConstants.ZONE_SYSTEM_STATUS_CHANNEL,
                new StringType(heatingZone.getStatus().tempOperation.toString()));
        updateState(iComfortS30BindingConstants.ZONE_OPERATION_MODE_CHANNEL,
                new StringType(heatingZone.getStatus().getPeriod().systemMode.toString()));
        updateState(iComfortS30BindingConstants.ZONE_FAN_STATUS_CHANNEL, OnOffType.from(heatingZone.getStatus().fan));
        updateState(iComfortS30BindingConstants.ZONE_FAN_MODE_CHANNEL,
                new StringType(heatingZone.getStatus().getPeriod().fanMode.toString()));
        updateState(iComfortS30BindingConstants.ZONE_HUMIDITY_STATUS_CHANNEL,
                new StringType(heatingZone.getStatus().humOperation.toString()));
        updateState(iComfortS30BindingConstants.ZONE_HUMIDITY_MODE_CHANNEL,
                new StringType(heatingZone.getStatus().getPeriod().humidityMode.toString()));
        updateState(iComfortS30BindingConstants.ZONE_HUMIDITY_SET_POINT_CHANNEL,
                new QuantityType(heatingZone.getStatus().getPeriod().husp, Units.PERCENT));
        updateState(iComfortS30BindingConstants.ZONE_DEHUMIDIFICATION_SET_POINT_CHANNEL,
                new QuantityType(heatingZone.getStatus().getPeriod().desp, Units.PERCENT));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleCommand(@NonNull ChannelUID channelUID, @NonNull Command command) {
        logger.debug("Entering Heating Zone Handler for zone {}", heatingZone.getId());
        logger.debug("Executing command {}", command.toString());

        if (command == RefreshType.REFRESH) {
            update(heatingZone, systemInfo);
        } else {
            iComfortS30ThermostatBridgeHandler bridge = getiComfortS30Bridge();
            String channelId = channelUID.getId();
            // ToDo
            if (iComfortS30BindingConstants.ZONE_OPERATION_MODE_CHANNEL.equals(channelId)) {
                if (command.toString() == HVACMode.HVAC_COOL.toString()
                        && heatingZone.getConfig().coolingOption == false) {
                    logger.warn("Operation mode {} is not available for zone {}", command.toString(),
                            heatingZone.getConfig().name);
                    return;
                } else if (command.toString() == HVACMode.HVAC_HEAT.toString()
                        && heatingZone.getConfig().heatingOption == false) {

                    logger.warn("Operation mode {} is not available for zone {}", command.toString(),
                            heatingZone.getConfig().name);
                    return;
                } else if (command.toString() == HVACMode.HVAC_HEAT_COOL.toString()
                        && (heatingZone.getConfig().heatingOption == false
                                || heatingZone.getConfig().coolingOption == false)) {
                    logger.warn("Operation mode {} is not available for zone {}", command.toString(),
                            heatingZone.getConfig().name);
                    return;

                } else if (command.toString() == HVACMode.HVAC_EMERGENCY_HEAT.toString()
                        && (heatingZone.getConfig().heatingOption == false || hasEmergencyHeat() == false)) {
                    logger.warn("Operation mode {} is not available for zone {}", command.toString(),
                            heatingZone.getConfig().name);
                    return;
                }
                if (isZoneManualMode() == false) {
                    logger.debug("Zone {} is not in Manual Mode, setting Zone Manual mode", heatingZone.getId());
                    bridge.setZoneSchedule(heatingZone, getManualModeScheduleId());
                }

                logger.debug("Setting HVAC operation mode to {}", CustomTypes.HVACMode.valueOf(command.toString()));
                bridge.setZoneOperationMode(heatingZone, CustomTypes.HVACMode.valueOf(command.toString()),
                        getManualModeScheduleId());

            } else if (iComfortS30BindingConstants.ZONE_FAN_MODE_CHANNEL.equals(channelId)) {

                if (isZoneManualMode() == true) {
                    logger.debug("Zone {} already in Manual Mode, setting Fan operation mode", heatingZone.getId());
                    bridge.setZoneFanMode(heatingZone, CustomTypes.FANMode.valueOf(command.toString()),
                            getManualModeScheduleId());
                } else if (isZoneOveride() == true) {
                    logger.debug("Zone {} already in Override Mode, setting Fan operation mode", heatingZone.getId());

                    bridge.setZoneFanMode(heatingZone, CustomTypes.FANMode.valueOf(command.toString()),
                            getOverrideScheduleId());
                } else {
                    logger.debug("Zone {} is following the schedule, setting Override schedule", heatingZone.getId());
                    Period period = heatingZone.getStatus().getPeriod();

                    // ToDo
                    // Possible error handling
                    getiComfortS30Bridge().setScheduleOverridePeriod(heatingZone, period.hspF, period.cspC, period.hspC,
                            period.cspF, period.spF, period.spC, period.husp, period.desp, period.humidityMode,
                            period.systemMode, period.startTime, CustomTypes.FANMode.valueOf(command.toString()),
                            getOverrideScheduleId());
                    // Possible error handling
                    getiComfortS30Bridge().setScheduleHold(heatingZone, PeriodExceptionType.HOLD, true, "0",
                            PeriodExpirationMode.NEXTPERIOD, getOverrideScheduleId());

                }

            } else if (iComfortS30BindingConstants.ZONE_HUMIDITY_MODE_CHANNEL.equals(channelId)) {
                if (command.toString() == HUMIDMode.HUMID_HUMID.toString()
                        && heatingZone.getConfig().humidificationOption == false) {
                    logger.warn("Humidification mode {} is not available for zone {}", command.toString(),
                            heatingZone.getConfig().name);
                    return;

                } else if (command.toString() == HUMIDMode.HUMID_DEHUMID.toString()
                        && heatingZone.getConfig().dehumidificationOption == false) {
                    logger.warn("Dehumidification mode {} is not available for zone {}", command.toString(),
                            heatingZone.getConfig().name);
                    return;

                }

                if (isZoneManualMode() == false) {
                    logger.debug("Zone {} is not in Manual Mode, setting Zone Manual mode", heatingZone.getId());
                    bridge.setZoneSchedule(heatingZone, getManualModeScheduleId());
                }

                logger.debug("Setting HVAC humidity operation mode to {}",
                        CustomTypes.HUMIDMode.valueOf(command.toString()));
                bridge.setZoneHumidificationMode(heatingZone, CustomTypes.HUMIDMode.valueOf(command.toString()),
                        getManualModeScheduleId());

                // If not in manual mode zone will be set to override mode
            } else if (iComfortS30BindingConstants.ZONE_COOL_SET_POINT_CHANNEL.equals(channelId)) {

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

                        QuantityType<Temperature> setPointС = ((QuantityType<Temperature>) command)
                                .toUnit(SIUnits.CELSIUS);
                        if (setPointС != null) {
                            Float cspC = Math.round(setPointС.floatValue() * 10f) / 10f;
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

                        QuantityType<Temperature> setPointС = ((QuantityType<Temperature>) command)
                                .toUnit(SIUnits.CELSIUS);
                        if (setPointС != null) {
                            Float hspC = Math.round(setPointС.floatValue() * 2f) / 2f;
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

            } else if (iComfortS30BindingConstants.ZONE_HUMIDITY_SET_POINT_CHANNEL.equals(channelId)) {
                if ((((QuantityType<Dimensionless>) command).intValue() > heatingZone.getConfig().minHumSp)
                        || (((QuantityType<Dimensionless>) command).intValue() < heatingZone.getConfig().maxHumSp)) {
                    performSetPoints(null, null, null, null, null, null,
                            ((QuantityType<Dimensionless>) command).intValue(), null);
                } else {
                    logger.warn(
                            "Desired set humidity {} is outside of set parameters Min({}) - Max({}), ignoring command",
                            ((QuantityType<Dimensionless>) command).intValue(), heatingZone.getConfig().minHumSp,
                            heatingZone.getConfig().maxHumSp);
                }

            } else if (iComfortS30BindingConstants.ZONE_DEHUMIDIFICATION_SET_POINT_CHANNEL.equals(channelId)) {
                if ((((QuantityType<Dimensionless>) command).intValue() > heatingZone.getConfig().minDehumSp)
                        || (((QuantityType<Dimensionless>) command).intValue() < heatingZone.getConfig().maxDehumSp)) {
                    performSetPoints(null, null, null, null, null, null, null,
                            ((QuantityType<Dimensionless>) command).intValue());
                } else {
                    logger.warn(
                            "Desired set dehumidification {} is outside of set parameters Min({}) - Max({}), ignoring command",
                            ((QuantityType<Dimensionless>) command).intValue(), heatingZone.getConfig().minDehumSp,
                            heatingZone.getConfig().maxDehumSp);
                }

            }

        }
    }

    private void performSetPoints(Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF, Float spC,
            Integer husp, Integer desp) {

        Period period = heatingZone.getStatus().getPeriod();
        if (period != null) {

            if (hspF == null) {
                hspF = period.hspF;
            }
            if (cspC == null) {
                cspC = period.cspC;
            }
            if (hspC == null) {
                hspC = period.hspC;
            }
            if (cspF == null) {
                cspF = period.cspF;
            }
            if (spF == null) {
                spF = period.spF;
            }
            if (spC == null) {
                spC = period.spC;
            }
            if (husp == null) {
                husp = period.husp;
            }
            if (desp == null) {
                desp = period.desp;
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
                HUMIDMode humidityMode = period.humidityMode;
                HVACMode systemMode = period.systemMode;
                Integer startTime = period.startTime;
                FANMode fanMode = period.fanMode;

                // ToDo
                // Possible error handling
                getiComfortS30Bridge().setScheduleOverridePeriod(heatingZone, hspF, cspC, hspC, cspF, spF, spC, husp,
                        desp, humidityMode, systemMode, startTime, fanMode, getOverrideScheduleId());
                // Possible error handling
                getiComfortS30Bridge().setScheduleHold(heatingZone, PeriodExceptionType.HOLD, true, "0",
                        PeriodExpirationMode.NEXTPERIOD, getOverrideScheduleId());
            }
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
        return 16 + heatingZone.getId();
    }

    private Integer getOverrideScheduleId() {
        return 32 + heatingZone.getId();
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
