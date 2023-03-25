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

package org.openhab.binding.icomforts30.internal.api.models.common;

import javax.measure.Unit;
import javax.measure.quantity.Temperature;

import org.openhab.core.library.unit.ImperialUnits;
import org.openhab.core.library.unit.SIUnits;

import com.google.gson.annotations.SerializedName;

/**
 * Custom types for responce messages
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class CustomTypes {

    public enum ConnectionStatus {
        @SerializedName("GOOD")
        GOOD,
        @SerializedName("BAD")
        BAD,
        UNKNOWN;
    }

    public enum OnOff {
        @SerializedName("On")
        ON,
        @SerializedName("Off")
        OFF;

    }

    // schedules[].schedule.periods[].period.systemMode
    // zones[].status.period.systemMode
    public enum HVACMode {
        @SerializedName("off")
        HVAC_OFF("off"),
        @SerializedName("cool")
        HVAC_COOL("cool"),
        @SerializedName("heat")
        HVAC_HEAT("heat"),
        @SerializedName("heat and cool")
        HVAC_HEAT_COOL("heat and cool"),
        @SerializedName("emergency heat")
        HVAC_EMERGENCY_HEAT("emergency heat");

        private String hvacModeValue;

        private HVACMode(String hvacModeValue) {
            this.hvacModeValue = hvacModeValue;
        }

        public String getHVACModeValue() {
            return this.hvacModeValue;
        }
    }

    // zones[].status.tempOperation
    public enum HVACStatus {
        @SerializedName("off")
        HVAC_IDLE,
        @SerializedName("cooling")
        HVAC_COOLING,
        @SerializedName("heating")
        HVAC_HEATING;
    }

    // zones[].status.humOperation
    public enum HUMIDOperation {
        @SerializedName("dehumidifying")
        HUMID_DEHUMID,
        @SerializedName("humidifying")
        HUMID_HUMID,
        @SerializedName("waiting")
        HUMID_WAITING,
        @SerializedName("off")
        OFF;
    }

    // zones[].status.period.humidityMode
    public enum HUMIDMode {
        @SerializedName("dehumidify")
        HUMID_DEHUMID("dehumidify"),
        @SerializedName("humidify")
        HUMID_HUMID("humidify"),
        @SerializedName("off")
        HUMID_OFF("off");

        private String humidModeValue;

        private HUMIDMode(String humidModeValue) {
            this.humidModeValue = humidModeValue;
        }

        public String getHumidModeValue() {
            return this.humidModeValue;
        }
    }

    // system.config.humidificationMode
    public enum HumidificationMode {

        @SerializedName("basic")
        BASIC,
        UNKNOWN;

    }

    // system.config.dehumidificationMode
    public enum DeHumidificationMode {

        @SerializedName("high") // Lennox UI - MAX
        BASIC,
        @SerializedName("medium") // Lennox UI - Normal
        MEDIUM,
        @SerializedName("auto") // Lennos UI - Climate IQ
        AUTO,
        UNKNOWN;

    }

    // system.status.zoningMode
    public enum ZoningMode {

        @SerializedName("zoned")
        ZONED,
        @SerializedName("central")
        CENTRAL;

    }

    // schedules[].schedule.periods[].period.fanMode
    // zones[].status.period.fanMode
    public enum FANMode {
        @SerializedName("on")
        FAN_ON,
        @SerializedName("auto")
        FAN_AUTO,
        @SerializedName("circulate")
        FAN_CIRCULATE;
    }

    // system.config.options.indoorUnitType
    public enum IndoorUnit {
        @SerializedName("furnace")
        FURNACE,
        @SerializedName("air handler")
        AIR_HANDLER;

    }

    // system.config.options.outdoorUnitType
    public enum OutdoorUnit {
        @SerializedName("air conditioner")
        AIR_CONDITIONER,
        @SerializedName("heat pump")
        HEAT_PUMP;
    }

    // system.config.options.humidifierType
    // possible other values
    public enum HumidifierUnit {
        @SerializedName("none")
        NONE,
        @SerializedName("24v")
        TWENTY_FOUR_V,
        UNKNOWN;
    }

    // system.config.options.dehumidifierType
    // possible other values
    public enum DeHumidifierUnit {
        @SerializedName("none")
        NONE,
        @SerializedName("air conditioner")
        AIR_CONDITIONER,
        UNKNOWN;
    }

    // system.config.options.pureAir.unitType
    // possible other values
    public enum PureAirUnit {
        @SerializedName("")
        NONE,
        UNKNOWN;
    }

    // system.config.options.ventilation.unitType
    // possible other values
    public enum VentilationUnit {
        @SerializedName("none")
        NONE,
        @SerializedName("ventilator")
        VENTILATOR,
        UNKNOWN;
    }

    // system.config.options.ventilation.controlMode
    // possible other values
    public enum VentilationControlMode {
        @SerializedName("timed")
        TIMED,
        @SerializedName("ASHRAE")
        ASHRAE,
        UNKNOWN;
    }

    // occupancy.smartAway.status.state
    public enum SAState {
        @SerializedName("enabled cancelled")
        SA_ENABLED_CANCELLED,
        @SerializedName("enabled active")
        SA_ENABLED_ACTIVE,
        @SerializedName("enabled inactive")
        SA_EANBLED_INACTIVE,
        @SerializedName("disabled")
        DISABLED;
    }

    // system.clock.tz
    public enum ClockTimezone {
        @SerializedName("newfoundland")
        NEWFOUNDLAND,
        @SerializedName("atlantic")
        ATLANTIC,
        @SerializedName("eastern")
        EASTERN,
        @SerializedName("central")
        CENTRAL,
        @SerializedName("mountain")
        MONUNTAIN,
        @SerializedName("pacific")
        PACIFIC,
        @SerializedName("alaska")
        ALASKA,
        @SerializedName("hawaii")
        HAWAII,
        @SerializedName("samoa")
        SAMOA,
        @SerializedName("chamorro")
        CHAMORRO,
        @SerializedName("mawson station")
        MAWSON_STATION,
        @SerializedName("christmas island")
        CHRISTMAS_ISLAND,
        @SerializedName("australia central")
        AUSTRALIA_CENTRAL,
        @SerializedName("heard and mcdonald island")
        HEARD_AND_MCDONALD_ISLAND,
        @SerializedName("australia western")
        AUSTRALIA_WESTERN,
        @SerializedName("australia eastern")
        AUSTRALIA_EASTERN,
        @SerializedName("cocos islands")
        COCOS_ISLANDS,
        @SerializedName("casey")
        CASEY,
        @SerializedName("lord howe")
        LORD_HOWE,
        @SerializedName("davis")
        DAVIS,
        @SerializedName("central western")
        CENTRAL_WESTERN,
        @SerializedName("norfolk")
        NORFOLK,
        UNKNOWN;
    }

    // home.address.country
    public enum CountryAbbreviation {
        @SerializedName("US")
        US,
        @SerializedName("CA")
        CA,
        @SerializedName("AU")
        AU;
    }

    // system.clock.format
    public enum ClockFormat {
        @SerializedName("12hour")
        TIMED,
        @SerializedName("24hour") // guessed
        ASHRAE,
        UNKNOWN;
    }

    public enum WriteAccess {
        @SerializedName("openAll")
        OPEN_ALL,
        @SerializedName("local")
        LOCAL,
        @SerializedName("remote")
        REMOTE,
        @SerializedName("active event zone 1")
        ACTIVE_EVENT_ZONE_1,
        @SerializedName("active event zone 2")
        ACTIVE_EVENT_ZONE_2,
        @SerializedName("active event zone 3")
        ACTIVE_EVENT_ZONE_3,
        @SerializedName("active event zone 4")
        ACTIVE_EVENT_ZONE_4,
        @SerializedName("active event zone 5")
        ACTIVE_EVENT_ZONE_5,
        @SerializedName("active event zone 6")
        ACTIVE_EVENT_ZONE_6,
        @SerializedName("active event zone 7")
        ACTIVE_EVENT_ZONE_7,
        @SerializedName("active event zone 8")
        ACTIVE_EVENT_ZONE_8;
    }

    // system.status.outdoorTemperatureStatus
    // zones[].status.humidityStatus
    // zones[].status.temperatureStatus
    public enum Status {
        @SerializedName("good")
        GOOD,
        @SerializedName("not_exist")
        NOT_EXIST,
        @SerializedName("not_available")
        NOT_AVAILABLE,
        @SerializedName("error")
        ERROR;
    }

    // system.status.alert
    public enum Alert {
        @SerializedName("critical")
        CRITICAL,
        @SerializedName("moderate")
        MODERATE,
        @SerializedName("minor")
        MINOR,
        @SerializedName("none")
        NONE;
    }

    public enum State {
        @SerializedName("enabled")
        ENABLED,
        @SerializedName("disabled")
        DISABLED;
    }

    // occupancy.smartAway.status.setpointState
    public enum SetpointState {
        @SerializedName("home")
        HOME,
        @SerializedName("transition")
        TRANSITION,
        @SerializedName("away")
        AWAY;
    }

    public enum ManualAway {
        // @SerializedName("false")
        HOME(false),
        // @SerializedName("true")
        AWAY(true);

        private Boolean awayValue;

        private ManualAway(Boolean awayValue) {
            this.awayValue = awayValue;
        }

        public Boolean getManualAwayValue() {
            return this.awayValue;
        }
    }

    public enum TemperatureUnit {
        @SerializedName("C")
        CELSIUS("C"),
        @SerializedName("F")
        FAHRENHEIT("F"),
        UNKNOWN("unknown");

        private String tempUnitsValue;

        private TemperatureUnit(String tempUnitsValue) {
            this.tempUnitsValue = tempUnitsValue;
        }

        public String getTempUnitsValue() {
            return this.tempUnitsValue;
        }

        public Unit<Temperature> getTemperatureUnit() {
            switch (this.tempUnitsValue) {
                case "F":
                    return ImperialUnits.FAHRENHEIT;
                case "C":
                    return SIUnits.CELSIUS;
                default:
                    return null;
            }
        }
    }

    // Other values are not known
    public enum PeriodExpirationMode {
        @SerializedName("nextPeriod")
        NEXTPERIOD,
        UNKNOWN;
    }

    // Other values are not known
    public enum PeriodExceptionType {
        @SerializedName("hold")
        HOLD,
        UNKNOWN;
    }

    // Other values are not known
    public enum DeviceType {
        @SerializedName("system")
        SYSTEM,
        @SerializedName("tstat")
        TSTAT,
        UNKNOWN;
    }

    // Other values are not known
    public enum DealerPermissionToUpdate {
        @SerializedName("always")
        ALWAYS,
        @SerializedName("none")
        NONE,
        UNKNOWN;
    }

    // alerts.active.alert.action
    public enum AlertAction {
        @SerializedName("clear")
        CLEAR,
        @SerializedName("set")
        SET,
        UNKNOWN;
    }

    // alerts.active.alert.clearedBy
    public enum AlertClearedBy {
        @SerializedName("device")
        DEVICE,
        @SerializedName("active")
        ACTIVE,
        UNKNOWN;
    }

    // alerts.active.alert.priority
    public enum AlertPriority {
        @SerializedName("info")
        INFO,
        @SerializedName("minor")
        MINOR,
        @SerializedName("moderate")
        MODERATE,
        @SerializedName("major") // guessed
        MAJOR,
        UNKNOWN;
    }
}
