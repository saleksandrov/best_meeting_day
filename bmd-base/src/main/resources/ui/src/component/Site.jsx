import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import CreateVote from './CreateVote';
import AddVote from './AddVote';
import Navigate from './Navigate';
import ViewResult from './ViewResult';
import moment from 'moment';

class Site extends Component {

    render() {
        return (
            <Router>
                <>
                <h1>Выбор лучшей даты встречи</h1>
                <br/>
                <Switch>
                    <Route path="/" exact component={Navigate}/>
                    <Route path="/createvote" exact component={CreateVote}/>
                    <Route path="/addvote/:voteId" exact component={AddVote}/>
                    <Route path="/viewresult/:voteId" exact component={ViewResult}/>
                </Switch>
                <br/>
                <br/>
                <p>
                    <a href="https://yasobe.ru/na/asv_app_dev">Поддержать проект</a>
                    &copy; {moment().year()} mailto:asv-app-dev@yandex.ru <small>v1.0.4</small></p>
                </>
            </Router>
        );
    }
}

export default Site;