import React, {Component} from 'react';
import DayPicker, { DateUtils } from 'react-day-picker';
import 'react-day-picker/lib/style.css';

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

    handleDayClick(day, { selected }) {
        const { selectedDays } = this.state;
        if (selected) {
            const selectedIndex = selectedDays.findIndex(selectedDay =>
                DateUtils.isSameDay(selectedDay, day)
            );
            selectedDays.splice(selectedIndex, 1);
        } else {
            selectedDays.push(day);
        }
        this.setState({ selectedDays });
    }


    render() {
        return (
            <div>
                <DayPicker
                    selectedDays={this.state.selectedDays}
                    onDayClick={this.handleDayClick}
                />
            </div>
        );
    }
}

export default AddVote;