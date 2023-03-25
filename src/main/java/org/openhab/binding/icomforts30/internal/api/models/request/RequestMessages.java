/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
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

package org.openhab.binding.icomforts30.internal.api.models.request;

/**
 * Request parameters enum
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public enum RequestMessages {

    SYSTEM_CONTROL("systemControl"),
    SYSTEM_CONTROLLER("systemController"),
    REMINDER_SENSORS("reminderSensors"),
    REMINDERS("reminders"),
    ALERTS_ACTIVE("alerts/active"),
    ALERTS_META("alerts/meta"),
    FIRMWARE("fwm"),
    RELAY_GATEWAY("rgw"),
    DEVICES("devices"),
    ZONES("zones"),
    EQUIPMENTS("equipments"),
    SCHEDULES("schedules"),
    OCCUPANCY("occupancy"),
    SYSTEM("system"),
    AUTOMATED_TEST("automatedTest"),
    ZONE_TEST_CONTROL("zoneTestControl"),
    HOMES("homes"),
    ALGORITHM("algorithm"),
    HISTORY_REPORT_FILE_DETAILS("historyReportFileDetails"),
    INTERFACES("interfaces"),
    LOGS("logs");

    public final String message;

    private RequestMessages(String message) {
        this.message = message;
    }

    public static String MessagesNormal() {
        StringBuilder sb = new StringBuilder();
        sb.append("1");
        sb.append(";/" + DEVICES.message);
        sb.append(";/" + ZONES.message);
        sb.append(";/" + EQUIPMENTS.message);
        sb.append(";/" + SCHEDULES.message);
        sb.append(";/" + OCCUPANCY.message);
        sb.append(";/" + SYSTEM.message);
        sb.append(";/" + FIRMWARE.message);
        sb.append(";/" + ALERTS_ACTIVE.message);
        sb.append(";/" + ALERTS_META.message);
        sb.append(";/" + HOMES.message);
        return sb.toString();
    }

    public static String MessagesMinimal() {
        StringBuilder sb = new StringBuilder();
        sb.append("1");
        sb.append(";/" + ZONES.message);
        sb.append(";/" + SYSTEM.message);
        return sb.toString();
    }

    // Commented out Messages are currently not supported
    public static String MessagesAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("1");
        // sb.append(";/" + SYSTEM_CONTROL.message);
        // sb.append(";/" + SYSTEM_CONTROLLER.message);
        // sb.append(";/" + REMINDER_SENSORS.message);
        // sb.append(";/" + REMINDERS.message);
        sb.append(";/" + ALERTS_ACTIVE.message);
        sb.append(";/" + ALERTS_META.message);
        sb.append(";/" + FIRMWARE.message);
        sb.append(";/" + RELAY_GATEWAY.message);
        sb.append(";/" + DEVICES.message);
        sb.append(";/" + ZONES.message);
        sb.append(";/" + EQUIPMENTS.message);
        sb.append(";/" + SCHEDULES.message);
        sb.append(";/" + OCCUPANCY.message);
        sb.append(";/" + SYSTEM.message);
        // sb.append(";/" + AUTOMATED_TEST.message);
        // sb.append(";/" + ZONE_TEST_CONTROL.message);
        sb.append(";/" + HOMES.message);
        // sb.append(";/" + ALGORITHM.message);
        // sb.append(";/" + HISTORY_REPORT_FILE_DETAILS.message);
        sb.append(";/" + INTERFACES.message);
        sb.append(";/" + LOGS.message);
        return sb.toString();
    }
}
