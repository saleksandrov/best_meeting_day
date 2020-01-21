import React, {Component} from 'react';
import {Button} from '@material-ui/core';
import {DatePicker, MuiPickersUtilsProvider} from "@material-ui/pickers";
import DateFnsUtils from '@date-io/date-fns';
import {Col, Container, Form, Row} from 'react-bootstrap';

/*
const classes = makeStyles(theme => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,

    },
}));
*/


class BmdApp extends Component {

    constructor(props) {
        super(props);
        this.state = {
            startDate: new Date(),
            endDate: new Date(),
            currentDate: new Date(),
            name: ""
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChangeName = this.handleChangeName.bind(this);
    }

    handleChange(value, name) {
        this.setState({
            [name]: value
        });
    }

    handleChangeName(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    handleSubmit(event) {
        alert('Отправленное имя: ' + this.state.name + ' Дана начала' + this.state.startDate);
        event.preventDefault();
    }

    render() {
        return (
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <Container>
                <Row>
                    <h1>Создать опрос выбора лучшей даты</h1>
                </Row>
                <Row>

                    <Form noValidate onSubmit={this.handleSubmit}>

                    <Form.Row>

                        <Form.Group controlId="startd">
                            <Form.Label column>Дата начала</Form.Label>
                            <Col>
                                <DatePicker
                                    value={this.state.startDate}
                                    onChange={date => this.handleChange(date, "startDate")}
                                    minDate={this.state.currentDate}
                                    format="dd.MM.yyyy"
                                    name="startDate"
                                />
                            </Col>
                        </Form.Group>

                        <Form.Group controlId="enddd">
                            <Form.Label column>Дата окончания</Form.Label>
                            <Col>
                                <DatePicker
                                    value={this.state.endDate}
                                    onChange={date => this.handleChange(date, "endDate")}
                                    minDate={this.state.currentDate}
                                    format="dd.MM.yyyy"
                                    name="endDate"
                                />
                            </Col>
                        </Form.Group>
                    </Form.Row>

                    <Form.Row>
                        <Form.Group controlId="name">
                            <Form.Label column>Имя автора</Form.Label>
                            <Col>
                                <Form.Control type="text" placeholder="Имя автора" length={50} maxLength={200}
                                              value={this.state.name} onChange={this.handleChangeName} name="name" />
                            </Col>
                        </Form.Group>
                    </Form.Row>

                    <Form.Row>
                        <Form.Group controlId="b">
                            <Col>
                                <Button name="name" variant="contained" color="primary" onClick={this.handleSubmit}>
                                    Создать
                                </Button>
                            </Col>
                        </Form.Group>
                    </Form.Row>

                </Form>
                </Row>
                </Container>
            </MuiPickersUtilsProvider>
        );
    }
}

export default BmdApp;