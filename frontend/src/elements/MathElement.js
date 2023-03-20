import React from 'react'
import { Button, ButtonToolbar,  ButtonGroup } from 'reactstrap'
import { InputGroup, Input } from 'reactstrap'

/**
 * Function which creates a new math element. 
 * @constructor
 */
export default function MathElement({id, type, name, formulaValue, handleDelete, handleUpArrow, handleDownArrow, handleOnChange}) {

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
              value={name}
              onChange={handleOnChange}
              
              />
        </InputGroup>
        </td>
        <td colSpan={3}>
        <InputGroup>
            <Input 
              name="formulaValue"
              placeholder="Enter formula" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '100%'}}
              value={formulaValue}
              onChange={handleOnChange}
              />
        </InputGroup>
        </td>
        <td></td>
        <td></td>
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
