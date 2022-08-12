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

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the occupancy information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class Occupancy {

    @SerializedName("smartAway")
    public SmartAway smartAway;

    public class SmartAway {

        @SerializedName("status")
        public SmartAwayStatus status;

        public class SmartAwayStatus {

            @SerializedName("state")
            public SAState state;

            @SerializedName("closestBandStatus")
            public Integer closestBandStatus;

            @SerializedName("setpointState")
            public SetpointState setpointState;

            @SerializedName("closestBandParticipant")
            public String closestBandParticipant;
        }

        @SerializedName("participants")
        public ArrayList<Participant> participants;

        public class Participant {

            @SerializedName("r1")
            public Integer r1;

            @SerializedName("lastUpdateTimestamp")
            public String lastUpdateTimestamp;

            @SerializedName("deviceId")
            public String deviceId;

            @SerializedName("currentBand")
            public Integer currentBand;

            @SerializedName("bandStatus")
            public Integer bandStatus;

            @SerializedName("id")
            public Integer id;

            @SerializedName("previousBand")
            public Integer previousBand;
        }

        @SerializedName("config")
        public Config config;

        public class Config {

            @SerializedName("reset")
            public Boolean reset;

            @SerializedName("enabled")
            public Boolean enabled;

            @SerializedName("masterLcc")
            public String masterLcc;

            @SerializedName("writeAccess")
            public WriteAccess writeAccess;

            @SerializedName("cancel")
            public Boolean cancel;
        }

        @SerializedName("update")
        public Update update;

        public class Update {

            @SerializedName("enable")
            public Boolean enable;

            @SerializedName("timestamp")
            public String timestamp;

            @SerializedName("band1Radius")
            public Integer band1Radius;

            @SerializedName("writeAccess")
            public WriteAccess writeAccess;

            @SerializedName("deviceId")
            public String deviceId;

            @SerializedName("participate")
            public Boolean participate;

            @SerializedName("currentBand")
            public Integer currentBand;

            @SerializedName("previousBand")
            public Integer previousBand;
        }
    }

    @SerializedName("writeAccess")
    public WriteAccess writeAccess;

    @SerializedName("manualAway")
    public Boolean manualAway;

    @SerializedName("publisher")
    public Publisher publisher;
    // Class Publisher defined in Publisher.java file
}
