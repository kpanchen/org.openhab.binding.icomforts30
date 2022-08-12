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
 * Request model for the requesting data from the system
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class AdditionalParameters {
    @SerializedName("JSONPath")
    public String JSONPath = null;

    public AdditionalParameters() {
    }

    public AdditionalParameters(String JSONPath) {
        this.JSONPath = JSONPath;
    }
}
