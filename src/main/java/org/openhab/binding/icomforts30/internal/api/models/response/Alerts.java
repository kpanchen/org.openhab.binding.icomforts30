package org.openhab.binding.icomforts30.internal.api.models.response;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.AlertAction;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.AlertClearedBy;
import org.openhab.binding.icomforts30.internal.api.models.common.CustomTypes.AlertPriority;

import com.google.gson.annotations.SerializedName;

public class Alerts {

    @SerializedName("active")
    private ArrayList<ActiveList> active;

    public ArrayList<ActiveList> getActiveAlerts() {
        return this.active;
    }

    public void setActiveAlerts(ArrayList<ActiveList> alertList) {
        this.active = alertList;
    }

    public @Nullable ActiveList getActiveAlert(Integer iD) {
        for (ActiveList activeElement : active) {
            if (activeElement.getId() == iD) {
                return activeElement;
            }

        }
        return null;
    }

    private class ActiveList {

        @SerializedName("alert")
        public ArrayList<Alert> alert;

        private class Alert {

            @SerializedName("userMessageID")
            public Integer userMessageID;

            // ToDo - Validate object type
            @SerializedName("timestampClear")
            public Date timestampClear;

            @SerializedName("code")
            public Integer code;

            @SerializedName("notifyDealer")
            public Boolean notifyDealer;

            @SerializedName("userMessage")
            public String userMessage;

            @SerializedName("isStillActive")
            public Boolean isStillActive;

            @SerializedName("update")
            public Boolean update;

            @SerializedName("clearableByDealer")
            public Boolean clearableByDealer;

            // ToDo - Validate object type
            @SerializedName("timestampLast")
            public Date timestampLast;

            @SerializedName("priority")
            public AlertPriority priority;

            @SerializedName("notifyUser")
            public Boolean notifyUser;

            @SerializedName("clearedBy")
            public AlertClearedBy clearedBy;

            // ToDo - Validate object type
            @SerializedName("timestampFirst")
            public Date timestampFirst;

            @SerializedName("equipmentType")
            public Integer equipmentType;

            @SerializedName("optionalfieldData")
            public String optionalfieldData;

            @SerializedName("action")
            public AlertAction action;

            // ToDo - Validate object type
            @SerializedName("optionalfieldType")
            public Object optionalfieldType;

            @SerializedName("clearableByUser")
            public Boolean clearableByUser;

            @SerializedName("count")
            public Integer count;
        }

        @SerializedName("maxItems")
        public Integer maxItems;

        @SerializedName("id")
        private Integer id;

        public Integer getId() {
            return this.id;
        }

        @SuppressWarnings("unused")
        public void setId(Integer id) {
            this.id = id;
        }
    }

    @SerializedName("meta")
    private Meta meta;

    public Meta getAlertsMeta() {
        return this.meta;
    }

    public void setAlertsMeta(Meta alertsMeta) {
        this.meta = alertsMeta;
    }

    private class Meta {

        @SerializedName("numClearedAlerts")
        public Integer numClearedAlerts;

        @SerializedName("numActiveAlerts")
        public Integer numActiveAlerts;

        @SerializedName("lastClearedAlertId")
        public Integer lastClearedAlertId;

        @SerializedName("szClearedAlerts")
        public Integer szClearedAlerts;

        @SerializedName("firstClearedAlertId")
        public Integer firstClearedAlertId;

        @SerializedName("numAlertsInActiveArray")
        public Integer numAlertsInActiveArray;
    }
}
