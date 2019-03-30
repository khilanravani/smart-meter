import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';  
import Card from '@material-ui/core/Card';
import { CardContent } from '@material-ui/core';
import Button from '@material-ui/core/Button';

const styles = theme => ({
  root: {
    width: '100%',
    justifyContent:'center'
  },
  avatar: {
    margin: 10,
    backgroundColor: '#0000ff',
  },
  card: {
    width: '30%',
    alignSelf: 'center',
    display: 'flex',
    justifyContent: 'center',
    backgroundColor: theme.palette.background.paper,
    margin: '20px auto',
    padding: '10px 0'
  },
  cardContent: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center'
  },
  button: {
    margin: theme.spacing.unit,
    alignSelf: 'center',
  },
});

function Profile(props) {
  const { classes } = props;
  return (
    <div className={classes.root}>
      <Card className={classes.card}>
      <CardContent className={classes.cardContent}>
        <div>
          <div>
            <p>Name: Harshendra Shah</p>
          </div>
          <div>
            <p>ID: 0123456789</p>
          </div>
          <div>
            <p>Email Id: abc@xyz.com</p>
          </div>
          <div>
            <p>Phone Number: 1234567890</p>
          </div>
        </div>
        <Button variant="contained" color="primary" className={classes.button}>
          Log Out
        </Button>
      </CardContent>
    </Card>
    </div>
    
  );
}

Profile.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Profile);