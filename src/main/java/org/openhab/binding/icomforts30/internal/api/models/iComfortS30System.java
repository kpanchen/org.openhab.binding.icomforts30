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

package org.openhab.binding.icomforts30.internal.api.models;

import java.util.ArrayList;

import org.openhab.binding.icomforts30.internal.api.models.response.DeviceList;
import org.openhab.binding.icomforts30.internal.api.models.response.EquipmentList;
import org.openhab.binding.icomforts30.internal.api.models.response.InterfaceList;
import org.openhab.binding.icomforts30.internal.api.models.response.Log;
import org.openhab.binding.icomforts30.internal.api.models.response.Occupancy;
import org.openhab.binding.icomforts30.internal.api.models.response.ScheduleList;
import org.openhab.binding.icomforts30.internal.api.models.response.System;
import org.openhab.binding.icomforts30.internal.api.models.response.ZoneList;

/**
 * Lennox iComfort S30 system representation
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */
public class iComfortS30System {

    private String sysID = null;

    private ArrayList<InterfaceList> interfaces = new ArrayList<InterfaceList>();
    private System system = new System();
    private ArrayList<ZoneList> zones = new ArrayList<ZoneList>();
    private ArrayList<DeviceList> devices = new ArrayList<DeviceList>();
    private ArrayList<EquipmentList> equipments = new ArrayList<EquipmentList>();
    private ArrayList<ScheduleList> schedules = new ArrayList<ScheduleList>();
    private Log logs = new Log();
    private Occupancy occupancy = new Occupancy();

    public void setOccupancy(Occupancy occupancy) {
        this.occupancy = occupancy;
    }

    public Occupancy getOccupancy() {
        return this.occupancy;
    }

    public void setLogs(Log logs) {
        this.logs = logs;
    }

    public Log getLogs() {
        return this.logs;
    }

    public void setSchedules(ArrayList<ScheduleList> schedules) {
        this.schedules = schedules;
    }

    public ArrayList<ScheduleList> getSchedules() {
        return this.schedules;
    }

    public void setEquipments(ArrayList<EquipmentList> equipments) {
        this.equipments = equipments;
    }

    public ArrayList<EquipmentList> getEquipments() {
        return this.equipments;
    }

    public void setInterface(Integer iD, InterfaceList systemInterface) {
        if (this.interfaces.size() < iD) {
            this.interfaces.add(iD, systemInterface);
        } else {
            this.interfaces.set(iD, systemInterface);
        }
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public System getSystem() {
        return this.system;
    }

    public void setInterfaces(ArrayList<InterfaceList> interfaces) {
        this.interfaces = interfaces;
    }

    public ArrayList<InterfaceList> getInterfaces() {
        return this.interfaces;
    }

    public void setZones(ArrayList<ZoneList> zones) {
        this.zones = zones;
    }

    public ArrayList<ZoneList> getZones() {
        return this.zones;
    }

    public void setZone(Integer iD, ZoneList zone) {
        if (this.zones.size() < iD + 1) {
            this.zones.add(iD, zone);
        } else {
            this.zones.set(iD, zone);
        }
    }

    // redo!
    public ZoneList getZone(Integer iD) {
        // return this.zones.get(iD);
        for (ZoneList zl : zones) {
            if (zl.getId() == iD) {
                return zl;
            }
        }
        return null;
    }

    public Boolean zoneExist(Integer iD) {
        for (ZoneList zl : zones) {
            if (zl.getId() == iD) {
                return true;
            }
        }
        return false;
    }

    public void setDevices(ArrayList<DeviceList> devices) {
        this.devices = devices;
    }

    public ArrayList<DeviceList> getDevices() {
        return this.devices;
    }

    public void setDevice(Integer iD, DeviceList device) {
        if (this.devices.size() < iD + 1) {
            this.devices.add(iD, device);
        } else {
            this.devices.set(iD, device);
        }
    }

    public String getSysID() {
        return this.sysID;
    }

    public void setSysID(String sysId) {
        this.sysID = sysId;
    }
}
