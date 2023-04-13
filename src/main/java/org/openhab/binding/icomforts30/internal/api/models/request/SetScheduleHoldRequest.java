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

package org.openhab.binding.icomforts30.internal.api.models.request;

import java.util.ArrayList;
import java.util.List;

import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.PeriodExceptionType;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.PeriodExpirationMode;
import org.openhab.binding.icomforts30.internal.api.models.common.ScheduleHold;

import com.google.gson.annotations.SerializedName;

/**
 * Request model for the setting HVAC Schedule Hold
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class SetScheduleHoldRequest {

    @SerializedName("zones")
    public ArrayList<ZoneList> zones = null;

    public SetScheduleHoldRequest(Integer zoneID, PeriodExceptionType exceptionType, Boolean scheduleHold,
            String expiresOn, PeriodExpirationMode expirationMode, Integer scheduleID) {
        this.zones = new ArrayList<ZoneList>(
                List.of(new ZoneList(zoneID, exceptionType, scheduleHold, expiresOn, expirationMode, scheduleID)));
    }

    private class ZoneList {
        @SerializedName("id")
        public Integer ID = null;

        @SerializedName("config")
        public Config config = null;

        public ZoneList(Integer zoneID, PeriodExceptionType exceptionType, Boolean scheduleHold, String expiresOn,
                PeriodExpirationMode expirationMode, Integer scheduleID) {
            this.ID = zoneID;
            this.config = new Config(exceptionType, scheduleHold, expiresOn, expirationMode, scheduleID);
        }

        private class Config {

            @SerializedName("scheduleHold")
            public ScheduleHold scheduleHold = null;

            public Config(PeriodExceptionType exceptionType, Boolean scheduleHold, String expiresOn,
                    PeriodExpirationMode expirationMode, Integer scheduleID) {
                this.scheduleHold = new ScheduleHold(exceptionType, scheduleHold, expiresOn, expirationMode,
                        scheduleID);
            }
        }
    }
}
