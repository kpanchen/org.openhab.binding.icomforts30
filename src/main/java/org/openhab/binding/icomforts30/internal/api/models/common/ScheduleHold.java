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

package org.openhab.binding.icomforts30.internal.api.models.common;

import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.PeriodExceptionType;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.PeriodExpirationMode;

import com.google.gson.annotations.SerializedName;

/**
 * Response and request model for the Schedule Hold information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class ScheduleHold {
    @SerializedName("scheduleId")
    public Integer scheduleID;

    @SerializedName("exceptionType")
    public PeriodExceptionType exceptionType;

    @SerializedName("enabled")
    public Boolean enabled;

    // Not sure why this is string and other values
    @SerializedName("expiresOn")
    public String expiresOn;

    @SerializedName("expirationMode")
    public PeriodExpirationMode expirationMode;

    public ScheduleHold(PeriodExceptionType exceptionType, Boolean scheduleHold, String expiresOn,
            PeriodExpirationMode expirationMode, Integer scheduleID) {

        this.exceptionType = PeriodExceptionType.HOLD;
        this.enabled = scheduleHold;
        this.expiresOn = "0";
        this.expirationMode = PeriodExpirationMode.NEXTPERIOD;
        this.scheduleID = scheduleID;
    }
}
