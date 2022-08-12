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

import com.google.gson.annotations.SerializedName;

/**
 * Request model for retrieving data from the system
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class RetrieveData {
    @SerializedName("Direction")
    public String direction = "Oldest-to-Newest";
    @SerializedName("MessageCount")
    public Integer messageCount = 10;
    @SerializedName("StartTime")
    public Integer startTime = 1;
    @SerializedName("LongPollingTimeout")
    public Integer longPollingTimeout = 15;

    public RetrieveData() {
    }

    public RetrieveData(String direction, Integer messageCount, Integer startTime, Integer longPollingTimeout) {
        this.direction = direction;
        this.messageCount = messageCount;
        this.startTime = startTime;
        this.longPollingTimeout = longPollingTimeout;
    }
}
