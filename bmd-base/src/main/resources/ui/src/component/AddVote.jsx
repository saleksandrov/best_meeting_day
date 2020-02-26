import React, {Component} from 'react';
import MultipleDatesPicker from '@randex/material-ui-multiple-dates-picker'
import {Button} from '@material-ui/core';

class AddVote extends Component {

    constructor(props) {
        super(props);
        this.state = {
            open_flag: false
        };

        this.setOpen = this.setOpen.bind(this);
    }

    setOpen() {
        this.state.open_flag = !this.state.open_flag;
    }

    render() {
        return (
            <div>
                <Button onClick={this.setOpen()}  name="selectdates" variant="contained" color="primary">
                    Выберите даты
                </Button>
                <MultipleDatesPicker

                    open={this.state.open_flag}
                    selectedDates={[]}
                    onSubmit={dates => console.log('selected dates', dates)} />
            </div>
        );
    }
}

export default AddVote;