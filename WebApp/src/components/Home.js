import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import { CardContent } from '@material-ui/core';
import Card from '@material-ui/core/Card';
import Button from '@material-ui/core/Button';
import data from '../data/data.json';

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

function Home(props) {
  const { classes } = props;
  return (
    <List className={classes.root}>
      {data.data.map((meter) => 
        <Link to={`/userdetails/${meter.meterid}`} className={classes.link}>
          <ListItem>
            <Card className={classes.card}>
              <CardContent className={classes.cardContent}>
                <div>
                  <div>
                    <p><span className="bold">Name:</span> {meter.name}</p>
                  </div>
                  <div>
                    <p><span className="bold">Meter ID:</span> {meter.meterid}</p>
                  </div>
                </div>
                <Button variant="contained" color="secondary" className={classes.button}>
                  Block Supply
                </Button>
              </CardContent>
            </Card>
          </ListItem>
        </Link>
        )
      }
    </List>
  );
}

Home.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Home);