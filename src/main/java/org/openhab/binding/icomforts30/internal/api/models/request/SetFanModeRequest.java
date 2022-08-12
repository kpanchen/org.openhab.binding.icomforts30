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

import com.google.gson.annotations.SerializedName;

/**
 * Request model for the setting HVAC Fan mode
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class SetFanModeRequest {
    @SerializedName("schedules")
    public ArrayList<ScheduleList> schedules = null;

    public SetFanModeRequest(FANMode fanMode, Integer scheduleID) {
        this.schedules = new ArrayList<ScheduleList>(List.of(new ScheduleList(fanMode, scheduleID)));
    }

    public class ScheduleList {

        @SerializedName("id")
        public Integer id = null;

        @SerializedName("schedule")
        public Schedule schedule;

        public ScheduleList(FANMode fanMode, Integer scheduleID) {
            this.id = scheduleID;
            this.schedule = new Schedule(fanMode);
        }

        private class Schedule {

            @SerializedName("periods")
            public ArrayList<PeriodList> periods = null;

            public Schedule(FANMode fanMode) {
                this.periods = new ArrayList<PeriodList>(List.of(new PeriodList(fanMode)));
            }

            private class PeriodList {

                @SerializedName("id")
                public Integer id = 0;

                @SerializedName("period")
                public Period period = null;

                public PeriodList(FANMode fanMode) {
                    this.period = new Period(fanMode);
                }

                private class Period {

                    @SerializedName("fanMode")
                    public FANMode fanMode = null;

                    public Period(FANMode fanMode) {
                        this.fanMode = fanMode;
                    }
                }
            }
        }
    }
}
