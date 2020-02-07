import React, {Component} from 'react';
import MultipleDatesPicker from '@randex/material-ui-multiple-dates-picker'
import {Button} from '@material-ui/core';

const [open, setOpen] = useState(false)

class AddVote extends Component {

    constructor(props) {
        super(props);
        //const [open, setOpen] = useState(false)
    }

    render() {
        return (
            <div>
                <Button onClick={() => setOpen(!open)}>
                    Select Dates
                </Button>
                <MultipleDatesPicker
                    open="true"
                    selectedDates={[]}
                    onSubmit={dates => console.log('selected dates', dates)} />
                    </div>
        );
    }
}

export default AddVote;