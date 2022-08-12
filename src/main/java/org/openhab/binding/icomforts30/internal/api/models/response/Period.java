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
 * Response model for the period information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class Period {

    @SerializedName("systemMode")
    public HVACMode systemMode;

    @SerializedName("fanMode")
    public FANMode fanMode;

    @SerializedName("husp")
    public Integer husp;

    @SerializedName("away")
    public Boolean away;

    @SerializedName("sp")
    public Integer spF;

    @SerializedName("desp")
    public Integer desp;

    @SerializedName("csp")
    public Integer cspF;

    @SerializedName("hsp")
    public Integer hspF;

    @SerializedName("hspC")
    public Float hspC;

    @SerializedName("startTime")
    public Integer startTime;

    @SerializedName("humidityMode")
    public HUMIDMode humidityMode;

    @SerializedName("spC")
    public Float spC;

    @SerializedName("cspC")
    public Float cspC;

    @SerializedName("isCspChanged")
    public Boolean isCspChanged;

    @SerializedName("isHspChanged")
    public Boolean isHspChanged;
}
