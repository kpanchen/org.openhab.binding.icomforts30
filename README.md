# iComfort S30 Binding

_This is an OpenHAB 3 Binding for Lennox iComfort S30/E30/M30 system / thermostat (Note this will not work with older system WiFi)._

## Supported Things

This binding supports two things:<br />

1. Thermostat system (currently under development, only System Away Channels currently supported for the thing)<br />
2. Heating / Cooling Zone (4 zones supported).


## Discovery

(Not available yet, on ToDo list).

## Binding Configuration

No binding configuration is required.

## Thing Configuration

Configuration of the Bridge thing is required:

hostname - IP address or hostname of the thermostate controller on the local network.<br />

Optional parameters:
port - must be set to default 443
protocol - must be set to default https
useSelfSignedCertificate - must be set to default true
refreshInterval - default set to 30 seconds.
initialRefreshDelay - default set to 30 seconds, delay to allow binding to get required data from the system (this is only to avoid errors in the log, possible will be changed in the future)
pumpInterval - default set to 5 seconds, how often to request data from the system.
appID - application connection identifier, if not provided this parameter will be generated on the first execution and will be the same per running binding (Note it is prefereble to keep this ID, in case of reinstall re-use it and provide in textual configuration or GUI. Important: do not include it on the first run, if you do textual configuration copy this from GUI and provide in the .thing file)

```
Bridge icomforts30:thermostat:demothermostat "Demo name" @ "Demo location" [ hostname="192.168.xxx.xxx", appID="mappxxxxxxxxxxxxxxxxxxxxxxxx" ] {}
```

No configuration required for System thing:

```
Thing system s30_system "iComfort S30 System" @ "Demo location"
```

Configuration of Zone thing is required:

id = zone id, number from 0 to 3 (Note: for a single zone system id must be set to 0)

Optional parameter:
name = "Name of the zone" - (Only to identify zone in the GUI)

```
Thing zone s30_zone_0 "iComfort S30 Heating Zone 0" @ "Demo location" [ id = 0, name = "Main Zone" ]
```

## Channels

System thing:

AwayMode - Current away status (Read / Write)<br />
OutsideTemperature - Current outside temperature (From sensor or internet, Read Only)<br />

Zone thing:

Temperature - Current zone temperature (Read Only)<br />
Humidity - Current zone humidity (Read Only)<br />
SystemStatus - Current system status (Read Only)<br />
HumidityStatus - Current humidity status (Read Only)<br />
OperationMode - Current operation mode (Read / Write)<br />
HumidityMode - Current humidity mode (Read / Write)<br />
FanMode - Current fan mode (Read / Write)<br />
CoolSetPoint - Cool set point for the zone (Read / Write)<br />
HeatSetPoint - Heat set point for the zone (Read / Write)<br />
SetPoint - Heat or Cool set point for the zone in single set point mode (Read / Write)<br />


## Full Example

Thing example:

```
Bridge icomforts30:thermostat:demothermostat "Demo name" @ "Demo location" [ hostname="192.168.xxx.xxx" ]  {
    Thing system s30_system "iComfort S30 System" @ "Demo location"
    Thing zone s30_zone_0 "iComfort S30 Heating Zone 0" @ "Demo location" [ id = 0, name = "Main Zone" ]
}
```

Items example:

```
//Zone items
Number:Temperature iComfortS30HeatingZone_0_Temperature "Temperature [%.1f %unit%]" <temperature> (gWholeHouse, gTemperatureSensors) {channel="icomforts30:zone:knplennoxs30:s30_zone_0:Temperature"}
Number:Dimensionless iComfortS30HeatingZone_0_Humidity "Humidity [%d %unit%]" <humidity> (gWholeHouse, gHumiditySensors) {channel="icomforts30:zone:knplennoxs30:s30_zone_0:Humidity"}
String iComfortS30HeatingZone_0_SystemStatus "System Status [%s]" <heating> (gWholeHouse) {channel="icomforts30:zone:knplennoxs30:s30_zone_0:SystemStatus"}
String iComfortS30HeatingZone_0_OperationMode "Operation Mode [%s]" <heating> (gWholeHouse) {channel="icomforts30:zone:knplennoxs30:s30_zone_0:OperationMode"}
String iComfortS30HeatingZone_0_HumidityStatus "Humidity Status [%s]" <heating> (gWholeHouse) {channel="icomforts30:zone:knplennoxs30:s30_zone_0:HumidityStatus"}
String iComfortS30HeatingZone_0_HumididtyMode "Humidity Mode [%s]" <heating> (gWholeHouse) {channel="icomforts30:zone:knplennoxs30:s30_zone_0:HumidityMode"}
String iComfortS30HeatingZone_0_FanModeStatus "Fan Mode [%s]" <fan> (gWholeHouse) {channel="icomforts30:zone:knplennoxs30:s30_zone_0:FanMode"}
Number:Temperature iComfortS30HeatingZone_0_CoolSetPoint "Cool Set Point [%.1f %unit%]" <temperature> (gWholeHouse) {channel="icomforts30:zone:knplennoxs30:s30_zone_0:CoolSetPoint"}
Number:Temperature iComfortS30HeatingZone_0_HeatSetPoint "Heat Set Point [%.1f %unit%]" <temperature> (gWholeHouse) {channel="icomforts30:zone:knplennoxs30:s30_zone_0:HeatSetPoint"}
```

```
//System items:
String iComfortS30System_AwayModeStatus "Away Mode [%s]" <heating> (gWholeHouse) {channel="icomforts30:system:knplennoxs30:s30_system:AwayMode"}
Number:Temperature iComfortS30System_Outside_Temperature "Outside Temperature [%.1f %unit%]" <temperature> (gWholeHouse, gTemperatureSensors) {channel="icomforts30:system:knplennoxs30:s30_system:OutsideTemperature"}
```

Sitemap example:

```
Text item=iComfortS30HeatingZone_0_Temperature
Text item=iComfortS30HeatingZone_0_Humidity
Text item=iComfortS30HeatingZone_0_SystemStatus
Text item=iComfortS30HeatingZone_0_HumidityStatus
Selection  item=iComfortS30HeatingZone_0_OperationMode mappings=[HVAC_OFF=System is idle", HVAC_HEAT="System is heating", HVAC_COOL="System is cooling", HVAC_EMERGENCY_HEAT="System is emergency heating", HVAC_HEAT_COOL="System is heating or cooling"]
Selection  item=iComfortS30HeatingZone_0_HumididtyMode
Switch item=Thermostat_Away_Mode mappings=[AWAY="Away", HOME="Home"]
Selection  item=Thermostat_Fan_Mode mappings=[FAN_AUTO="Auto", FAN_ON="On", FAN_CIRCULATE="Circulate"]
Setpoint item=Thermostat_Cool_Point
Setpoint item=Thermostat_Heat_Point

```

## Foot note!

This binding is based on my previous binding for iComfortWiFi thermostat wich in turn was based on Nest binding and EVO Home binding, all the credits for original code goes to the original authors.<br />
I also used a lot of reversed engineering done by other programmers on Internet and I have to Thank them here!
In particularly Python code created by PeterRager, without his work this wouldn't be possible, all the credits goes to him, his original code available here:
https://github.com/PeteRager/lennoxs30api
