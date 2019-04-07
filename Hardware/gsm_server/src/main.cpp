#include <Arduino.h>
#include <SoftwareSerial.h>
SoftwareSerial mySerial(10, 9);
char msg;
char call;

void SendMessage()
{
	mySerial.println("AT+CMGF=1");					 //Sets the GSM Module in Text Mode
	delay(1000);									 // Delay of 1000 milli seconds or 1 second
	mySerial.println("AT+CMGS=\"+917600931609\"\r"); // Replace x with mobile number
	delay(1000);
	mySerial.println("toggle"); // The SMS text you want to send
	delay(100);
	Serial.println("toggle");
	mySerial.println((char)26); // ASCII code of CTRL+Z
	delay(1000);
}

void ReceiveMessage()
{
	mySerial.println("AT+CNMI=2,2,0,0,0"); // AT Command to recieve a live SMS
	delay(1000);
	if (mySerial.available() > 0)
	{
		msg = mySerial.read();
		Serial.print(msg);
	}
}

void setup()
{
	mySerial.begin(9600); // Setting the baud rate of GSM Module
	Serial.begin(9600);   // Setting the baud rate of Serial Monitor (Arduino)
	Serial.println("GSM SIM900A BEGIN");
	delay(100);
}

void loop()
{
	if (Serial.available() > 0)
		switch (Serial.read())
		{
		case 's':
			SendMessage();
			break;
		case 'r':
			ReceiveMessage();
			break;
		}
	while (mySerial.available() > 0)
		Serial.write(mySerial.read());
}
