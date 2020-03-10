import React, {Component} from 'react';
import DayPicker, {DateUtils} from 'react-day-picker';
import 'react-day-picker/lib/style.css';
import {Col, Container, Form, Row} from 'react-bootstrap';
import VoteDataService from '../service/VoteDataService';
import {Button} from "@material-ui/core";
import moment from 'moment';

class AddVote extends Component {

    constructor(props) {
        super(props);
        this.state = {
            voteId: this.props.match.params.voteId,
            startDate: "",
            endDate: "",
            creator: "",
            author: "",
            open_flag: false,
            selectedDays: [],
            wasSent: false,
            isVisible: true
        };

        this.setOpen = this.setOpen.bind(this);
        this.handleDayClick = this.handleDayClick.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChangeName = this.handleChangeName.bind(this);
    }

    componentDidMount() {
        console.log("UI Get votes by id " + this.state.voteId);
        if (this.state.voteId.length === 0) {
            return
        }

        VoteDataService.getVote(this.state.voteId)
            .then(response => this.setState({
                startDate: response.data.startDate,
                endDate: response.data.endDate,
                creator: response.data.creator
            }))
    }

    setOpen() {
        this.state.open_flag = !this.state.open_flag;
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
        if (this.state.wasSent) return;
        let dates = [];
        this.state.selectedDays.forEach((date) => {
            dates.push(moment(date).format('DD.MM.YYYY'))
        });
        VoteDataService.addVote({
            author: this.state.author,
            bestDates: dates
        }, this.state.voteId).then(response => {
            this.setState({
                wasSent: true,
                isVisible: false
            })
        })
    }

    render() {

        let {creator, startDate, endDate, isVisible} = this.state;

        return (
                <div>
                    <Container>
                        <Row><a href="/">Назад</a></Row>
                        <Row>
                            <h3>Добавить голос. ID голосования {this.state.voteId}.</h3>
                        </Row>

                        {this.state.wasSent &&
                        <div class="alert alert-success">Голос добавлен. ID голосования {this.state.voteId}</div>}
                        <Row style={isVisible ? {} : { display: 'none' }}>
                            <Form noValidate onSubmit={this.handleSubmit}>

                                <div>Создатель {creator}</div>
                                <div>Дата начала {startDate}</div>
                                <div>Дата окончания {endDate}</div>

                                <Form.Group controlId="dates">
                                    <Form.Label column>Даты</Form.Label>
                                    <Col>
                                        <DayPicker
                                            selectedDays={this.state.selectedDays}
                                            onDayClick={this.handleDayClick}
                                        />
                                    </Col>
                                </Form.Group>
                                <Form.Group controlId="name">
                                    <Form.Label column>Имя автора</Form.Label>
                                    <Col>
                                        <Form.Control type="text" placeholder="Имя автора" length={50}
                                                      maxLength={200}
                                                      value={this.state.author} onChange={this.handleChangeName}
                                                      name="author"/>
                                    </Col>
                                </Form.Group>
                                <Form.Group controlId="b">
                                    <Col>
                                        <Button name="name" variant="contained" color="primary"
                                                onClick={this.handleSubmit}>
                                            Создать
                                        </Button>
                                    </Col>
                                </Form.Group>
                            </Form>
                        </Row>
                    </Container>
                </div>
        );
    }
}

export default AddVote;