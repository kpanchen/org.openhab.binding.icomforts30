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

package org.openhab.binding.icomforts30.internal.handler;

import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.ManualAway;
import org.openhab.binding.icomforts30.internal.api.models.response.Occupancy;
import org.openhab.binding.icomforts30.internal.api.models.response.System;
import org.openhab.binding.icomforts30.internal.iComfortS30BindingConstants;
import org.openhab.core.library.types.StringType;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link iComfortS30SystemHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Konstantin Panchenko - Initial contribution
 */
public class iComfortS30SystemHandler extends BaseiComfortS30Handler {

    private final Logger logger = LoggerFactory.getLogger(iComfortS30SystemHandler.class);

    private System systemInfo;
    private Occupancy occupancyInfo;

    public iComfortS30SystemHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    public void update(System systemInfo, Occupancy occupancyInfo) {
        this.systemInfo = systemInfo;
        this.occupancyInfo = occupancyInfo;

        updateiComfortS30ThingStatus(ThingStatus.ONLINE);

        if (occupancyInfo.manualAway) {
            updateState(iComfortS30BindingConstants.SYSTEM_AWAY_MODE_CHANNEL,
                    new StringType(ManualAway.AWAY.toString()));
        } else {
            updateState(iComfortS30BindingConstants.SYSTEM_AWAY_MODE_CHANNEL,
                    new StringType(ManualAway.HOME.toString()));
        }
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("Executing command {}", command.toString());

        if (command == RefreshType.REFRESH) {
            update(systemInfo, occupancyInfo);
        } else {
            iComfortS30ThermostatBridgeHandler bridge = getiComfortS30Bridge();
            String channelId = channelUID.getId();
            if (iComfortS30BindingConstants.SYSTEM_AWAY_MODE_CHANNEL.equals(channelId)) {
                bridge.setSystemManualAwayMode(CustomTypes.ManualAway.valueOf(command.toString()).getManualAwayValue());
            }

            // cancel_smart_away
            // enable_smart_away

        }
    }
}
