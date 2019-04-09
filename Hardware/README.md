# Smart Meter Hardware

![alt text](/imgs/hardware_pics/hw_proto.jpg)

## Hardware Architecture

● The wireless energy meter is able to send the electricity reading data using the IoT device GSM 900A module which connected with the arduino microcontroller (MCU)
both gsm and microcontroller is connected with each other using bi-directional bus
for data transfer.

● The electricity data required for sending to the server is generated using the the
current sensor ACS712.

● Voltage is measured by using the rectifier circuit whose output pin gives the analog
data to the MCU.

● Microcontroller generates the electricity units which are sent by the GSM
module to the server to the electricity supplier company.

● The wireless energy meter also allows the electricity supplier company to
switch the electricity connection of the each home from the server.

● For this, one IGBT/relay module is connected between the ac mains power
lines and the smart electricity meter.

![alt text](/imgs/hardware_pics/hw_proto2.jpg)
