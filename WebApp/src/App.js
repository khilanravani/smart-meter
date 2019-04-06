import React, { Component } from 'react';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';
import { TransitionGroup, CSSTransition } from 'react-transition-group';
import './App.css';
import Home from './components/Home';
import Profile from './components/Profile';
import Header from './components/Header';
import UserDetails from './components/UserDetails';

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <div>
          <Header />
          <TransitionGroup>
            <CSSTransition classNames="page" timeout={300}>
              <Switch>
                  <Route path='/home' component={Home} />
                  <Route path='/profile' component={Profile} />
                  <Route path='/userdetails/:username' component={UserDetails} />
                  <Redirect to="/home" />
              </Switch>
            </CSSTransition>
          </TransitionGroup>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
