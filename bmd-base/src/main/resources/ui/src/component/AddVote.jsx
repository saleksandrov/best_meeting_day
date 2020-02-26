import React, {Component} from 'react';
import DayPicker, {DateUtils} from 'react-day-picker';
import 'react-day-picker/lib/style.css';
import {Col, Container, Form, Row} from 'react-bootstrap';
import VoteDataService from '../service/VoteDataService';
import {DatePicker} from "@material-ui/pickers";
import {Button} from "@material-ui/core";


class AddVote extends Component {

    constructor(props) {
        super(props);
        this.state = {
            open_flag: false,
            selectedDays: []
        };

        this.setOpen = this.setOpen.bind(this);
        this.handleDayClick = this.handleDayClick.bind(this);
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


    render() {
        return (
            <div>
                <Container>

                    <Row>
                        <h1>Добавить голос</h1>
                    </Row>

                    {this.state.voteId &&
                    <div class="alert alert-success">Голос добавлен {this.state.voteId}</div>}

                    <Row>
                        <Form noValidate onSubmit={this.handleSubmit}>
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
                                    <Form.Control type="text" placeholder="Имя автора" length={50} maxLength={200}
                                                  value={this.state.name} onChange={this.handleChangeName} name="name" />
                                </Col>
                            </Form.Group>
                            <Form.Group controlId="b">
                                <Col>
                                    <Button name="name" variant="contained" color="primary" onClick={this.handleSubmit}>
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