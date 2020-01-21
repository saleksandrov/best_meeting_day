import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import BmdApp from './BmdApp';
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.min.css';

ReactDOM.render(<BmdApp />, document.getElementById('root'));
//ReactDOM.render(<HelloMessage name="ИРД" />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
