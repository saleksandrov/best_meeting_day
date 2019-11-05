import React, { Component } from 'react';
import DatePicker from 'react-date-picker';

class BmdApp extends Component {

 constructor(props) {
    super(props);
    this.state = {
        startDate: new Date(),
        endDate: new Date(),
        currentDate: new Date(),
        name: ""
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
      const target = event.target;
      const value = target.value;
      const name = target.name;

      this.setState({
        [name]: value
      });
  }

   handleSubmit(event) {
      alert('Отправленное имя: ' + this.state.name);
      event.preventDefault();
    }

  render() {
    return (
      <div className="App">
        <h1>Best Meeting Day</h1>
        <form onSubmit={this.handleSubmit}>
            <div>
                <label>Дата начала:
                <DatePicker
                    onChange={this.handleChange}
                    value={this.state.startDate}
                    name="startDate"
                    minDate={this.state.currentDate}
                    format="dd.MM.yyyy"
                />
                </label>
            </div>

            <div>
                <label>Дата окончания:
                <DatePicker
                   onChange={this.handleChange}
                   value={this.state.endDate}
                   name="endDate"
                   minDate={this.state.currentDate}
                   format="dd.MM.yyyy"
                 />
                 </label>
            </div>

            <div>
                <label>Имя:
                   <input type="text" name="name" value={this.state.name} onChange={this.handleChange} />
                </label>
                <input type="submit" value="Создать" />
            </div>
         </form>
    </div>
    );
  }
}

export default BmdApp;