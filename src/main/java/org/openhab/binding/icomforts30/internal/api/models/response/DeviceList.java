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

import java.util.ArrayList;

import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.*;

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the device information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class DeviceList {

    @SerializedName("writeAccess")
    public WriteAccess writeAccess;

    @SerializedName("maxItems")
    public Integer maxItems;

    @SerializedName("device")
    public DeviceStatus device;

    @SerializedName("id")
    public Integer id;

    @SerializedName("publisher")
    public Publisher publisher;

    public DeviceList() {
    }

    public class DeviceStatus {

        @SerializedName("features")
        public ArrayList<Features> features;

        @SerializedName("parameters")
        public ArrayList<Parameters> parameters;

        @SerializedName("szFeatures")
        public Integer szFeatures;

        @SerializedName("szParameters")
        public Integer szParameters;

        @SerializedName("deviceType")
        public Integer deviceType;

        public class Features {

            @SerializedName("id")
            public Integer id;

            @SerializedName("feature")
            public Feature feature;

            public class Feature {

                @SerializedName("name")
                public String name;

                @SerializedName("szValues")
                public Integer szValues;

                @SerializedName("values")
                public ArrayList<Values> values;

                @SerializedName("fid")
                public Integer fid;

                @SerializedName("unit")
                public String unit;

                @SerializedName("format")
                public String format;

                public class Values {

                    @SerializedName("id")
                    public Integer id;

                    @SerializedName("value")
                    public String value;
                }
            }
        }

        public class Parameters {

            @SerializedName("parameter")
            public Parameter parameter;

            @SerializedName("id")
            public Integer id;

            public class Parameter {

                @SerializedName("name")
                public String name;

                @SerializedName("format")
                public String format;

                @SerializedName("pid")
                public Integer pid;

                @SerializedName("defaultValue")
                public String defaultValue;

                @SerializedName("enabled")
                public Boolean enabled;

                @SerializedName("value")
                public String value;

                @SerializedName("descriptor")
                public String descriptor;

                @SerializedName("radio")
                public Radio radio;

                @SerializedName("range")
                public Range range;

                public class Radio {

                    @SerializedName("max")
                    public String max;

                    @SerializedName("texts")
                    public ArrayList<Texts> texts;

                    public class Texts {

                        @SerializedName("text")
                        public String text;

                        @SerializedName("id")
                        public Integer id;
                    }
                }

                public class Range {

                    @SerializedName("max")
                    public Integer max;

                    @SerializedName("min")
                    public Integer min;

                    @SerializedName("inc")
                    public Integer inc;
                }
            }
        }
    }
}
