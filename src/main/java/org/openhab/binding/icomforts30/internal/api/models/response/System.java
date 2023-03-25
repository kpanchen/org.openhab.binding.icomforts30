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

package org.openhab.binding.icomforts30.internal.api.models.response;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.*;

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the system information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class System {

    @SerializedName("clock")
    public Clock clock;

    public Clock getClock() {
        return this.clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public class Clock {

        @SerializedName("setRtcTime")
        public Date setRtcTime;

        @SerializedName("tz")
        public ClockTimezone tz;

        @SerializedName("format")
        public ClockFormat format;

        @SerializedName("localSetRtcTime")
        public String localSetRtcTime;

        @SerializedName("enableDst")
        public Boolean enableDst;

        @SerializedName("dstOffset")
        public Integer dstOffset;

        @SerializedName("writeAccess")
        public WriteAccess writeAccess;

        @SerializedName("offset")
        public Integer offset;
    }

    @SerializedName("time")
    public Time time;

    public Time getTime() {
        return this.time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public class Time {

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;

        @SerializedName("sysUpTime")
        public Duration sysUpTime;

        @SerializedName("currentTime")
        public Date currentTime;
    }

    @SerializedName("status")
    public SystemStatus status;

    public SystemStatus getStatus() {
        return this.status;
    }

    public void setStatus(SystemStatus status) {
        this.status = status;
    }

    public class SystemStatus {

        @SerializedName("diagLevel")
        public Integer diagLevel;

        @SerializedName("outdoorTemperature")
        public Float outdoorTemperature;

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;

        @SerializedName("diagRuntime")
        public Integer diagRuntime;

        @SerializedName("configured")
        public Boolean configured;

        @SerializedName("diagVentilationRuntime")
        public Integer diagVentilationRuntime;

        @SerializedName("diagPoweredHours")
        public Integer diagPoweredHours;

        @SerializedName("unknownDeviceFound")
        public Boolean unknownDeviceFound;

        @SerializedName("ventilationRemainingTime")
        public Integer ventilationRemainingTime;

        @SerializedName("wideSetpointRange")
        public Boolean wideSetpointRange;

        @SerializedName("outdoorTemperatureStatus")
        public Status outdoorTemperatureStatus;

        @SerializedName("ventilatingUntilTime")
        public String ventilatingUntilTime;

        @SerializedName("writeAccess")
        public WriteAccess writeAccess;

        @SerializedName("reminder")
        public Boolean reminder;

        @SerializedName("configProblem")
        public Boolean configProblem;

        @SerializedName("zoningMode")
        public ZoningMode zoningMode;

        @SerializedName("replacementPart")
        public Boolean replacementPart;

        @SerializedName("alert")
        public Alert alert;

        @SerializedName("feelsLikeMode")
        public Boolean feelsLikeMode;

        @SerializedName("numberOfZones")
        public Integer numberOfZones;

        @SerializedName("singleSetpointMode")
        public Boolean singleSetpointMode;

        @SerializedName("missingDeviceFound")
        public Boolean missingDeviceFound;

        @SerializedName("rsbusMode")
        public String rsbusMode;

        @SerializedName("replaced")
        public Boolean replaced;

        @SerializedName("outdoorTemperatureC")
        public Float outdoorTemperatureC;

        @SerializedName("incompleteSystem")
        public Boolean incompleteSystem;
    }

    @SerializedName("registration")
    public SystemRegistration registration;

    public SystemRegistration getRegistration() {
        return this.registration;
    }

    public void setRegistration(SystemRegistration registration) {
        this.registration = registration;
    }

    public class SystemRegistration {

        @SerializedName("state")
        public String state;

        @SerializedName("eula")
        public String eula;

        @SerializedName("statusMsg")
        public String statusMsg;
    }

    @SerializedName("test")
    public SystemTest test;

    public SystemTest getTest() {
        return this.test;
    }

    public void setTest(SystemTest test) {
        this.test = test;
    }

    public class SystemTest {

        @SerializedName("list")
        public ArrayList<SystemTestList> list;

        public class SystemTestList {

            @SerializedName("test")
            public SystemTestListTest test;

            public class SystemTestListTest {

                @SerializedName("tid")
                public Integer tid;

                @SerializedName("explanation")
                public String explanation;

                @SerializedName("name")
                public String name;
            }

            @SerializedName("id")
            public Integer id;
        }

        @SerializedName("szTest")
        public Integer szTest;
    }

    @SerializedName("config")
    public SystemConfig config;

    public SystemConfig getConfig() {
        return this.config;
    }

    public void setConfig(SystemConfig config) {
        this.config = config;
    }

    public class SystemConfig {

        @SerializedName("name")
        public String name;

        @SerializedName("language")
        public String language;

        @SerializedName("centralMode")
        public Boolean centralMode;

        @SerializedName("temperatureUnit")
        public TemperatureUnit temperatureUnit;

        @SerializedName("circulateTime")
        public Float circulateTime;

        @SerializedName("humidificationMode")
        public HumidificationMode humidificationMode;

        @SerializedName("enhancedDehumidificationOvercoolingC")
        public Integer enhancedDehumidificationOvercoolingC;

        @SerializedName("ventilationMode")
        public String ventilationMode;

        @SerializedName("writeAccess")
        public WriteAccess writeAccess;

        @SerializedName("enhancedDehumidificationOvercoolingF")
        public Integer enhancedDehumidificationOvercoolingF;

        @SerializedName("lccGroupId")
        public Integer lccGroupId;

        @SerializedName("allergenDefender")
        public Boolean allergenDefender;

        @SerializedName("options")
        public SystemConfigOptions options;

        public class SystemConfigOptions {

            @SerializedName("enhancedDehumidificationPidat")
            public Boolean enhancedDehumidificationPidat;

            @SerializedName("indoorUnitType")
            public IndoorUnit indoorUnitType;

            @SerializedName("indoorUnitStaging")
            public Integer indoorUnitStaging;

            @SerializedName("humidifierType")
            public HumidifierUnit humidifierType;

            @SerializedName("enhancedDehumidificationOvercoolingF")
            public SystemConfigOptionsEnhancedDehumidificationOvercooling enhancedDehumidificationOvercoolingF;

            @SerializedName("enhancedDehumidificationOvercoolingC")
            public SystemConfigOptionsEnhancedDehumidificationOvercooling enhancedDehumidificationOvercoolingC;

            public class SystemConfigOptionsEnhancedDehumidificationOvercooling {

                @SerializedName("range")
                public SystemConfigOptionsEnhancedDehumidificationOvercoolingRange range;

                public class SystemConfigOptionsEnhancedDehumidificationOvercoolingRange {

                    @SerializedName("enable")
                    public Boolean enable;

                    @SerializedName("min")
                    public Integer min;

                    @SerializedName("max")
                    public Integer max;

                    @SerializedName("def")
                    public Integer def;

                    @SerializedName("unit")
                    public String unit;

                    @SerializedName("inc")
                    public Integer inc;
                }
            }

            @SerializedName("outdoorUnitCommunicating")
            public Boolean outdoorUnitCommunicating;

            @SerializedName("pureAir")
            public SystemConfigOptionsPureAir pureAir;

            public class SystemConfigOptionsPureAir {

                @SerializedName("unitType")
                public PureAirUnit unitType;
            }

            @SerializedName("ventilation")
            public SystemConfigOptionsVentilation ventilation;

            public class SystemConfigOptionsVentilation {

                @SerializedName("unitType")
                public VentilationUnit unitType;

                @SerializedName("controlMode")
                public VentilationControlMode controlMode;
            }

            @SerializedName("productType")
            public String productType;

            @SerializedName("outdoorUnitStaging")
            public Integer outdoorUnitStaging;

            @SerializedName("outdoorUnitType")
            public OutdoorUnit outdoorUnitType;

            @SerializedName("dehumidifierType")
            public DeHumidifierUnit dehumidifierType;
        }

        @SerializedName("dehumidificationMode")
        public DeHumidificationMode dehumidificationMode;
    }

    @SerializedName("ports")
    public ArrayList<SystemPorts> ports;

    public ArrayList<SystemPorts> getPorts() {
        return this.ports;
    }

    public void setPorts(ArrayList<SystemPorts> ports) {
        this.ports = ports;
    }

    public class SystemPorts {

        @SerializedName("id")
        public Integer id;

        @SerializedName("port")
        public SystemPortsPort port;

        public class SystemPortsPort {

            @SerializedName("name")
            public String name;

            @SerializedName("boardId")
            public String boardId;

            @SerializedName("controllerSerialNumber")
            public String controllerSerialNumber;

            @SerializedName("enabled")
            public Boolean enabled;

            @SerializedName("valid")
            public Boolean valid;

            @SerializedName("address")
            public String address;

            @SerializedName("partNumber")
            public String partNumber;

            @SerializedName("swRevision")
            public String swRevision;

            @SerializedName("type")
            public String type;

            @SerializedName("controllerModelNumber")
            public String controllerModelNumber;
        }
    }

    @SerializedName("publisher")
    public Publisher publisher;
    // Class Publisher defined in Publisher.java file

    public Publisher getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @SerializedName("internalStatus")
    public SystemInternalStatus internalStatus;

    public SystemInternalStatus getInternalStatus() {
        return this.internalStatus;
    }

    public void setInternalStatus(SystemInternalStatus internalStatus) {
        this.internalStatus = internalStatus;
    }

    public class SystemInternalStatus {

        @SerializedName("pureAir")
        public SystemInternalStatusPureAir pureAir;

        public class SystemInternalStatusPureAir {

            @SerializedName("pressureSensorStatus")
            public Boolean pressureSensorStatus;

            @SerializedName("uvSensorCalibrationStatus")
            public Boolean uvSensorCalibrationStatus;

            @SerializedName("remainingPurifierLamp")
            public Integer remainingPurifierLamp;

            @SerializedName("doNotPersist")
            public Boolean doNotPersist;

            @SerializedName("uvSensorStatus")
            public Boolean uvSensorStatus;

            @SerializedName("pressureSensorCalibrationStatus")
            public Boolean pressureSensorCalibrationStatus;

            @SerializedName("remainingFilterLife")
            public Integer remainingFilterLife;

            @SerializedName("uvLightStatus")
            public Boolean uvLightStatus;
        }

        @SerializedName("ventilation")
        public Publisher ventilation;

        public class SystemInternalStatusVentilation {

            @SerializedName("currentCfm")
            public Integer currentCfm;

            @SerializedName("doNotPersist")
            public Boolean doNotPersist;

            @SerializedName("totalAirVolume")
            public Integer totalAirVolume;

            @SerializedName("equipment")
            public String equipment;
        }

        @SerializedName("writeAccess")
        public WriteAccess writeAccess;

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;

        @SerializedName("capacityPrognostics")
        public SystemInternalStatusCapacityPrognostics capacityPrognostics;

        public class SystemInternalStatusCapacityPrognostics {

            @SerializedName("persistenceCountThreshold")
            public Integer persistenceCountThreshold;

            @SerializedName("persistentThreshold")
            public Integer persistentThreshold;

            @SerializedName("doNotPersist")
            public Boolean doNotPersist;

            @SerializedName("isValid")
            public Boolean isValid;

            @SerializedName("filterGain")
            public Integer filterGain;

            @SerializedName("alertThreshold")
            public Integer alertThreshold;
        }

        @SerializedName("configOverrides")
        public SystemInternalStatusConfigOverrides configOverrides;

        public class SystemInternalStatusConfigOverrides {

            @SerializedName("centralMode")
            public Boolean centralMode;

            @SerializedName("writeAccess")
            public WriteAccess writeAccess;

            @SerializedName("doNotPersist")
            public Boolean doNotPersist;
        }
    }

    public System() {
    }
}
