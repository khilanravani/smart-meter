import React from 'react';
import PropTypes from 'prop-types';
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
  listItem: {
    flex: '0 0 50%'
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
});

function Home(props) {
  const { classes } = props;
  return (
    <List className={classes.root}>
      {data.data.map((meter) => 
        <ListItem className={classes.listItem}>
          <Card className={classes.card}>
            <CardContent className={classes.cardContent}>
              <div>
                <div>
                  <p>Name: {meter.name}</p>
                </div>
                <div>
                  <p>Meter ID: {meter.meterid}</p>
                </div>
              </div>
              <Button variant="contained" color="secondary" className={classes.button}>
                Block Supply
              </Button>
            </CardContent>
          </Card>
        </ListItem>
        )
      }
    </List>
  );
}

Home.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Home);