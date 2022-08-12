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
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Custom deserializer for date information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class GsonDateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String s = json.getAsJsonPrimitive().getAsString();
        StringBuilder sb = new StringBuilder(s);
        sb.setLength(13);
        String sp = sb.toString().replaceAll("[^0-9]", "0");
        long l = Long.parseLong(sp);
        Date d = new Date(l);
        return d;
    }
}
