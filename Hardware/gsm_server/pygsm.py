from time import sleep
import serial
import requests
import json


def check_status(status, user, ser):
    print('Inside Check Status')
    print(user)
    resp = requests.get(
        'https://smart-meter-guj.herokuapp.com/rest/user/' + user + '/')
    print(resp.status_code)
    if(resp.status_code == 200):
        data = resp.json()
        print(data)
        print(status)
        if(data['meter_status'] == status):
            return
        else:
            sleep(1)
            ser.write(bytes(str('s').encode()))
            sleep(4)
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
    ser = serial.Serial('/dev/ttyACM1', 9600)
    counter = 32  # Below 32 everything in ASCII is gibberish
    ser.write(bytes(str('r').encode()))
    sleep(1)
    while True:
        counter += 1
        # Convert the decimal number to ASCII then send it to the Arduino
        # ser.write(bytes(str(chr(counter)).encode()))
        # Read the newest output from the Arduino
        line = ser.readline()
        line = line.decode("ASCII").strip()
        print(line)
        if(line == 'start'):
            try:
                print('IN')
                user = ser.readline().decode("ASCII").strip()
                send_dict = {}
                send_dict['volt'] = float(
                    ser.readline().decode("ASCII").strip())
                send_dict['current'] = float(
                    ser.readline().decode("ASCII").strip())
                send_dict['watt'] = float(
                    ser.readline().decode("ASCII").strip())
                send_dict['energy'] = float(
                    ser.readline().decode("ASCII").strip())
                send_dict['bill_time'] = bool(
                    int(ser.readline().decode("ASCII").strip()))
                status = bool(int(ser.readline().decode("ASCII").strip()))
                print(status, 'ssssssssssssssssssss')
                check_status(status, user, ser)
                push_to_rest(user, send_dict)
                print(send_dict)
                print(user, status)
                sleep(1)
                ser.write(bytes(str('r').encode()))
                sleep(1)
            except ValueError:
                print(ser.read_all())
    sleep(.1)  # Delay for one tenth of a second
    if counter == 255:
        counter = 32


if __name__ == "__main__":
    main()
