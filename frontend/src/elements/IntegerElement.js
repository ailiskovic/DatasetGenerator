import React from 'react'
import { Button, ButtonToolbar,  ButtonGroup} from 'reactstrap'
import { InputGroup, Input } from 'reactstrap'
import styled from 'styled-components';


const ButtonToggle = styled(Button)`
  opacity: 0.6;
  ${({ active }) =>
    active &&
    `
    opacity: 1;
  `}
`;

const types = ['Increase', 'Decrease'];

/**
 * Function which creates a new integer element. 
 * @constructor
 */
export default function IntegerElement({id, type, name, startValue, unitValue, increase, handleDelete, handleUpArrow, handleDownArrow, handleOnChange,
                                        handleOnChangeIntegerOnly, handleButtonStateChange}) {


  return (
    <>
    <tr>
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
        <InputGroup>
            <Input 
              name="startValue"
              type="number"
              placeholder="Enter start value" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '50%'}}
              value={startValue}
              onChange={handleOnChangeIntegerOnly}
              />
        </InputGroup>
        </td>
        <td>
        <InputGroup>
            <Input 
              name="unitValue"
              type="number"
              placeholder="Enter unit value" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '50%'}}
              value={unitValue}
              onChange={handleOnChangeIntegerOnly}
              />
        </InputGroup>
        </td>
        <td></td>
        <td></td>
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
            <ButtonGroup size='sm' className="me-3">
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
