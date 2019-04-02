import serial
import RPi.GPIO as GPIO
import os
import time

GPIO.setmode(GPIO.BOARD)

# Enable Serial Communication
port = serial.Serial("/dev/ttyS0", baudrate=9600, timeout=1)

# Transmitting AT Commands to the Modem
# '\r' indicates the Enter key

port.write('AT'+'\r')
rcv = port.read_all()
print rcv
time.sleep(1)

port.write('AT+CMGF=1'+'\r')  # Select Message format as Text mode
rcv = port.read_all()
print rcv
time.sleep(1)

port.write('AT+CNMI=2,1,0,0,0'+'\r')   # New SMS Message Indications
rcv = port.read_all()
print rcv
time.sleep(1)

# Sending a message to a particular Number

port.write('AT+CMGS="+919427134631"'+'\r')
rcv = port.read_all()
print rcv
time.sleep(1)
port.reset_output_buffer()
port.write('Hello User'+'\r')  # Message
rcv = port.read_all()
print rcv

port.write("\x1A")  # Enable to send SMS
for i in range(10):
    rcv = port.read_all()
    print rcv
