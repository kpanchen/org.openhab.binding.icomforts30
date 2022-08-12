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
 * Command model for the sending command to the system
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class CommandData {

    @SerializedName("MessageType")
    public String messageType = "Command";
    @SerializedName("SenderID")
    public String senderID = null;
    @SerializedName("MessageID")
    public String messageID = null;
    @SerializedName("TargetID")
    public String targetID = null;
    @SerializedName("Data")
    public Object data = null;

    public CommandData() {
    }

    public CommandData(String senderID, String messageID, String targetID, Object data) {
        this.senderID = senderID;
        this.messageID = messageID;
        this.targetID = targetID;
        this.data = data;
    }
}
