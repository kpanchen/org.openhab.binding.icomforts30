/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
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

import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.CountryAbbreviation;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.DealerPermissionToUpdate;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.WriteAccess;

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the home information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class HomeList {

    @SerializedName("dealerPermissionToView")
    public Boolean dealerPermissionToView;

    @SerializedName("name")
    public String name;

    @SerializedName("notifyAlertsToDealer")
    public Boolean notifyAlertsToDealer;

    @SerializedName("address")
    public Address address;

    public class Address {

        @SerializedName("streetAddress1")
        public String streetAddress1;

        @SerializedName("streetAddress2")
        public String streetAddress2;

        @SerializedName("zip")
        public String zip;

        @SerializedName("country")
        public CountryAbbreviation country;

        @SerializedName("longitude")
        public Float longitude;

        @SerializedName("state")
        public String state;

        @SerializedName("latitude")
        public Float latitude;

        @SerializedName("city")
        public String city;

        @SerializedName("publisher")
        public Publisher publisher;
    }

    // ToDo
    @SerializedName("dealerPermissionExpiresAt")
    public Boolean dealerPermissionExpiresAt; // Date

    @SerializedName("writeAccess")
    public WriteAccess writeAccess;

    @SerializedName("systems")
    public ArrayList<HomeSystems> systems;

    public class HomeSystems {

        @SerializedName("sysId")
        public String sysId;

        @SerializedName("id")
        public Integer id;

        @SerializedName("controlModelNumber")
        public String controlModelNumber; // null??
    }

    @SerializedName("maxItems")
    public Integer maxItems;

    @SerializedName("notifyRemindersToDealer")
    public Boolean notifyRemindersToDealer;

    @SerializedName("homeId")
    public Integer homeId;

    @SerializedName("type")
    public String type; // "error"

    @SerializedName("id")
    public Integer id;

    @SerializedName("dealerPermissionToUpdate")
    public DealerPermissionToUpdate dealerPermissionToUpdate;

    @SerializedName("publisher")
    public Publisher publisher;
}
