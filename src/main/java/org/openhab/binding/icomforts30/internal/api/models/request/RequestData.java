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

//import org.openhab.binding.icomfortwifi.internal.api.models.response.ZoneStatus;

import com.google.gson.annotations.SerializedName;

/**
 * Request model for the requesting data from the system
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class RequestData {
    @SerializedName("MessageType")
    public String messageType = "RequestData";
    @SerializedName("SenderID")
    public String senderID = null;
    @SerializedName("MessageID")
    public String messageID = null;
    @SerializedName("TargetID")
    public String targetID = null;
    @SerializedName("AdditionalParameters")
    public AdditionalParameters additionalParameters = null;

    public RequestData() {
    }

    public RequestData(String senderID, String messageID, String targetID, AdditionalParameters additionalParameters) {
        this.senderID = senderID;
        this.messageID = messageID;
        this.targetID = targetID;
        this.additionalParameters = additionalParameters;
    }
}
