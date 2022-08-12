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

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the equipment information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class EquipmentList {

    @SerializedName("id")
    public Integer id;

    @SerializedName("equipment")
    public Equipment equipment;

    public class Equipment {

        @SerializedName("szDiagnostics")
        public Integer szDiagnostics;

        @SerializedName("features")
        public ArrayList<FeatureList> features;

        public class FeatureList {

            @SerializedName("id")
            public Integer id;

            @SerializedName("feature")
            public Feature feature;

            public class Feature {

                @SerializedName("name")
                public String name;

                @SerializedName("format")
                public String format;

                @SerializedName("szValues")
                public Integer szValues;

                @SerializedName("values")
                public ArrayList<Value> values;

                public class Value {

                    @SerializedName("id")
                    public Integer id;

                    @SerializedName("value")
                    public String value;
                }

                @SerializedName("fid")
                public Integer fid;

                @SerializedName("unit")
                public String unit;
            }
        }

        @SerializedName("parameters")
        public ArrayList<ParameterList> parameters;

        public class ParameterList {

            @SerializedName("id")
            public Integer id;

            @SerializedName("parameter")
            public Parameter parameter;

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

                @SerializedName("unit")
                public String unit;

                @SerializedName("string")
                public StringDescriptor stringDescriptor;

                public class StringDescriptor {

                    @SerializedName("max")
                    public String max;
                }

                @SerializedName("range")
                public RangeDescriptor rangeDescriptor;

                public class RangeDescriptor {

                    @SerializedName("max")
                    public String max;

                    @SerializedName("min")
                    public String min;

                    @SerializedName("inc")
                    public String inc;
                }

                @SerializedName("radio")
                public RadioDescriptor radioDescriptor;

                public class RadioDescriptor {

                    @SerializedName("max")
                    public String max;

                    @SerializedName("texts")
                    public ArrayList<Texts> texts;

                    public class Texts {

                        @SerializedName("id")
                        public Integer id;

                        @SerializedName("text")
                        public String text;
                    }
                }
            }
        }

        @SerializedName("szFeatures")
        public Integer szFeatures;

        @SerializedName("szParameters")
        public Integer szParameters;

        @SerializedName("diagnostics")
        public ArrayList<DiagnosticList> diagnostics;

        public class DiagnosticList {

            @SerializedName("id")
            public Integer id;

            @SerializedName("diagnostic")
            public Diagnostic diagnostic;

            public class Diagnostic {

                @SerializedName("name")
                public String name;

                @SerializedName("value")
                public String value;

                @SerializedName("valid")
                public Boolean valid;

                @SerializedName("unit")
                public String unit;
            }
        }

        @SerializedName("equipType")
        public Integer equipType;
    }

    @SerializedName("publisher")
    public Publisher publisher;
    // Class Publisher defined in Publisher.java file
}
