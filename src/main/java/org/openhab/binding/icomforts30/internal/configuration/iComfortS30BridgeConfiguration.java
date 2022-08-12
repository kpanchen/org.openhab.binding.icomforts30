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

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The {@link icomforts30BridgeConfiguration} class contains fields mapping thing configuration parameters.
 *
 * @author Konstantin Panchenko - Initial contribution
 */

@NonNullByDefault
public class iComfortS30BridgeConfiguration {

    private static final Integer DEFAULT_REFRESH = 30;
    private static final Integer DEFAULT_DELAY = 30;
    private static final Integer DEFAULT_PUMP = 5;
    public static final String HTTP = "http";
    public static final String HTTPS = "https";

    // List of all Thermostat Configuration Properties
    public static final String THERMOSTAT_CONFIG_HOST = "hostname";
    public static final String THERMOSTAT_CONFIG_PORT = "port";
    public static final String THERMOSTAT_CONFIG_PROTOCOL = "protocol";
    public static final String THERMOSTAT_CONFIG_SYSTEM_NAME = "lennoxSystemName";
    public static final String THERMOSTAT_CONFIG_SELF_SIGNED_CERT = "useSelfSignedCertificate";
    public static final String THERMOSTAT_CONFIG_REFRESH_INTERVAL = "refreshInterval";
    public static final String THERMOSTAT_CONFIG_REFRESH_DELAY = "initialRefreshDelay";
    public static final String THERMOSTAT_CONFIG_PUMP_INTERVAL = "pumpInterval";
    public static final String THERMOSTAT_CONFIG_APP_ID = "appID";

    // Only local connections are supported
    private Boolean localconnection = true;
    // Password and Username for cloud connection
    // public String userName = "";
    // public String password = "";

    // Hostname for local connection
    private @Nullable String hostname = "";
    private @Nullable Integer port;
    private String protocol = HTTPS;
    private String lennoxSystemName = "LCC";
    private boolean useSelfSignedCertificate = true;

    private Integer refreshInterval = DEFAULT_REFRESH;
    private Integer initialRefreshDelay = DEFAULT_DELAY;
    private Integer pumpInterval = DEFAULT_PUMP;

    private String appID = "";

    public @Nullable Boolean getLocalConnection() {
        return localconnection;
    }

    public void setLocalConnection(Boolean localconnection) {
        this.localconnection = localconnection;
    }

    public @Nullable String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        Integer thePort = port;
        return (thePort != null) ? thePort.intValue() : HTTPS.equals(protocol) ? 443 : 80;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getPollingInterval() {
        return refreshInterval;
    }

    public void setPollingInterval(int pollingInterval) {
        this.refreshInterval = pollingInterval;
    }

    public int getRefreshDelay() {
        return initialRefreshDelay;
    }

    public void setRefreshDelay(int delayInterval) {
        this.initialRefreshDelay = delayInterval;
    }

    public int getPumpInterval() {
        return pumpInterval;
    }

    public void setPumpInterval(int pumpInterval) {
        this.pumpInterval = pumpInterval;
    }

    public Boolean getUseSelfSignedCertificate() {
        return useSelfSignedCertificate;
    }

    public void setUseSelfSignedCertificat(Boolean useSelfSignedCertificate) {
        this.useSelfSignedCertificate = useSelfSignedCertificate;
    }

    public String getLennoxSystemName() {
        return lennoxSystemName;
    }

    public String getApplicationID() {
        return appID;
    }

    public void setApplicationID(String ApplicationID) {
        this.appID = ApplicationID;
    }
}
