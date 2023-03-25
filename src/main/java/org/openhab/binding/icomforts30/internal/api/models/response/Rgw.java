package org.openhab.binding.icomforts30.internal.api.models.response;

import java.util.ArrayList;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.WriteAccess;

import com.google.gson.annotations.SerializedName;

public class Rgw {

    @SerializedName("aapState")
    public String aapState;

    @SerializedName("IsDealerPublished")
    public Boolean IsDealerPublished;

    @SerializedName("writeAccess")
    public WriteAccess writeAccess;

    @SerializedName("configSec")
    public ConfigSec configSec;

    @SerializedName("status")
    public Status status;

    @SerializedName("publisher")
    private Publisher publisher;

    public Publisher getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @SerializedName("rgwInternal")
    public rgwInternal rgwInternal;

    @SerializedName("command")
    public Command command;

    @SerializedName("diagnostics")
    public ArrayList<DiagnosticList> diagnostics;

    public @Nullable DiagnosticList getDiagnostic(Integer iD) {
        for (DiagnosticList diagnosticElement : diagnostics) {
            if (diagnosticElement.getId() == iD) {
                return diagnosticElement;
            }
        }
        return null;
    }

    private class ConfigSec {

        @SerializedName("deviceManagementPort")
        public Integer deviceManagementPort;

        @SerializedName("pingDuration")
        public Integer pingDuration;

        @SerializedName("longPollTime")
        public Integer longPollTime;

        @SerializedName("shortPollRemUser")
        public Integer shortPollRemUser;

        @SerializedName("weatherEpId")
        public String weatherEpId;

        @SerializedName("shortPollPeriod")
        public Integer shortPollPeriod;

        @SerializedName("relayServerPort")
        public Integer relayServerPort;

        @SerializedName("maxConnectAttempts")
        public Integer maxConnectAttempts;

        @SerializedName("updatedToV3_50")
        public Boolean updatedToV3_50;

        @SerializedName("backOffTime")
        public Integer backOffTime;

        @SerializedName("respTimeout")
        public Integer respTimeout;

        @SerializedName("deviceManagementUrl")
        public String deviceManagementUrl;

        @SerializedName("pingInterval")
        public Integer pingInterval;

        @SerializedName("pingUrl")
        public String pingUrl;

        @SerializedName("relayServerUrl")
        public String relayServerUrl;

        @SerializedName("urlsConfigured")
        public Boolean urlsConfigured;

        @SerializedName("connectRetryPeriod")
        public Integer connectRetryPeriod;

        @SerializedName("notificationUriV3_50")
        public String notificationUriV3_50;

        @SerializedName("connectTimeout")
        public Integer connectTimeout;

        @SerializedName("retrieveServerUrl")
        public String retrieveServerUrl;

        @SerializedName("requestServerPort")
        public Integer requestServerPort;

        @SerializedName("requestServerUrl")
        public String requestServerUrl;

        @SerializedName("publishServerPort")
        public Integer publishServerPort;

        @SerializedName("publishServerUrl")
        public String publishServerUrl;

        @SerializedName("retrieveServerPort")
        public Integer retrieveServerPort;

        @SerializedName("updatedToMicroservices")
        public Boolean updatedToMicroservices;

        @SerializedName("updatedToV3_80")
        public Boolean updatedToV3_80;
    }

    private class Status {

        @SerializedName("utilityConnection")
        public String utilityConnection;

        @SerializedName("syncAAPState")
        public String syncAAPState;

        @SerializedName("pingInProgress")
        public Boolean pingInProgress;

        @SerializedName("remoteUserLoggedIn")
        public Boolean remoteUserLoggedIn;

        @SerializedName("relayServerConnected")
        public Boolean relayServerConnected;

        @SerializedName("internetStatus")
        public Boolean internetStatus;

        @SerializedName("writeAccess")
        public WriteAccess writeAccess;

        @SerializedName("requestConnection")
        public String requestConnection;

        @SerializedName("retrieveConnection")
        public String retrieveConnection;

        @SerializedName("publishConnection")
        public String publishConnection;

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;

        @SerializedName("rgwUp")
        public Boolean rgwUp;

        @SerializedName("aapConnection")
        public String aapConnection;
    }

    private class rgwInternal {

        @SerializedName("writeAccess")
        public WriteAccess writeAccess;

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;

        @SerializedName("lastLogReadTime")
        public Integer lastLogReadTime;
    }

    private class Command {

        @SerializedName("code")
        public String code;

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;

        @SerializedName("targetId")
        public String targetId;

        @SerializedName("writeAccess")
        public WriteAccess writeAccess;

        @SerializedName("result")
        public Integer result;

        @SerializedName("payload")
        public String payload;
    }

    private class DiagnosticList {

        @SerializedName("remotePort")
        public Integer remotePort;

        @SerializedName("localPort")
        public Integer localPort;

        @SerializedName("doNotPersist")
        public Boolean doNotPersist;

        @SerializedName("connName")
        public Integer connName;

        @SerializedName("remoteAddress")
        public String remoteAddress;

        @SerializedName("activeOpens")
        public Integer activeOpens;

        @SerializedName("localAddress")
        public String localAddress;

        @SerializedName("id")
        private Integer id;

        public Integer getId() {
            return this.id;
        }

        @SuppressWarnings("unused")
        public void setId(Integer id) {
            this.id = id;
        }

        @SerializedName("attemptFails")
        public Integer attemptFails;
    }
}
