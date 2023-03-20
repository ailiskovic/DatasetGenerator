import React from 'react'
import { Button, ButtonToolbar,  ButtonGroup } from 'reactstrap'
import { InputGroup, Input } from 'reactstrap'

/**
 * Function which creates a new normal distribution element. 
 * @constructor
 */
export default function NormalDistributionElement({id, type, name, muValue, sigmaValue,  handleDelete, handleUpArrow, 
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
              name="muValue"
              type="number"
              placeholder="Enter mu value" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '50%'}}
              value={muValue}
              onChange={handleOnChangeFloatOnly}
              />
        </InputGroup>
        </td>
        <td>
        <InputGroup>
            <Input 
              name="sigmaValue"
              type="number"
              placeholder="Enter sigma value" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '50%'}}
              value={sigmaValue}
              onChange={handleOnChangeFloatOnly}
              />
        </InputGroup>
        </td>
        <td></td>
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
