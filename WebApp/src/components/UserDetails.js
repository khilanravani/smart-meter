import React, {Component} from 'react';
import data from '../data/data.json';

class UserDetails extends Component {
    render() {
        let current = data.data.filter(meter => meter.meterid === parseInt(this.props.match.params.meterid))[0];
        return(
            <div>
                <p>Meter ID: {this.props.match.params.meterid}</p>
                <p>Name: {current.name}</p>
                <p>Phone Number: {current.phonenumber}</p>
                <p>Address: {current.address}</p>
            </div>
        );
    }
}

export default UserDetails;