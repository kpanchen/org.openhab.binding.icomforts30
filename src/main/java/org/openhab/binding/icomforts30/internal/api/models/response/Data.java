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
 * Response model for the data information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class Data {

    @SerializedName("interfaces")
    public ArrayList<InterfaceList> interfaces;

    @SerializedName("system")
    public System system;

    @SerializedName("zones")
    public ArrayList<ZoneList> zones;

    @SerializedName("devices")
    public ArrayList<DeviceList> devices;

    @SerializedName("equipments")
    public ArrayList<EquipmentList> equipments;

    @SerializedName("schedules")
    public ArrayList<ScheduleList> schedules;

    @SerializedName("logs")
    public Log logs;

    @SerializedName("occupancy")
    public Occupancy occupancy;

    public Data() {
    }

    public Occupancy getOccupancy() {
        return this.occupancy;
    }

    public Log getLogs() {
        return this.logs;
    }

    public Log getLog() {
        return this.logs;
    }

    public ArrayList<ScheduleList> getSchedules() {
        return this.schedules;
    }

    public ScheduleList getSchedule(Integer iD) {
        return this.schedules.get(iD);
    }

    public ArrayList<EquipmentList> getEquipments() {
        return this.equipments;
    }

    public EquipmentList getEquipment(Integer iD) {
        return this.equipments.get(iD);
    }

    public ArrayList<InterfaceList> getInterfaces() {
        return this.interfaces;
    }

    public InterfaceList getInterface(Integer iD) {
        return this.interfaces.get(iD);
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

    public ZoneList getZone(Integer iD) {
        return this.zones.get(iD);
    }

    public Integer getNumberOfZones() {
        return this.zones.size();
    }

    public ArrayList<DeviceList> getDevices() {
        return this.devices;
    }

    public DeviceList getDevice(Integer iD) {
        return this.devices.get(iD);
    }

    public Integer getNumberOfDevices() {
        return this.devices.size();
    }
}
