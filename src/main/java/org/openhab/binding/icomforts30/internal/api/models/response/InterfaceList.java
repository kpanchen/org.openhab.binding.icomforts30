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

import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.*;

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the interface information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class InterfaceList {

    @SerializedName("publisher")
    public Publisher publisher;
    // Class Publisher defined in Publisher.java file

    @SerializedName("Info")
    public Info info;

    public class Info {

        @SerializedName("status")
        public NetworkStatus status;

        @SerializedName("APDetails")
        public APDetails apDetails;

        @SerializedName("diagnostics")
        public Diagnostics diagnostics;

        @SerializedName("WiFiStatus")
        public WiFiStatus wifiStatus;

        public Info() {
        }

        public class Diagnostics {

            @SerializedName("ssid")
            public String ssid;

            @SerializedName("bssid")
            public String bssid;

            @SerializedName("txByteCount")
            public Long txByteCount;

            @SerializedName("rxByteCount")
            public Long rxByteCount;

            @SerializedName("ip4addr")
            public String ip4addr;

            @SerializedName("txPacketCount")
            public Long txPacketCount;

            @SerializedName("rxPacketCount")
            public Long rxPacketCount;
        }

        public class NetworkStatus {

            @SerializedName("macAddr")
            public String macAddr;

            @SerializedName("ssid")
            public String ssid;

            @SerializedName("ip")
            public String ip;

            @SerializedName("router")
            public String router;

            @SerializedName("networkStatus")
            public State networkStatus;

            @SerializedName("channel")
            public Integer channel;

            @SerializedName("dns")
            public String dns;

            @SerializedName("dns2")
            public String dns2;

            @SerializedName("subnetMask")
            public String subnetMask;

            @SerializedName("bitRate")
            public Long bitRate;

            @SerializedName("speed")
            public String speed;

            @SerializedName("rssi")
            public Integer rssi;
        }

        public class APDetails {

            @SerializedName("rssi")
            public Integer rssi;

            @SerializedName("security")
            public String security;

            @SerializedName("password")
            public String password;

            @SerializedName("ssid")
            public String ssid;

            @SerializedName("bssid")
            public String bssid;
        }

        public class WiFiStatus {

            @SerializedName("status")
            public String status;
        }
    }

    @SerializedName("id")
    public Integer id;

    public InterfaceList() {
    }
}
