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

import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.HUMIDMode;

import com.google.gson.annotations.SerializedName;

/**
 * Request model for the setting HVAC humidity mode
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class SetHumidityModeRequest {
    @SerializedName("schedules")
    public ArrayList<ScheduleList> schedules = null;

    public SetHumidityModeRequest(HUMIDMode humidMode, Integer scheduleID) {
        this.schedules = new ArrayList<ScheduleList>(List.of(new ScheduleList(humidMode, scheduleID)));
    }

    public class ScheduleList {

        @SerializedName("id")
        public Integer id = null;

        @SerializedName("schedule")
        public Schedule schedule;

        public ScheduleList(HUMIDMode humidMode, Integer scheduleID) {
            this.id = scheduleID;
            this.schedule = new Schedule(humidMode);
        }

        private class Schedule {

            @SerializedName("periods")
            public ArrayList<PeriodList> periods = null;

            public Schedule(HUMIDMode humidMode) {
                this.periods = new ArrayList<PeriodList>(List.of(new PeriodList(humidMode)));
            }

            private class PeriodList {

                @SerializedName("id")
                public Integer id = 0;

                @SerializedName("period")
                public Period period = null;

                public PeriodList(HUMIDMode humidMode) {
                    this.period = new Period(humidMode);
                }

                private class Period {

                    @SerializedName("humidityMode")
                    public HUMIDMode humidMode = null;

                    public Period(HUMIDMode humidMode) {
                        this.humidMode = humidMode;
                    }
                }
            }
        }
    }
}
