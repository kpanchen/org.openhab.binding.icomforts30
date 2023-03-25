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

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the period list information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class PeriodList {

    @SerializedName("id")
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("enabled")
    public Boolean enabled;

    @SerializedName("period")
    public Period period;
    // Class Period defined in Period.java file
}
