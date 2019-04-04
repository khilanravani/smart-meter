import React, {Component} from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip } from 'recharts';
import Button from '@material-ui/core/Button';
import data from '../data/data.json';
import graph_data from '../data/graph_data.json';

class UserDetails extends Component {

    constructor(props) {
		super(props);
		this.state = {
            plot: [],
            showPlot: false
        };
        this.onClick = this.onClick.bind(this);
    }
    onClick() {
        this.setState({showPlot: true})
    }
    render() {
        let current = data.data.filter(meter => meter.meterid === parseInt(this.props.match.params.meterid))[0];
        let current_graph = graph_data.graph_data.filter(graph => graph.meterid === parseInt(this.props.match.params.meterid))[0];
        let visible = this.state.showPlot;
        console.log(visible);
        return(
            <div>
                <div>    
                    <p>Meter ID: {this.props.match.params.meterid}</p>
                    <p>Name: {current.name}</p>
                    <p>Phone Number: {current.phonenumber}</p>
                    <p>Address: {current.address}</p>
                </div>
                <Button variant="contained" color="primary" onClick={this.onClick}>
                  Generate Graph
                </Button>
                <div style={{display: visible ? 'block' : 'none' }}>
                    <LineChart width={600} height={300} data={current_graph.points} margin={{ top: 5, right: 20, bottom: 5, left: 0 }}>
                        <Line type="monotone" dataKey="y" stroke="#8884d8" />
                        <CartesianGrid stroke="#ccc" strokeDasharray="5 5" />
                        <XAxis dataKey="x" />
                        <YAxis dataKey="y"/>
                        <Tooltip />
                    </LineChart>
                </div>
            </div>
        );
    }
}

export default UserDetails;