import React, {Component} from 'react';
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, Label } from 'recharts';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
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
            <div className='container'>
                <div className="user-info">    
                    <p><span className="bold">Meter ID:</span> {this.props.match.params.meterid}</p>
                    <p><span className="bold">Name:</span> {current.name}</p>
                    <p><span className="bold">Phone Number:</span> {current.phonenumber}</p>
                    <p><span className="bold">Address:</span> {current.address}</p>
                </div>
                <div>
                    <Button variant="contained" color="primary" onClick={this.onClick}>
                    Generate Graph
                    </Button>
                </div>
                <Card style={{display: visible ? 'inline-block' : 'none', padding:'20px', marginTop: '40px'}}>
                    <AreaChart width={600} height={300} data={current_graph.points} margin={{ top: 5, right: 20, bottom: 5, left: 0 }}>
                        <Area type="monotone" dataKey="y" stroke="#8884d8" />
                        <CartesianGrid stroke="#ccc" strokeDasharray="5 5" />
                        <XAxis dataKey="x">
                            <Label value="X-axis" offset={0} position="insideBottom" />
                        </XAxis>
                        <YAxis dataKey="y">
                            <Label value="Y-axis" offset={0} position="insideLeft" />
                        </YAxis>
                        <Tooltip />
                    </AreaChart>
                </Card>
            </div>
        );
    }
}

export default UserDetails;