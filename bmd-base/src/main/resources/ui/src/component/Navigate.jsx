import React, {Component} from 'react';
import {Col, Form, Row} from "react-bootstrap";
import {Button} from "@material-ui/core";

class Navigate extends Component {

    constructor(props) {
        super(props);
        this.state = {
            voteId: ""
        };

        this.handleChangeName = this.handleChangeName.bind(this);
    }

    handleChangeName(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    render() {
        return (
            <div>
                <Form noValidate>
                    <Form.Group controlId="createvote">
                        <Row>
                        <Col>
                           <p>Сервис выбора лучшей даты встречи подбирает 2 ближайшие
                           даты с максимальным количеством участников.
                           </p>
                           <p>
                           Первая дата подбирается без учета пожеланий организатора, а вторая дата - та которая подходит организатору.
                           После создания голосования сохраните ID и используйте его для добавления голоса и просмотра результатов.
                           </p>
                        </Col>
                        </Row>
                    </Form.Group>

                    <Form.Group controlId="createvote">
                        <Row>
                            <Col>
                                <Button name="addvote" variant="contained" color="primary"
                                        onClick={() => this.props.history.push(`/createvote`)}>
                                    Создать голосование
                                </Button>
                            </Col>
                        </Row>
                    </Form.Group>

                    <Form.Group controlId="addvote">
                        <Row>
                            <Col>
                                <Form.Control type="text" placeholder="ID голосования"
                                              length={40} maxLength={40}
                                              value={this.state.voteId} onChange={this.handleChangeName} name="voteId"/>
                            </Col>
                            <Col>
                                <Button name="addvote" variant="contained" color="primary"
                                        onClick={() => this.props.history.push(`/addvote/${this.state.voteId}`)}>
                                    Проголосовать
                                </Button>
                            </Col>
                        </Row>
                    </Form.Group>

                    <Form.Group controlId="viewresult">
                        <Row>
                            <Col>
                                <Form.Control type="text" placeholder="ID голосования"
                                              length={40} maxLength={40}
                                              value={this.state.voteId} onChange={this.handleChangeName} name="voteId"/>
                            </Col>
                            <Col>
                                <Button name="viewresult" variant="contained" color="primary"
                                        onClick={() => this.props.history.push(`/viewresult/${this.state.voteId}`)}>
                                    Посмотреть результаты
                                </Button>
                            </Col>
                        </Row>
                    </Form.Group>
                </Form>
            </div>
        );
    }
}

export default Navigate;