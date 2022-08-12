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

import static org.openhab.binding.icomforts30.internal.iComfortS30BindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.icomforts30.internal.handler.iComfortS30HeatingZoneHandler;
import org.openhab.binding.icomforts30.internal.handler.iComfortS30SystemHandler;
import org.openhab.binding.icomforts30.internal.handler.iComfortS30ThermostatBridgeHandler;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link icomfortS30HandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Konstantin Panchenko - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.icomforts30", service = ThingHandlerFactory.class)
public class iComfortS30HandlerFactory extends BaseThingHandlerFactory {

    private final Logger logger = LoggerFactory.getLogger(iComfortS30HandlerFactory.class);

    // private @NonNullByDefault({}) HttpClient httpClient;

    /**
     * Provides the supported thing types
     */
    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    /**
     * Create handler of things.
     */
    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (thingTypeUID.equals(iComfortS30BindingConstants.THING_TYPE_ICOMFORT_THERMOSTAT)) {
            iComfortS30ThermostatBridgeHandler bridge = new iComfortS30ThermostatBridgeHandler((Bridge) thing);
            // registeriComfortWiFiDiscoveryService(bridge); //No discovery service at the moment
            return bridge;
        } else if (thingTypeUID.equals(iComfortS30BindingConstants.THING_TYPE_ICOMFORT_ZONE)) {
            return new iComfortS30HeatingZoneHandler(thing);
        } else if (thingTypeUID.equals(iComfortS30BindingConstants.THING_TYPE_ICOMFORT_SYSTEM)) {
            return new iComfortS30SystemHandler(thing);
        } else {
            logger.error("ThingHandler not found for {}", thingTypeUID);
        }

        return null;
    }
}
