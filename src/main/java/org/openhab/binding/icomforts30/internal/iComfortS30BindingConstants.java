/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
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

package org.openhab.binding.icomforts30.internal;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link icomforts30BindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Konstantin Panchenko - Initial contribution
 */

@NonNullByDefault
public class iComfortS30BindingConstants {

    private static final String BINDING_ID = "icomforts30";

    // Pre-generated Application ID
    public static final String APPLICATION_ID = "mapp079372367644467046827099";

    // List of all Thing Type UIDs
    // public static final ThingTypeUID THING_TYPE_ICOMFORT_ACCOUNT = new ThingTypeUID(BINDING_ID, "account"); //Cloud
    // account is not supported at the moment
    public static final ThingTypeUID THING_TYPE_ICOMFORT_THERMOSTAT = new ThingTypeUID(BINDING_ID, "thermostat");
    public static final ThingTypeUID THING_TYPE_ICOMFORT_ZONE = new ThingTypeUID(BINDING_ID, "zone");
    public static final ThingTypeUID THING_TYPE_ICOMFORT_SYSTEM = new ThingTypeUID(BINDING_ID, "system");

    // List of all Channel ids
    // Read-Only channels
    public static final String ZONE_TEMPERATURE_CHANNEL = "Temperature";
    public static final String ZONE_HUMIDITY_CHANNEL = "Humidity";
    public static final String ZONE_SYSTEM_STATUS_CHANNEL = "SystemStatus";
    public static final String ZONE_FAN_STATUS_CHANNEL = "FanStatus";
    public static final String ZONE_HUMIDITY_STATUS_CHANNEL = "HumidityStatus";
    public static final String SYSTEM_OUTSIDE_TEMPERATURE_CHANNEL = "OutsideTemperature";
    // Read-Write channels
    public static final String ZONE_OPERATION_MODE_CHANNEL = "OperationMode";
    public static final String ZONE_HUMIDITY_MODE_CHANNEL = "HumidityMode";
    public static final String SYSTEM_AWAY_MODE_CHANNEL = "AwayMode";

    public static final String ZONE_FAN_MODE_CHANNEL = "FanMode";
    public static final String ZONE_COOL_SET_POINT_CHANNEL = "CoolSetPoint";
    public static final String ZONE_HEAT_SET_POINT_CHANNEL = "HeatSetPoint";
    public static final String ZONE_SET_POINT_CHANNEL = "SetPoint";
    public static final String ZONE_HUMIDITY_SET_POINT_CHANNEL = "HumiditySetPoint";
    public static final String ZONE_DEHUMIDIFICATION_SET_POINT_CHANNEL = "DehumidificationSetPoint";

    public static final String TCS_PROPERTY_SYSTEM_NAME = "systemName";
    public static final String TCS_PROPERTY_PRODUCT_TYPE = "productType";
    public static final String TCS_PROPERTY_LANGUAGE = "language";
    // public static final String TCS_PROPERTY_GATEWAY_SN = "gatewaySerialNumber";
    // public static final String TCS_PROPERTY_FIRMWARE_VERSION = "firmwareVersion";

    // List of all addressable things in OH = SUPPORTED_DEVICE_THING_TYPES_UIDS + the virtual bridge
    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections
            .unmodifiableSet(Stream.of(/* THING_TYPE_ICOMFORT_ACCOUNT, */ THING_TYPE_ICOMFORT_THERMOSTAT,
                    THING_TYPE_ICOMFORT_ZONE, THING_TYPE_ICOMFORT_SYSTEM).collect(Collectors.toSet()));
}
