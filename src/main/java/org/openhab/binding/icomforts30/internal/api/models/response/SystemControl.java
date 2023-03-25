package org.openhab.binding.icomforts30.internal.api.models.response;

import com.google.gson.annotations.SerializedName;

public class SystemControl {

    // ToDo
    @SerializedName("publisher")
    public Publisher publisher;

    @SerializedName("doNotPersist")
    public Boolean doNotPersist;
}
