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

package org.openhab.binding.icomforts30.internal.configuration;

/**
 * Contains the common configuration definition of an iComfortS30 Zone Thing
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class iComfortS30ThingConfiguration {
    public Integer id = 0; // Zone ID
    public String name = ""; // Zone name (optional)

    public int getZoneID() {
        return id;
    }

    public String getHeatingZoneName() {
        return name;
    }
}
