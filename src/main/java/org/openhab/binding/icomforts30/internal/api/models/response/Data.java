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

import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the data information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class Data {

    @SerializedName("interfaces")
    private ArrayList<InterfaceList> interfaces;

    @SerializedName("system")
    private System system;

    @SerializedName("zones")
    private ArrayList<ZoneList> zones;

    @SerializedName("devices")
    private ArrayList<DeviceList> devices;

    @SerializedName("equipments")
    private ArrayList<EquipmentList> equipments;

    @SerializedName("schedules")
    private ArrayList<ScheduleList> schedules;

    @SerializedName("logs")
    private Log logs;

    @SerializedName("occupancy")
    private Occupancy occupancy;

    @SerializedName("rgw")
    private Rgw rgw;

    @SerializedName("fwm")
    private Fwm fwm;

    @SerializedName("alerts")
    private Alerts alerts;

    @SerializedName("siblings")
    public ArrayList<SiblingList> siblings;

    @SerializedName("homes")
    public ArrayList<HomeList> homes;

    public Data() {
    }

    public Occupancy getOccupancy() {
        return this.occupancy;
    }

    public Fwm getFirmware() {
        return this.fwm;
    }

    public Log getLogs() {
        return this.logs;
    }

    public Log getLog() {
        return this.logs;
    }

    public void setAlerts(Alerts alerts) {
        this.alerts = alerts;
    }

    public Alerts getAlerts() {
        return this.alerts;
    }

    public ArrayList<ScheduleList> getSchedules() {
        return this.schedules;
    }

    public @Nullable ScheduleList getSchedule(Integer iD) {
        for (ScheduleList scheduleElement : schedules) {
            if (scheduleElement.getId() == iD) {
                return scheduleElement;
            }

        }
        return null;
    }

    public ArrayList<EquipmentList> getEquipments() {
        return this.equipments;
    }

    public @Nullable EquipmentList getEquipment(Integer iD) {
        for (EquipmentList equipmentElement : equipments) {
            if (equipmentElement.getId() == iD) {
                return equipmentElement;
            }

        }
        return null;
    }

    public ArrayList<InterfaceList> getInterfaces() {
        return this.interfaces;
    }

    public @Nullable InterfaceList getInterface(Integer iD) {
        for (InterfaceList interfaceElement : interfaces) {
            if (interfaceElement.getId() == iD) {
                return interfaceElement;
            }

        }
        return null;
    }

    public Integer getNumberOfInterfaces() {
        return this.interfaces.size();
    }

    public System getSystem() {
        return this.system;
    }

    public ArrayList<ZoneList> getZones() {
        return this.zones;
    }

    public @Nullable ZoneList getZone(Integer iD) {
        for (ZoneList zoneElement : zones) {
            if (zoneElement.getId() == iD) {
                return zoneElement;
            }

        }
        return null;
    }

    public Integer getNumberOfZones() {
        return this.zones.size();
    }

    public ArrayList<DeviceList> getDevices() {
        return this.devices;
    }

    public @Nullable DeviceList getDevice(Integer iD) {
        for (DeviceList deviceElement : devices) {
            if (deviceElement.getId() == iD) {
                return deviceElement;
            }

        }
        return null;
    }

    public Integer getNumberOfDevices() {
        return this.devices.size();
    }

    public Rgw getRgw() {
        return this.rgw;
    }

    public ArrayList<SiblingList> getSiblings() {
        return this.siblings;
    }

    public @Nullable SiblingList getSibling(Integer iD) {
        for (SiblingList siblingElement : siblings) {
            if (siblingElement.getId() == iD) {
                return siblingElement;
            }

        }
        return null;
    }

    public Integer getNumberOfSiblings() {
        return this.siblings.size();
    }
}
