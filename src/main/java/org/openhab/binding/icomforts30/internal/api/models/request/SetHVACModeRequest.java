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

import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.HVACMode;

import com.google.gson.annotations.SerializedName;

/**
 * Request model for the setting HVAC mode
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class SetHVACModeRequest {
    @SerializedName("schedules")
    public ArrayList<ScheduleList> schedules = null;

    public SetHVACModeRequest(HVACMode systemMode, Integer scheduleID) {
        this.schedules = new ArrayList<ScheduleList>(List.of(new ScheduleList(systemMode, scheduleID)));
    }

    public class ScheduleList {

        @SerializedName("id")
        public Integer id = null;

        @SerializedName("schedule")
        public Schedule schedule;

        public ScheduleList(HVACMode systemMode, Integer scheduleID) {
            this.id = scheduleID;
            this.schedule = new Schedule(systemMode);
        }

        private class Schedule {

            @SerializedName("periods")
            public ArrayList<PeriodList> periods = null;

            public Schedule(HVACMode systemMode) {
                this.periods = new ArrayList<PeriodList>(List.of(new PeriodList(systemMode)));
            }

            private class PeriodList {

                @SerializedName("id")
                public Integer id = 0;

                @SerializedName("period")
                public Period period = null;

                public PeriodList(HVACMode systemMode) {
                    this.period = new Period(systemMode);
                }

                private class Period {

                    @SerializedName("systemMode")
                    public HVACMode systemMode = null;

                    public Period(HVACMode systemMode) {
                        this.systemMode = systemMode;
                    }
                }
            }
        }
    }
}
