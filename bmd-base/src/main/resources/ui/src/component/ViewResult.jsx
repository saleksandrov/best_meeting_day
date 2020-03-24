import React, {Component} from 'react';
import {Container, Row} from 'react-bootstrap';
import VoteDataService from '../service/VoteDataService';
import { Bar } from '@nivo/bar'

class ViewResult extends Component {

    constructor(props) {
        super(props);
        this.state = {
            voteId: this.props.match.params.voteId,
            bestDay: "",
            bestDayWithCreator: "",
            bestDayVoters: [],
            bestDayWithCreatorVoters: [],
            totalVotes: 0
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
                bestDayWithCreatorVoters: response.data.bestDayWithCreatorVoters,
                totalVotes: response.data.totalVotes
            }))
    }

    render() {

        let {bestDay, bestDayWithCreator, bestDayVoters, bestDayWithCreatorVoters, totalVotes} = this.state;
        const keys = ['value'];
        const commonProps = {
            width: 800,
            height: 400,
            margin: { top: 60, right: 80, bottom: 60, left: 80 },
            data: [
                   {'date': bestDay, 'value': bestDayVoters.length},
                   {'date': bestDayWithCreator +' (Лучшая дата с автором)', 'value': bestDayWithCreatorVoters.length}],
            indexBy: 'date',
            keys,
            padding: 0.2,
            labelTextColor: 'inherit:darker(1.4)',
            labelSkipWidth: 16,
            labelSkipHeight: 16,
            axisBottom: {tickSize: 5, tickPadding: 5, tickRotation: 0, legend: 'Даты', legendPosition: 'middle', legendOffset: 32 },
            axisLeft: {tickSize: 5, tickPadding: 5, tickRotation: 0, legend: 'Количество голосов', legendPosition: 'middle', legendOffset: -40 }
        }

        return (
            <Container>
                <Row><a href="/">Назад</a></Row>
                <br/>
                <Row>
                    <h3>Результаты голосования. ID голосования {this.state.voteId}.</h3>
                </Row>

                <p>Всего проголосовало {totalVotes} </p>

                <Bar
                        {...commonProps}
                        tooltip={({ id, value, color, index }) => (
                            <strong style={{ color }}>
                                { index === 0 ?
                                   bestDayVoters.map((voterName, i) => {
                                                       return (<p>{voterName}</p>)
                                                   })
                                   :
                                   bestDayWithCreatorVoters.map((voterName, i) => {
                                                       return (<p>{voterName}</p>)
                                                   })

                                }
                            </strong>
                        )}
                        theme={{ tooltip: { container: { background: '#444', }, }, }}
                    />

            </Container>
        );
    }
}

export default ViewResult;