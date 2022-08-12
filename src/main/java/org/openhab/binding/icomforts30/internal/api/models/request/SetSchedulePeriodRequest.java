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

package org.openhab.binding.icomforts30.internal.api.models.request;

import java.util.ArrayList;
import java.util.List;

import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.FANMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.HUMIDMode;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.HVACMode;

import com.google.gson.annotations.SerializedName;

/**
 * Request model for the setting HVAC Period data
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class SetSchedulePeriodRequest {

    @SerializedName("schedules")
    public ArrayList<ScheduleList> schedules = null;

    public SetSchedulePeriodRequest(Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF, Float spC,
            Integer husp, Integer desp, HUMIDMode humidityMode, HVACMode systemMode, Integer startTime, FANMode fanMode,
            Integer scheduleId) {
        this.schedules = new ArrayList<ScheduleList>(List.of(new ScheduleList(hspF, cspC, hspC, cspF, spF, spC, husp,
                desp, humidityMode, systemMode, startTime, fanMode, scheduleId)));
    }

    private class ScheduleList {

        @SerializedName("id")
        public Integer id;

        @SerializedName("schedule")
        public Schedule schedule;

        public ScheduleList(Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF, Float spC, Integer husp,
                Integer desp, HUMIDMode humidityMode, HVACMode systemMode, Integer startTime, FANMode fanMode,
                Integer scheduleId) {
            this.id = scheduleId;
            this.schedule = new Schedule(hspF, cspC, hspC, cspF, spF, spC, husp, desp, humidityMode, systemMode,
                    startTime, fanMode);
        }

        private class Schedule {

            @SerializedName("periods")
            public ArrayList<PeriodList> periods;

            public Schedule(Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF, Float spC, Integer husp,
                    Integer desp, HUMIDMode humidityMode, HVACMode systemMode, Integer startTime, FANMode fanMode) {

                this.periods = new ArrayList<PeriodList>(List.of(new PeriodList(hspF, cspC, hspC, cspF, spF, spC, husp,
                        desp, humidityMode, systemMode, startTime, fanMode)));
            }

            private class PeriodList {

                @SerializedName("id")
                public Integer id;

                @SerializedName("period")
                public Period period;

                public PeriodList(Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF, Float spC,
                        Integer husp, Integer desp, HUMIDMode humidityMode, HVACMode systemMode, Integer startTime,
                        FANMode fanMode) {

                    this.id = 0;
                    this.period = new Period(hspF, cspC, hspC, cspF, spF, spC, husp, desp, humidityMode, systemMode,
                            startTime, fanMode);
                }

                private class Period {

                    @SerializedName("desp")
                    public Integer desp;

                    @SerializedName("hsp")
                    public Integer hspF;

                    @SerializedName("cspC")
                    public Float cspC;

                    @SerializedName("sp")
                    public Integer spF;

                    @SerializedName("husp")
                    public Integer husp;

                    @SerializedName("humidityMode")
                    public HUMIDMode humidityMode;

                    @SerializedName("systemMode")
                    public HVACMode systemMode;

                    @SerializedName("spC")
                    public Float spC;

                    @SerializedName("hspC")
                    public Float hspC;

                    @SerializedName("csp")
                    public Integer cspF;

                    @SerializedName("startTime")
                    public Integer startTime;

                    @SerializedName("fanMode")
                    public FANMode fanMode;

                    public Period(Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF, Float spC,
                            Integer husp, Integer desp, HUMIDMode humidityMode, HVACMode systemMode, Integer startTime,
                            FANMode fanMode) {

                        this.desp = desp;
                        this.hspF = hspF;
                        this.cspC = cspC;
                        this.spF = spF;
                        this.husp = husp;
                        this.humidityMode = humidityMode;
                        this.systemMode = systemMode;
                        this.spC = spC;
                        this.hspC = hspC;
                        this.cspF = cspF;
                        this.startTime = startTime;
                        this.fanMode = fanMode;
                    }
                }
            }
        }
    }
}
