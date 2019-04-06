#include <Arduino.h>
#include <SPI.h>
#include <LiquidCrystal.h>
#include <SoftwareSerial.h>

SoftwareSerial mySerial(9, 10);

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

float readVal1 = 0; // for voltage
float readVal2 = 0; // for current
float voltage = 0.0;

double Variable = 0;
double VRMS = 0;
double AmpsRMS = 0;

float avgamps = 0;
float amphr = 0;

float power = 0.0;
float energy = 0.0;
int send_flag = 1;
int mVperAmp = 186;

int i = 0;
int j = 0;
int k = 0;
int delayTime2 = 350; // Delay between shifts
bool p13 = true;

void SendMessage()
{
    mySerial.println("AT+CMGF=1");                   //Sets the GSM Module in Text Mode
    delay(1000);                                     // Delay of 1000 milli seconds or 1 second
    mySerial.println("AT+CMGS=\"+917600931609\"\r"); // Replace x with mobile number
    delay(1000);
    mySerial.println(power);   // The SMS text you want to send
    mySerial.println(voltage); // The SMS text you want to send
    mySerial.println(energy);  // The SMS text you want to send
    mySerial.println(AmpsRMS); // The SMS text you want to send
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

float getVPP()
{
    float result;
    int readValue;       //value read from the sensor
    int maxValue = 0;    // store max value here
    int minValue = 1024; // store min value here

    uint32_t start_time = millis();
    while ((millis() - start_time) < 1000) //sample for 1 Sec
    {
        readVal2 = analogRead(A1);
        Serial.print(A1);
        // see if you have a new maxValue
        if (readValue > maxValue)
        {
            /*record the maximum sensor value*/
            maxValue = readValue;
        }
        if (readValue < minValue)
        {
            /*record the minimum sensor value*/
            minValue = readValue;
        }
    }

    // Subtract min from max
    result = ((maxValue - minValue) * 5.0) / 1024.0;

    return result;
}

void setup()
{

    mySerial.begin(15200); // Setting the baud rate of GSM Module
    Serial.begin(9600);    // Setting the baud rate of Serial Monitor (Arduino)
    Serial.println("Starting test ...");
    lcd.begin(16, 2);
    lcd.clear();
    pinMode(13, OUTPUT);
    digitalWrite(13, HIGH);
}

void loop()
{
    lcd.setCursor(16, 1); // set the cursor outside the display count
    lcd.print(" ");       // print empty character

    delay(600);

    long milisec = millis();    // calculate time in milliseconds
    long time = milisec / 1000; // convert milliseconds to seconds

    int readVal1 = analogRead(A0);
    Serial.print(readVal1);

    float voltage = readVal1 * (9.23);
    Serial.print("AC Voltage: ");
    Serial.print(voltage);
    Serial.println(" Volts");
    //delay(1000);

    Variable = getVPP();
    VRMS = (Variable / 2.0) * 0.707; //root 2 is 0.707
    AmpsRMS = (VRMS * 1000) / mVperAmp;
    //Serial.print(AmpsRMS);
    //Serial.println(" Amps RMS");

    //amps = actualval * 10;                    // 100mv/A from data sheet
    //totamps = totamps + amps;                // total amps
    //avgamps = totamps / time;                // average amps
    //amphr = (avgamps * time) / 3600;        // amphour
    //watt = voltage * amps;                    // power=voltage*current
    //energy = (watt * time) / (1000 * 3600); // energy in kWh

    avgamps = AmpsRMS / time;        // average amps
    amphr = (avgamps * time) / 3600; // amp-hour
    power = voltage * AmpsRMS;
    //energy=(watt*time)/3600; Watt-sec is again convert to Watt-Hr by dividing 1hr(3600sec)
    energy = (power * time) / (1000 * 3600); //for reading in kWh

    lcd.setCursor(1, 0); // set the cursor at 1st col and 1st row
    //lcd.print(power);
    //lcp.print("watt ");
    lcd.print(voltage);
    lcd.print("V");

    lcd.setCursor(1, 1); // set the cursor at 1st col and 2nd row
                         //lcd.print(energy);
                         //lcd.print("KWh   ");
                         //lcd.print(AmpsRMS);
                         //lcd.print("A");

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
    if (send_flag % 6 == 0)
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
