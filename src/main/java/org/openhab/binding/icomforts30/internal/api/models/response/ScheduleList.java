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
 * Response model for the schedule list information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class ScheduleList {

    @SerializedName("id")
    public Integer id;

    @SerializedName("writeAccess")
    public WriteAccess writeAccess;

    @SerializedName("maxItems")
    public Integer maxItems;

    @SerializedName("schedule")
    public Schedule schedule;
    // Class Schedule defined in Schedule.java file

    @SerializedName("publisher")
    public Publisher publisher;
    // Class Publisher defined in Publisher.java file
}
