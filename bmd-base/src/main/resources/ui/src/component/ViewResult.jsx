import React, {Component} from 'react';
import {Container, Row} from 'react-bootstrap';
import VoteDataService from '../service/VoteDataService';


class ViewResult extends Component {

    constructor(props) {
        super(props);
        this.state = {
            voteId: this.props.match.params.voteId,
            bestDay: "",
            bestDayWithCreator: "",
            bestDayVoters: [],
            bestDayWithCreatorVoters: []

        };

    }

    componentDidMount() {
        console.log("UI Get votes by id " + this.state.voteId);
        if (this.state.voteId.length === 0) {
            return
        }

        VoteDataService.getBestDates(this.state.voteId)
            .then(response => this.setState({
                bestDay: response.data.bestDay,
                bestDayWithCreator: response.data.bestDayWithCreator,
                bestDayVoters: response.data.bestDayVoters,
                bestDayWithCreatorVoters: response.data.bestDayWithCreatorVoters
            }))
    }

    render() {

        let {bestDay, bestDayWithCreator, bestDayVoters, bestDayWithCreatorVoters} = this.state;

        return (
            <Container>
                <Row><a href="/">Назад</a></Row>
                <Row>
                    <h3>Результаты голосования. ID голосования {this.state.voteId}.</h3>
                </Row>
                <Row><h4>Лучшая дата {bestDay}. Количество проголосовавших {bestDayVoters.length}.</h4></Row>
                <Row>Участники: </Row>

                {bestDayVoters.map((voterName, i) => {
                    return (<Row>{voterName}</Row>)
                })}

                <Row>
                    <h4>
                    Лучшая дата c автором {bestDayWithCreator}.
                    Количество проголосовавших {bestDayWithCreatorVoters.length}.</h4>
                </Row>
                <Row>Участники: </Row>

                {bestDayWithCreatorVoters.map((voterName, i) => {
                    return (<Row>{voterName}</Row>)
                })}

            </Container>
        );
    }
}

export default ViewResult;