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

import com.google.gson.annotations.SerializedName;

/**
 * Request model for the setting HVAC Schedule Set Points
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class SetSchedulePeriodSetPointRequest {

    @SerializedName("schedules")
    public ArrayList<ScheduleList> schedules = null;

    public SetSchedulePeriodSetPointRequest(Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF, Float spC,
            Integer husp, Integer desp, Integer scheduleId) {
        this.schedules = new ArrayList<ScheduleList>(
                List.of(new ScheduleList(hspF, cspC, hspC, cspF, spF, spC, husp, desp, scheduleId)));
    }

    private class ScheduleList {

        @SerializedName("id")
        public Integer id;

        @SerializedName("schedule")
        public Schedule schedule;

        public ScheduleList(Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF, Float spC, Integer husp,
                Integer desp, Integer scheduleId) {
            this.id = scheduleId;
            this.schedule = new Schedule(hspF, cspC, hspC, cspF, spF, spC, husp, desp);
        }

        private class Schedule {

            @SerializedName("periods")
            public ArrayList<PeriodList> periods;

            public Schedule(Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF, Float spC, Integer husp,
                    Integer desp) {

                this.periods = new ArrayList<PeriodList>(
                        List.of(new PeriodList(hspF, cspC, hspC, cspF, spF, spC, husp, desp)));
            }

            private class PeriodList {

                @SerializedName("id")
                public Integer id;

                @SerializedName("period")
                public Period period;

                public PeriodList(Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF, Float spC,
                        Integer husp, Integer desp) {

                    this.id = 0;
                    this.period = new Period(hspF, cspC, hspC, cspF, spF, spC, husp, desp);
                }

                private class Period {

                    @SerializedName("hsp")
                    public Integer hspF;

                    @SerializedName("cspC")
                    public Float cspC;

                    @SerializedName("hspC")
                    public Float hspC;

                    @SerializedName("csp")
                    public Integer cspF;

                    @SerializedName("sp")
                    public Integer spF;

                    @SerializedName("spC")
                    public Float spC;

                    @SerializedName("husp")
                    public Integer husp;

                    @SerializedName("desp")
                    public Integer desp;

                    public Period(Integer hspF, Float cspC, Float hspC, Integer cspF, Integer spF, Float spC,
                            Integer husp, Integer desp) {

                        this.hspF = hspF;
                        this.cspC = cspC;
                        this.hspC = hspC;
                        this.cspF = cspF;
                        this.spF = spF;
                        this.spC = spC;
                        this.husp = husp;
                        this.desp = desp;
                    }
                }
            }
        }
    }
}
