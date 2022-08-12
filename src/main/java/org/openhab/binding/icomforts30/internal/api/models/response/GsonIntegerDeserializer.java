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

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Custom deserializer for Integer
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class GsonIntegerDeserializer implements JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Integer result = null;
        try {
            result = jsonElement.getAsInt();
        } catch (NumberFormatException e) {
            return result;
        }
        return result;
    }
}
