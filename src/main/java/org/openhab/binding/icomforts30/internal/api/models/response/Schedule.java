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

import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the schedule information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class Schedule {

    @SerializedName("name")
    public String name;

    @SerializedName("periodCount")
    public Integer periodCount;

    @SerializedName("periods")
    private ArrayList<PeriodList> periods;

    public ArrayList<PeriodList> getPeriods() {
        return this.periods;
    }

    public @Nullable PeriodList getPeriod(Integer iD) {
        for (PeriodList periodElement : periods) {
            if (periodElement.getId() == iD) {
                return periodElement;
            }

        }
        return null;
    }
}
