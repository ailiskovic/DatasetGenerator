import React from 'react'
import { Button, ButtonToolbar,  ButtonGroup } from 'reactstrap'
import { InputGroup, Input } from 'reactstrap'

/**
 * Function which creates a new hawkess element. 
 * @constructor
 */
export default function HawkesElement({id, type, name, lambdaValue, alphaValue, betaValue,  handleDelete, handleUpArrow, 
                                                    handleDownArrow, handleOnChange, handleOnChangeFloatOnly}) {

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
              name="lambdaValue"
              type="number"
              placeholder="Enter lambda value" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '50%'}}
              value={lambdaValue}
              onChange={handleOnChangeFloatOnly}
              />
        </InputGroup>
        </td>
        <td>
        <InputGroup>
            <Input 
              name="alphaValue"
              type="number"
              placeholder="Enter alpha value" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '50%'}}
              value={alphaValue}
              onChange={handleOnChangeFloatOnly}
              />
        </InputGroup>
        </td>
        <td >
        <InputGroup>
            <Input 
              name="betaValue"
              type="number"
              placeholder="Enter beta value" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '50%'}}
              value={betaValue}
              onChange={handleOnChangeFloatOnly}
              />
        </InputGroup>
        </td>
        <td ></td>
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
