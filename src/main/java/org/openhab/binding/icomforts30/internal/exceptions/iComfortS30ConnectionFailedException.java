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

package org.openhab.binding.icomforts30.internal.exceptions;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * Thrown if connection to HVAC fails
 * 
 * @author Konstantin Panchenko - Initial contribution
 */

@SuppressWarnings("serial")
@NonNullByDefault
public class iComfortS30ConnectionFailedException extends iComfortS30Exception {

    private static final long serialVersionUID = 2221L;

    public iComfortS30ConnectionFailedException() {
        super();
    }

    public iComfortS30ConnectionFailedException(String message) {
        super(message);
    }

    public iComfortS30ConnectionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
