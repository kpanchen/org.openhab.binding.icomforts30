package org.openhab.binding.icomforts30.internal.api.models.response;

import com.google.gson.annotations.SerializedName;

public class SiblingList {

    @SerializedName("id")
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("publisher")
    private Publisher publisher;

    public Publisher getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @SerializedName("selfIdentifier")
    private String selfIdentifier;

    public String getSelfIdentifier() {
        return this.selfIdentifier;
    }

    public void setSelfIdentifier(String selfIdentifier) {
        this.selfIdentifier = selfIdentifier;
    }

    @SerializedName("sibling")
    public Sibling sibling;

    public Sibling getSibling() {
        return this.sibling;
    }

    public void setSibling(Sibling sibling) {
        this.sibling = sibling;
    }

    private class Sibling {

        @SerializedName("identifier")
        public String identifier;

        @SerializedName("systemName")
        public String systemName;

        @SerializedName("nodePresent")
        public Boolean nodePresent;

        @SerializedName("portNumber")
        public Integer portNumber;

        @SerializedName("groupCountTracker")
        public Boolean groupCountTracker;

        @SerializedName("ipAddress")
        public String ipAddress;
    }
}
