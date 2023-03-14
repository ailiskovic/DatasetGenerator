import React from 'react'
import { Button, ButtonToolbar,  ButtonGroup } from 'reactstrap'
import { InputGroup, Input } from 'reactstrap'

export default function RandomWalkBinomialDistributionElement({id, type, name, x0Value, nTrialsValue, pValue,  handleDelete, 
                                                    handleUpArrow, handleDownArrow, handleOnChange, 
                                                    handleOnChangeFloatOnly}) {

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

        <td >
        <InputGroup>
            <Input 
              name="x0Value"
              type="number"
              placeholder="Enter x0 value" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '50%'}}
              value={x0Value}
              onChange={handleOnChangeFloatOnly}
              />
        </InputGroup>
        </td>
        <td>
        <InputGroup>
            <Input 
              name="nTrialsValue"
              type="number"
              placeholder="Enter n trials value" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '60%'}}
              value={nTrialsValue}
              onChange={handleOnChangeFloatOnly}
              />
        </InputGroup>
        </td>
        <td>
        <InputGroup>
            <Input 
              name="pValue"
              type="number"
              placeholder="Enter p value" 
              bsSize='sm' 
              style={{ fontSize: 12, maxWidth: '50%'}}
              value={pValue}
              onChange={handleOnChangeFloatOnly}
              />
        </InputGroup>
        </td>
        <td >
        
        </td>
        <td>
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
