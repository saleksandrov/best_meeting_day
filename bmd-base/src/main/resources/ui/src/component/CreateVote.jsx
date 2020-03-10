import React, {Component} from 'react';
import {Button} from '@material-ui/core';
import {DatePicker, MuiPickersUtilsProvider} from "@material-ui/pickers";
import DayPicker, {DateUtils} from 'react-day-picker';
import 'react-day-picker/lib/style.css';
import DateFnsUtils from '@date-io/date-fns';
import {Col, Container, Form, Row} from 'react-bootstrap';
import VoteDataService, {HOST} from '../service/VoteDataService';
import moment from 'moment';
import {TelegramShareButton, WhatsappShareButton} from 'react-share';


class CreateVote extends Component {

    constructor(props) {
        super(props);
        this.state = {
            startDate: new Date(),
            endDate: new Date(),
            currentDate: new Date(),
            name: "",
            voteId: "",
            selectedDays: [],
            errorMsg: ""
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChangeName = this.handleChangeName.bind(this);
        this.handleDayClick = this.handleDayClick.bind(this);
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
        event.preventDefault();
        let dates = [];
        this.state.selectedDays.forEach((date) => {
            dates.push(moment(date).format('DD.MM.YYYY'))
        });

        VoteDataService.createVote({
            startDate: moment(this.state.startDate).format('DD.MM.YYYY'),
            endDate: moment(this.state.endDate).format('DD.MM.YYYY'),
            creator: this.state.name,
            bestDatesForCreator: dates
        }).then(response => {
            this.setState({
                startDate: new Date(),
                endDate: new Date(),
                currentDate: new Date(),
                name: "",
                voteId: response.data.id,
                errorMsg: ""})
        }).catch(error => {
               this.setState({
                  errorMsg: error.response.data
               })
            }
        )

    }

    handleDayClick(day, {selected}) {
        const {selectedDays} = this.state;
        if (selected) {
            const selectedIndex = selectedDays.findIndex(selectedDay =>
                DateUtils.isSameDay(selectedDay, day)
            );
            selectedDays.splice(selectedIndex, 1);
        } else {
            selectedDays.push(day);
        }
        this.setState({selectedDays});
    }

    render() {

        let linkTOAddVote = `/addvote/${this.state.voteId}`;
        let urlToAddVote = HOST + linkTOAddVote;

        return (
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <Container>
                    <Row><a href="/">Назад</a></Row>
                    <Row>
                        <h3>Создать опрос выбора лучшей даты</h3>
                    </Row>
                    {
                        this.state.voteId &&
                        <div class="alert alert-success">Создано голосование {this.state.voteId}.
                            <a href={linkTOAddVote}>Ссылка на голование </a>
                            <TelegramShareButton url={urlToAddVote} title="Проголосовать за дату" children=""/>
                            <WhatsappShareButton url={urlToAddVote} title="Проголосовать за дату" children=""/>
                        </div>
                    }
                    {
                        this.state.errorMsg &&
                        <div class="alert alert-danger">{this.state.errorMsg} </div>
                    }
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
                                <Form.Group controlId="dates">
                                    <Form.Label column>Даты</Form.Label>
                                    <Col>
                                        <DayPicker
                                            selectedDays={this.state.selectedDays}
                                            onDayClick={this.handleDayClick}
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