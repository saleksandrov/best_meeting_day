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
            totalVotes: 0,
            creator: "",
            description: ""
        };

    }

    componentDidMount() {
        console.log("UI Get votes by id " + this.state.voteId);
        if (this.state.voteId.length === 0) {
            return;
        }

        VoteDataService.getBestDates(this.state.voteId)
            .then(response => this.setState({
                bestDay: response.data.bestDay,
                bestDayWithCreator: response.data.bestDayWithCreator,
                bestDayVoters: response.data.bestDayVoters,
                bestDayWithCreatorVoters: response.data.bestDayWithCreatorVoters,
                totalVotes: response.data.totalVotes,
                creator: response.data.creator,
                descr: response.data.description
            })).catch(error => {
               this.setState({
                   bestDay: "",
                   bestDayWithCreator: "",
                   bestDayVoters: "",
                   bestDayWithCreatorVoters: "",
                   totalVotes: "",
                   creator: "",
                   descr: ""
               })
           });
    }

    render() {

        let {bestDay, bestDayWithCreator, bestDayVoters, bestDayWithCreatorVoters, totalVotes, creator, descr} = this.state;
        bestDayWithCreator = bestDayWithCreator === null ? "" : bestDayWithCreator;
        const keys = ['value'];
        const commonProps = {
            width: 380,
            height: 250,
            margin: { top: 30, right: 50, bottom: 50, left: 45 },
            data: [
                   {'date': bestDay, 'value': bestDayVoters.length},
                   {'date': bestDayWithCreator + ' (с организатором)', 'value': bestDayWithCreatorVoters.length}],
            indexBy: 'date',
            keys,
            padding: 0.2,
            labelTextColor: 'inherit:darker(1.4)',
            labelSkipWidth: 5,
            labelSkipHeight: 5,
            axisBottom: {tickSize: 6, tickPadding: 5, tickRotation: 0, legend: 'Даты', legendPosition: 'middle', legendOffset: 40 },
            axisLeft: {tickSize: 6, tickPadding: 5, tickRotation: 0, legend: 'Количество голосов', legendPosition: 'middle', legendOffset: -40 }
        }

        return (
            <Container>
                <Row><a href="/">Назад</a></Row>
                <br/>
                <Row>
                    <h3>Результаты голосования</h3>
                </Row>
                <Row>
                    <h5>Организатор: {creator}</h5>
                </Row>

                <Row>Всего проголосовало: {totalVotes} </Row>

                <Row>Цель встречи: {descr} </Row>

                <Row>Для просмотра списка участников нажмите/наведите мышкой на столбец</Row>

                <Row>
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
                    </Row>

            </Container>
        );
    }
}

export default ViewResult;