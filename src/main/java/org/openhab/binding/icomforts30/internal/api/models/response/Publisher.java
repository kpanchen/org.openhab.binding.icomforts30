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
 * Response model for the publisher information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class Publisher {

    @SerializedName("writeAccess")
    public WriteAccess writeAccess;

    @SerializedName("publisherName")
    public String publisherName;

    @SerializedName("doNotPersist")
    public Boolean doNotPersist;

    public Publisher() {
    }
}
