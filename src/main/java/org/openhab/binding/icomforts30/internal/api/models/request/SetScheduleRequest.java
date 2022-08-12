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
 * Request model for the setting HVAC Schedule
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class SetScheduleRequest {

    @SerializedName("zones")
    public ArrayList<ZoneList> zones = null;

    public SetScheduleRequest(Integer zoneID, Integer scheduleID) {
        this.zones = new ArrayList<ZoneList>(List.of(new ZoneList(zoneID, scheduleID)));
    }

    private class ZoneList {
        @SerializedName("id")
        public Integer ID = null;

        @SerializedName("config")
        public Config config = null;

        public ZoneList(Integer zoneID, Integer scheduleID) {
            this.ID = zoneID;
            this.config = new Config(scheduleID);
        }

        public class Config {

            @SerializedName("scheduleId")
            public Integer scheduleID = null;

            public Config(Integer scheduleID) {
                this.scheduleID = scheduleID;
            }
        }
    }
}
