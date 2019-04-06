from time import sleep
import serial
import requests
import json


def check_status(status, user):
    resp = requests.get(
        'https://smart-meter-guj.herokuapp.com/rest/user/' + user + '/')
    if(resp.status_code == 200):
        data = resp.json()
        if(data['meter_status'] == status):
            return
        else:
            # send the message
            return
    else:
        return


def push_to_rest(user, send_dict):
    resp = requests.post(
        'https://smart-meter-guj.herokuapp.com/rest/user/record/' + user + '/', data=json.dumps(send_dict),
        headers={'Content-Type': 'application/json'},)
    print(resp, resp.json())
    pass


def main():
    # Establish the connection on a specific port
    ser = serial.Serial('/dev/ttyACM0', 9600)
    counter = 32  # Below 32 everything in ASCII is gibberish
    while True:
        counter += 1
        # Convert the decimal number to ASCII then send it to the Arduino
        ser.write(bytes(str(chr(counter)).encode()))
        # Read the newest output from the Arduino
        line = ser.readline()
        line = line.decode("ASCII").strip()
        print(line)
        if(line == 'start'):
            print('IN')
            user = ser.readline().decode("ASCII").strip()
            send_dict = {}
            send_dict['volt'] = float(ser.readline().decode("ASCII").strip())
            send_dict['current'] = float(
                ser.readline().decode("ASCII").strip())
            send_dict['watt'] = float(ser.readline().decode("ASCII").strip())
            send_dict['energy'] = float(ser.readline().decode("ASCII").strip())
            send_dict['bill_time'] = bool(
                ser.readline().decode("ASCII").strip())
            status = bool(ser.readline().decode("ASCII").strip())
            check_status(status, user)
            push_to_rest(user, send_dict)
            print(send_dict)
            print(user, status)
    sleep(.1)  # Delay for one tenth of a second
    if counter == 255:
        counter = 32


if __name__ == "__main__":
    main()
