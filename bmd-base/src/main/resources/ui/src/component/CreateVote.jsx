import React, {Component} from 'react';
import {Button} from '@material-ui/core';
import {DatePicker, MuiPickersUtilsProvider} from "@material-ui/pickers";
import DateFnsUtils from '@date-io/date-fns';
import {Col, Container, Form, Row} from 'react-bootstrap';
import VoteDataService from '../service/VoteDataService';

class CreateVote extends Component {

    constructor(props) {
        super(props);
        this.state = {
            startDate: new Date(),
            endDate: new Date(),
            currentDate: new Date(),
            name: "",
            voteId: ""
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
        //alert('Отправленное имя: ' + this.state.name + ' Дана начала' + this.state.startDate);
        event.preventDefault();
        //TODO change on real data
        VoteDataService.createVote({
            startDate: "01.11.2019",
            endDate: "01.12.2019",
            creator: this.state.name,
            bestDatesForCreator: ["11.11.2019", "12.11.2019"]
        }).then(response => {
            this.setState({
                startDate: new Date(),
                endDate: new Date(),
                currentDate: new Date(),
                name: "",
                voteId: response.data.id })
        })

    }

    render() {
        return (
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <Container>

                    <Row>
                        <h3>Создать опрос выбора лучшей даты</h3>
                    </Row>
                    {this.state.voteId && <div class="alert alert-success">Создано голосование {this.state.voteId}</div>}
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

export default CreateVote;