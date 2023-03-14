import React, { useState } from 'react'
import DatePicker from "react-datepicker";  
import "react-datepicker/dist/react-datepicker.css";  

import { Button, ButtonToolbar,  ButtonGroup, Form } from 'reactstrap'
import { Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import styled  from 'styled-components';
import { InputGroup, Input, } from 'reactstrap'

const ButtonToggle = styled(Button)`
  opacity: 0.6;
  ${({ active }) =>
    active &&
    `
    opacity: 1;
  `}
`;

const types = ['Increase', 'Decrease'];
const timeUnit = ['year', 'month', 'day', 'hour', 'minute', 'second', 'milisecond']


export default function TimeStampElement({id, type, name, dateValue, unitValue, timeValue, increase,  handleDelete, handleUpArrow, handleDownArrow, handleOnChange,
                                          handleOnDateChange, handleOnChangeIntegerOnly, handleButtonStateChange, handleOnTimeUnitChange, handleOnTimeValueChange}) {
  
  const [modal, setModal] = useState(false);

  const toggle = (id) => {
    setModal(!modal)
    handleOnTimeValueChange(time, id)

  };                                          
              
                               

  let [hours, updateHours] = useState(0);
  let [minutes, updateMinutes] = useState(0);
  let [seconds, updateSeconds] = useState(0);
  let [miliseconds, updateMiliseconds] = useState(0);

  let time = hours + ":" + minutes + ":" + seconds + "." + miliseconds;

  const updateTImeClick = (event, id) => {
    toggle()
    console.log(time)
  }

  return (
    <>
    <tr >
        <td>{id + 1}</td>
        <td>{type}</td>
        <td>
        <InputGroup>
            <Input 
              name='name'
              placeholder="Enter name of the column" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '90%'}}
              onChange={handleOnChange}
              value={name}
              />
        </InputGroup>
        </td>
        <td>

        <DatePicker  size='sm' selected={dateValue} onChange={(date) =>   
handleOnDateChange(date)} />
        
        </td>
        <td>
        <InputGroup>
        <Input
          disabled
          bsSize="sm"
          style={{ fontSize: 12, maxWidth: '50%'}}
          value={time}
        />
        <Button color="primary" size="sm"
          onClick={updateTImeClick}
        >
                  Pick time
        </Button>
        </InputGroup>
        <Modal isOpen={modal} toggle={toggle} anomation={false}>
        <ModalHeader toggle={toggle}>Choose a time</ModalHeader>
        <ModalBody>

        <h6>Hour: {hours}</h6>
        <Input type='range'
        max={23}
        min={0}
        onChange={(e) => updateHours(e.target.value)}
        value={hours}/>
        
        <h6>Minute: {minutes}</h6>
        <Input type='range'
        max={59}
        min={0}
        onChange={(e) => updateMinutes(e.target.value)}
        value={minutes}/>
  

        <h6>Second: {seconds}</h6>
        <Input type='range'
        max={59}
        min={0}
        onChange={(e) => updateSeconds(e.target.value)}
        value={seconds}/>

        <h6>Milisecond: {miliseconds}</h6>
        <Input type='range'
        max={999}
        min={0}
        onChange={(e) => updateMiliseconds(e.target.value)}
        value={miliseconds}/>

        </ModalBody>

        <ModalFooter>
          <Button color="primary" onClick={toggle}>
            Submit
          </Button>{' '}
          <Button color="secondary" onClick={toggle}>
            Cancel
          </Button>
        </ModalFooter>
      </Modal>
        </td>
        
        <td >
        <InputGroup>
            <Input
              name="unitValue"
              type="number" 
              placeholder="Enter value" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '50%'}}
              value={unitValue}
              onChange={handleOnChangeIntegerOnly}
              />
        </InputGroup>
        </td>
        
        <td>
        <Form>
        <Input
            className="mb-3"
            type="select"
            bsSize="sm"
            style={{ fontSize: 12, maxWidth: '100%'}}
            onChange={handleOnTimeUnitChange}
            >
            <option value={timeUnit[0]}>
              Year
            </option>
            <option value={timeUnit[1]}>
              Month
            </option>
            <option value={timeUnit[2]}>
              Day
            </option>
            <option value={timeUnit[3]}>
              Hour
            </option>
            <option value={timeUnit[4]}>
              Minute
            </option>
            <option value={timeUnit[5]}>
              Second
            </option>
            <option value={timeUnit[6]}>
              Milisecond
            </option>                         
        </Input>
        </Form>

        </td>
        <td>

        <ButtonToolbar>
          <ButtonGroup size='sm' className="me-2">
            <ButtonToggle key={types[0]}
            name="increase"
            active={increase === true} 
            onClick={handleButtonStateChange}> 
                Increase
          </ButtonToggle>
          <ButtonToggle key={types[1]}
          name="decrease"
          active={increase === false}
          onClick={handleButtonStateChange}>
                Decrease
           </ButtonToggle>
          </ButtonGroup>
        </ButtonToolbar>
       
        </td>
        <td>
          
          <ButtonToolbar>
            <ButtonGroup className="me-3"
             size='sm'>
              <Button color="primary" onClick={handleUpArrow}> 
                  Up
            </Button>
            <Button color="primary" onClick={handleDownArrow}>
                  Down
             </Button>
            </ButtonGroup>
          </ButtonToolbar>
          
        </td>
        
        <td>
        <Button color="danger" size='sm' onClick={handleDelete}>Clear</Button>
        </td>
    </tr>
    </>
  )
}
