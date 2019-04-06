import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import { CardContent } from '@material-ui/core';
import Card from '@material-ui/core/Card';
import Button from '@material-ui/core/Button';
import axios from 'axios';

const styles = theme => ({
  root: {
    display: 'flex',
    justifyContent: 'space-between',
    flexWrap: 'wrap',
    padding: '0 8rem',
    backgroundColor: theme.palette.background.paper
  },
  card: {
    width: '100%'
  },
  cardContent: {
    display: 'flex',
    justifyContent: 'space-between'
  },
  button: {
    margin: theme.spacing.unit,
    alignSelf: 'center',
  },
  link: {
    textDecoration: 'none',
    color: 'initial',
    flex: '0 0 50%'
  }
});

class Home extends React.Component {

  constructor(props) {
    super(props);
		this.state = {
            userdata: [],
            currentuser: {
              isBlocked: false,
              username: null
            }
        };
    this.onClick = this.onClick.bind(this);
  }

  componentDidMount() {
    axios.get('http://smart-meter-guj.herokuapp.com/rest/user/?format=json')
      .then(res => {
        const users = res.data;
          this.setState({
            userdata: users
          })
      });
  }

  onClick(prop) {

    axios.get('http://smart-meter-guj.herokuapp.com/rest/user/'+ prop +'/?format=json')
      .then(res => {
        axios.patch('http://smart-meter-guj.herokuapp.com/rest/user/' + prop + '/', {
          "meter_status": res.data.meter_status ? false : true
        });

        this.setState({
          currentuser: {
            ...this.state.currentuser,
            isBlocked : res.data.meter_status ? false: true,
            username : prop
          }
        });
    });
  }

  render() {
    const { classes } = this.props;
    const users = this.state.userdata;

    return(
      <List className={classes.root}>
      {users.map((user) => 
        <ListItem>
            <Card className={classes.card}>
              <CardContent className={classes.cardContent}>
                <Link to={`/userdetails/${user.user.username}`} className={classes.link}>
                  <div>
                    <div>
                      <p><span className="bold">Name:</span> {user.user.username}</p>
                    </div>
                    <div>
                      <p><span className="bold">Meter ID:</span> {user.meter_id}</p>
                    </div>
                  </div>
                  </Link>
                <Button variant="contained" color="secondary" className={classes.button} onClick={() => this.onClick(user.user.username)}>
                  {!user.meter_status ? "Unblock Supply" : "Block Supply"}
                </Button>
              </CardContent>
            </Card>
          </ListItem>
        )
      }
    </List>
    );
  }
}

Home.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Home);