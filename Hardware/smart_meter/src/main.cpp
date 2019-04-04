#include <Arduino.h>
#include <SPI.h>
#include <LiquidCrystal.h>
#include <SoftwareSerial.h>

SoftwareSerial mySerial(9, 10);

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
//int backLight = 13;    // pin 9 will control the backlight
//int lallight= 22;
float sample1 = 0; // for voltage
float sample2 = 0; // for current
float voltage = 0.0;
float val;		 // current callibration
float actualval; // read the actual current from ACS 712
float amps = 0.0;
float totamps = 0.0;
float avgamps = 0.0;
float amphr = 0.0;
float watt = 0.0;
float energy = 0.0;
int send_flag = 1;

int i = 0;
int j = 0;
int k = 0;
int delayTime2 = 350; // Delay between shifts
bool p13 = true;

void SendMessage()
{
	mySerial.println("AT+CMGF=1");					 //Sets the GSM Module in Text Mode
	delay(1000);									 // Delay of 1000 milli seconds or 1 second
	mySerial.println("AT+CMGS=\"+919724927731\"\r"); // Replace x with mobile number
	delay(1000);
	mySerial.println(watt);	// The SMS text you want to send
	mySerial.println(voltage); // The SMS text you want to send
	mySerial.println(energy);  // The SMS text you want to send
	mySerial.println(amps);	// The SMS text you want to send
	delay(100);
	mySerial.println((char)26); // ASCII code of CTRL+Z
	delay(1000);
}

void RecieveMessage()
{
	mySerial.println("AT+CNMI=2,2,0,0,0"); // AT Command to receive a live SMS
	delay(3000);
}

void check_messages()
{
	Serial.println("IN");
	String c;
	while (mySerial.available() > 0)
	{
		c = mySerial.readString();
		Serial.print(c);

		if (c.indexOf("+919724927731") > 0 || c.indexOf("toggle") > 0)
		{
			Serial.println("\nToggle time");
			if (p13)
			{
				digitalWrite(13, LOW);
				p13 = false;
			}
			else
			{
				digitalWrite(13, HIGH);
				p13 = true;
			}
		}
	}
	Serial.println("Out");
}
void cleanSerial()
{
	while (mySerial.available() > 0)
		mySerial.read();
	Serial.println("Serial Cleaned");
}

void setup()
{

	mySerial.begin(15200); // Setting the baud rate of GSM Module
	Serial.begin(9600);	// Setting the baud rate of Serial Monitor (Arduino)
	Serial.println("Starting test ...");
	lcd.begin(16, 2);
	lcd.clear();
	pinMode(13, OUTPUT);
	digitalWrite(13, HIGH);
}

void loop()
{
	lcd.setCursor(16, 1); // set the cursor outside the display count
	lcd.print(" ");		  // print empty character

	delay(600);

	long milisec = millis();	// calculate time in milisec
	long time = milisec / 1000; // convert time to sec

	for (int i = 0; i < 150; i++)
	{
		sample1 += analogRead(A0); //read the voltage from the sensor
		sample2 += analogRead(A1); //read the current from sensor
		delay(2);
	}
	sample1 = sample1 / 150;
	sample2 = sample2 / 150;

	voltage = sample1 * (250.0 / 1024.0);
	val = (4.89 * sample2) / 1024.0;
	actualval = val - 2.5; // offset voltage is 2.5v

	amps = actualval * 10;					// 100mv/A from data sheet
	totamps = totamps + amps;				// total amps
	avgamps = totamps / time;				// average amps
	amphr = (avgamps * time) / 3600;		// amphour
	watt = voltage * amps;					// power=voltage*current
	energy = (watt * time) / (1000 * 3600); // energy in kWh

	lcd.setCursor(1, 0); // set the cursor at 1st col and 1st row
	lcd.print(watt);
	lcd.print("W ");
	lcd.print(voltage);
	lcd.print("V");

	lcd.setCursor(1, 1); // set the cursor at 1st col and 2nd row
	lcd.print(energy);
	lcd.print("WH   ");
	lcd.print(amps);
	lcd.print("A");

	/*
  	mySerial.println("AT+CMGF=1");    //Sets the GSM Module in Text Mode
  	delay(1000);  // Delay of 1000 milli seconds or 1 second
  	mySerial.println("AT+CMGS=\"+919428217561\"\r"); // Replace x with mobile number
  	delay(1000);
  	//mySerial.print(watt, voltage, energy, amps);
  	mySerial.println(watt);
  	mySerial.println(voltage);
  	mySerial.println(energy);
  	mySerial.println(amps);
  	//mySerial.println("sim900a sms");// The SMS text you want to send
  	delay(100);
  	mySerial.println((char)26);// ASCII code of CTRL+Z
  	//delay(1000);
  	delay(60000);
  	*/
  	Serial.println(send_flag);
  	if(send_flag % 6 == 0)
	{
		send_flag = 1;
		Serial.println("Sending");
		SendMessage();
		Serial.println("Sent");
		delay(500);
	}
	else
	{
		send_flag += 1;
	}
	check_messages();
	RecieveMessage();
	check_messages();
	delay(3000);
}
