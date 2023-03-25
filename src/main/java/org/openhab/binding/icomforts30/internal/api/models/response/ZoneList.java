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

import java.util.ArrayList;

import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.*;
import org.openhab.binding.icomforts30.internal.api.models.common.ScheduleHold;

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the zone information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class ZoneList {

    @SerializedName("id")
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("publisher")
    public Publisher publisher;

    public Publisher getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @SerializedName("status")
    public ZoneStatus status;

    public ZoneStatus getStatus() {
        return this.status;
    }

    public void setStatus(ZoneStatus status) {
        this.status = status;
    }

    @SerializedName("maxItems")
    public Integer maxItems;

    public Integer getMaxItems() {
        return this.maxItems;
    }

    public void setMaxItems(Integer maxItems) {
        this.maxItems = maxItems;
    }

    @SerializedName("writeAccess")
    public WriteAccess writeAccess;

    public WriteAccess getWriteAccess() {
        return this.writeAccess;
    }

    public void setWriteAccess(WriteAccess writeAccess) {
        this.writeAccess = writeAccess;
    }

    @SerializedName("config")
    public ZoneConfig config;

    public ZoneConfig getConfig() {
        return this.config;
    }

    public void setConfig(ZoneConfig config) {
        // this.config = config;
        if (config.name != null) {
            this.config.name = config.name;
        }
        if (config.name != null) {
            this.config.writeAccess = config.writeAccess;
        }
        if (config.name != null) {
            this.config.scheduleId = config.scheduleId;
        }
        if (config.name != null) {
            this.config.scheduleHold = config.scheduleHold;
        }
        if (config.name != null) {
            this.config.name = config.name;
        }
        if (config.name != null) {
            this.config.name = config.name;
        }
    }

    @SerializedName("schedule")
    public Schedule schedule;
    // Class Period defined in Schedule.java file

    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @SerializedName("sensors")
    public ArrayList<ZoneSensors> sensors;

    public ArrayList<ZoneSensors> getSensors() {
        return this.sensors;
    }

    public void setSensors(ArrayList<ZoneSensors> sensors) {
        this.sensors = sensors;
    }

    @SerializedName("inputs")
    public ArrayList<ZoneInputs> inputs;

    public ArrayList<ZoneInputs> getInputs() {
        return this.inputs;
    }

    public void setInputs(ArrayList<ZoneInputs> inputs) {
        this.inputs = inputs;
    }

    public ZoneList() {
    }

    public class ZoneStatus {

        @SerializedName("fan")
        public Boolean fan;

        @SerializedName("allergenDefender")
        public Boolean allergenDefender;

        @SerializedName("humidity")
        public Integer humidity;

        @SerializedName("temperature")
        public Float temperature;

        @SerializedName("damper")
        public Integer damper;

        @SerializedName("heatCoast")
        public Boolean heatCoast;

        @SerializedName("defrost")
        public Boolean defrost;

        @SerializedName("humidityStatus")
        public Status humidityStatus;

        @SerializedName("period")
        public Period period;
        // Class Period defined in Period.java file

        @SerializedName("humOperation")
        public HUMIDOperation humOperation;

        @SerializedName("scheduleExceptionIds")
        public ArrayList<scheduleExceptionId> scheduleExceptionIds;

        @SerializedName("balancePoint")
        public String balancePoint;

        @SerializedName("tempOperation")
        public HVACStatus tempOperation;

        @SerializedName("ventilation")
        public Boolean ventilation;

        @SerializedName("demand")
        public Integer demand;

        @SerializedName("aux")
        public Boolean aux;

        @SerializedName("coolCoast")
        public Boolean coolCoast;

        @SerializedName("ssr")
        public Boolean ssr;

        @SerializedName("temperatureStatus")
        public Status temperatureStatus;

        @SerializedName("temperatureC")
        public Float temperatureC;

        public class scheduleExceptionId {

            @SerializedName("scheduleId")
            public Integer scheduleId;

            @SerializedName("id")
            public Integer id;
        }
    }

    public class ZoneConfig {

        @SerializedName("name")
        public String name;

        @SerializedName("writeAccess")
        public WriteAccess writeAccess;

        @SerializedName("scheduleId")
        public Integer scheduleId;

        @SerializedName("scheduleHold")
        public ScheduleHold scheduleHold;

        @SerializedName("temperatureDeadband")
        public Integer temperatureDeadband;

        @SerializedName("minCspC")
        public Float minCspC;

        @SerializedName("minHumSp")
        public Integer minHumSp;

        @SerializedName("heatingOption")
        public Boolean heatingOption;

        @SerializedName("maxHsp")
        public Integer maxHsp;

        @SerializedName("temperatureDeadbandC")
        public Float temperatureDeadbandC;

        @SerializedName("humidificationOption")
        public Boolean humidificationOption;

        @SerializedName("coolingOption")
        public Boolean coolingOption;

        @SerializedName("singleSetpointAvailable")
        public Boolean singleSetpointAvailable;

        @SerializedName("humidityDeadband")
        public Integer humidityDeadband;

        @SerializedName("humidificationNotAdjustable")
        public Boolean humidificationNotAdjustable;

        @SerializedName("minHspC")
        public Float minHspC;

        @SerializedName("minCsp")
        public Integer minCsp;

        @SerializedName("maxCspC")
        public Float maxCspC;

        @SerializedName("emergencyHeatingOption")
        public Boolean emergencyHeatingOption;

        @SerializedName("maxHumSp")
        public Integer maxHumSp;

        @SerializedName("maxCsp")
        public Integer maxCsp;

        @SerializedName("dehumidificationNotAdjustable")
        public Boolean dehumidificationNotAdjustable;

        @SerializedName("minDehumSp")
        public Integer minDehumSp;

        @SerializedName("dehumidificationOption")
        public Boolean dehumidificationOption;

        @SerializedName("maxDehumSp")
        public Integer maxDehumSp;

        @SerializedName("minHsp")
        public Integer minHsp;

        @SerializedName("maxHspC")
        public Float maxHspC;
    }

    public class ZoneSensors {

        @SerializedName("id")
        public Integer id;

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;

        @SerializedName("writeAccess")
        public WriteAccess writeAccess;

        @SerializedName("tempStatus")
        public String tempStatus;

        @SerializedName("hum")
        public Float hum;

        @SerializedName("tsense")
        public Float tsense;

        @SerializedName("humStatus")
        public String humStatus;

        @SerializedName("tant")
        public Float tant;
    }

    public class ZoneInputs {

        @SerializedName("id")
        public Integer id;

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;
    }
}
