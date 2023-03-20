import React from 'react'
import {useState} from 'react';
import {  Table } from 'reactstrap'
import { InputGroup, Input, InputGroupText } from 'reactstrap'
import { UncontrolledDropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap'
import IntegerElement from './elements/IntegerElement';
import FloatElement from './elements/FloatElement';
import IdElement from './elements/IdElement';
import NormalDistributionElement from './elements/NormalDistributionElement';
import BinomialDistributionElement from './elements/BinomialDistributionElement';
import CauchyDistributionElement from './elements/CauchyDistributionElement';
import MathElement from './elements/MathElement';
import HawkesElement from './elements/HawkesElement';
import styles from './style.css';
import TimeStampElement from './elements/TimeStampElement';
import { Button } from 'reactstrap';
import axios from 'axios'
import RandomWalkNormalDistributionElement from './elements/RandomWalkNormalDistributionElement';
import RandomWalkBinomialDistributionElement from './elements/RandomWalkBinomialDistributionElement';
import RandomWalkCauchyDistributionElement from './elements/RandomWalkCauchyDistributionElement';

const elementTypes = ['ID', 'Integer', 'Float', 'Timestamp', 'Normal_Distribution', 'Binomial_Distribution',
                      'Cauchy_Distribution', 'Random_Walk', 'Hawkess_Process', 'Math', 'Random_Walk_Normal_Distribution',
                    'Random_Walk_Binomial_Distribution', 'Random_Walk_Cauchy_Distribution']
const types = ['increase', 'decrease'];
const timeUnit = ['year', 'month', 'day', 'hour', 'minute', 'second', 'milisecond']

/**
 * Function which creates a new sample 
 * @constructor
 */
export default function CreateNewSample() {

  let [elementObjects, updateElementObjects] = useState([]);
  let [numberOfRows, updateNumberOfRows] = useState("");
  let [nameOfFile, updateNameOfFile] = useState("");

  /** Handler for changing the state of increase/decrease button */
  const handleButtonStateChange = (event, id) => {
    const newArray = [...elementObjects]
    
    if(event.target.name === types[0])
      newArray[id].increase = true;
    else if(event.target.name === types[1])
      newArray[id].increase = false; 
    updateElementObjects(newArray)
  }

  /** Handler for changing float values */
  const handleOnChangeFloatOnly = (event, id) => {
    const newArray = [...elementObjects]
    var objectName = event.target.name
    newArray[id][objectName] = event.target.value;
    updateElementObjects(newArray)
  }

  /** Handler for changing integer values */
  const handleOnChangeIntegerOnly = (event, id) => {
    var value = event.target.value
    if(value.includes('.'))
      value = value.replace('.', '')
    const newArray = [...elementObjects]
    var objectName = event.target.name
    newArray[id][objectName] = value;
    updateElementObjects(newArray)
  }

  /** Handler for changing any value */
  const handleOnChange = (event, id) => {
    const newArray = [...elementObjects]
    var objectName = event.target.name
    newArray[id][objectName] = event.target.value;
    updateElementObjects(newArray)
  }

  /** Handler for changing date values */
  const handleOnDateChange = (event, id) => {

    const newArray = [...elementObjects]
    if(event !== null)
      newArray[id]["dateValue"] = new Date(event.toString());
    else 
    newArray[id]["dateValue"] = new Date(0);
    updateElementObjects(newArray)
  }

  /** Handler for changing time values */
  const handleOnTimeValueChange = (value, id) => {
    const newArray = [...elementObjects]
    newArray[id].timeValue = value;
    updateElementObjects(newArray)
  }

  /** Handler for changing time units */
  const handleOnTimeUnitChange = (event, id) => {
    const newArray = [...elementObjects]
    newArray[id].timeUnit = event.target.value;
    updateElementObjects(newArray)
  }

  /** Handler for changing up button state */
  const handleUpArrow = (id) => { 
    if(id > 0 && id < elementObjects.length)
    {
      let newArray = elementObjects
      let tmp = newArray[id-1]
      newArray[id-1] = newArray[id] 
      newArray[id] = tmp
      updateElementObjects(updateIds(newArray))
    }
  }

  /** Handler for changing down button state */
  const handleDownArrow = (id) => {
    if(id >= 0 && id < elementObjects.length-1)
    {
      let newArray = elementObjects
      let tmp = newArray[id+1]
      newArray[id+1] = newArray[id] 
      newArray[id] = tmp
      updateElementObjects(updateIds(newArray))
    }
  }

  /** Handler for deleting element */
  const handleDelete = (id) => {
    const newArray = elementObjects.filter((item) => item.id !== id) 
    updateElementObjects(updateIds(newArray))
  }

  /** Handler for updating the value of id */
  function updateIds(myArray)
  {
    let idCounter = 0;
    return myArray.map(el => {
      el.id = idCounter
      idCounter++
      return el
    })
  }

  /** Handler for adding integer element */
  function handleIntegerElement()
  { 
    let id = elementObjects.length
    updateElementObjects( arr => [...arr, {id: id, type: elementTypes[1], name:"", startValue:"", unitValue:"", 
                                          increase:true}]);
  }

  /** Handler for adding float element */
  function handleFloatElement()
  { 
    let id = elementObjects.length
    updateElementObjects( arr => [...arr, {id: id, type: elementTypes[2], name:"", startValue:"", unitValue:"", 
                                          increase:true}]);
   }

  /** Handler for adding id element */   
  function handleIdElement()
  { 
    let id = elementObjects.length
    updateElementObjects( arr => [...arr, {id: id, type: elementTypes[0], name:"", startValue:"",  increase:true}]);
  }

  /** Handler for adding time stamp element */
  function handleTimeStampElement()
  {
    let id = elementObjects.length
    updateElementObjects( arr => [...arr, {id: id, type: elementTypes[3], name:"", dateValue: new Date(), 
                                          timeValue: "0:0:0.0" , unitValue:"",  timeUnit: timeUnit[0], increase:true}]);
  }

  /** Handler for adding normal distribution element */
  function handleNormalDistributionElement()
  { 
    let id = elementObjects.length
    updateElementObjects( arr => [...arr, {id: id, type: elementTypes[4], name:"", muValue:"", sigmaValue:""}]);
  }

  /** Handler for adding binomial distribution element */
  function handleBinomialDistributionElement()
  { 
    let id = elementObjects.length
    updateElementObjects( arr => [...arr, {id: id, type: elementTypes[5], name:"", nTrialsValue:"", pValue:""}]);
  }

  /** Handler for adding cauchy distribution element */
  function handleCauchyDistributionElement()
  { 
    let id = elementObjects.length
    updateElementObjects( arr => [...arr, {id: id, type: elementTypes[6], name:"", x0Value:"", lambdaValue:""}]);
  }

  /** Handler for adding hawkess element */
  function handleHawkesElement()
  { 
    let id = elementObjects.length
    updateElementObjects( arr => [...arr, {id: id, type: elementTypes[8], name:"", lambdaValue:"", alphaValue:"", 
    betaValue:""}]);
  }

  /** Handler for adding math element */
  function handleMathElement()
  { 
    let id = elementObjects.length
    updateElementObjects( arr => [...arr, {id: id, type: elementTypes[9], name:"", formulaValue:""}]);
  }

  /** Handler for adding random walk normal distribution element */
  function handleRandomWalkNormalDistributionElement()
  { 
    let id = elementObjects.length
    updateElementObjects( arr => [...arr, {id: id, type: elementTypes[10], name:"", x0Value:"", muValue:"", 
                                                                                                      sigmaValue:""}]);
  }

  /** Handler for adding random walk binomial distribtion element */
  function handleRandomWalkBinomialDistributionElement()
  { 
    let id = elementObjects.length
    updateElementObjects( arr => [...arr, {id: id, type: elementTypes[11], name:"", x0Value:"", nTrialsValue:"", 
                                                                                                          pValue:""}]);
  }

  /** Handler for adding random walk cacuhy distribution element */
  function handleRandomWalkCauchyDistributionElement()
  { 
    let id = elementObjects.length
    updateElementObjects( arr => [...arr, {id: id, type: elementTypes[12], name:"", x0Value:"", lambdaValue:""}]);
  }
  
  /** Handler for clearing all elements */
  function clearAllElements()
  {
    updateElementObjects([])
  }

  /** Function for comparing two strings */
  function strCompare(str1,str2){
    return str1 === str2 ;
  } 

  /** Function for checking is the row number input field empty */
  function checkRowNumberInput(numberOfRows)
  {
    if(strCompare(numberOfRows, ""))
    {
      return false
    }
    return true  
  }

  /** Function for checking does the all elements have value in input field */
  function checkElementObjectsInput(elementObjects)
  {
    var toBreak = false;
    elementObjects.forEach(element => {
      // Integer
      if(element.type === elementTypes[1])
      {
        if(element.name === "" || element.startValue === "" || element.unitValue === "")
          toBreak = true;
      }
      // ID
      else if(element.type === elementTypes[0])
      {
        if(element.name === "" || element.startValue === "")
          toBreak = true;
      }
      // Float
      else if(element.type === elementTypes[2])
      {
        if(element.name === "" || element.startValue === "" || element.unitValue === "")
          toBreak = true;
      }
      // Timestamp
      else if(element.type === elementTypes[3])
      {
        if(element.name === "" || element.unitValue === "" || element.timeUnit === "")
          toBreak = true;
      }
      // Normal_Distribution
      else if(element.type === elementTypes[4])
      {
        if(element.name === "" || element.muValue === "" || element.sigmaValue === "")
          toBreak = true;
      }
      // Binomial_Distribution
      else if(element.type === elementTypes[5])
      {
        if(element.name === "" || element.nTrialsValue === "" || element.nTrialsValue === "" || element.pValue === "")
          toBreak = true;
      }
      // Cauchy_Distribution
      else if(element.type === elementTypes[6])
      {
        if(element.name === "" || element.x0Value === "" || element.lambdaValue === "")
          toBreak = true;
      }
      // Hawkess_Process
      else if(element.type === elementTypes[8])
      {
        if(element.name === "" || element.muValue === "" || element.alphaValue === "" || element.betaValue === "")
          toBreak = true;
      }
      // Math
      else if(element.type === elementTypes[9])
      {
        if(element.name === "" || element.formulaValue === "")
          toBreak = true;
      }
      // Random_Walk_Normal_Distribution
      else if(element.type === elementTypes[10])
      {
        if(element.name === "" || element.x0Vlaue === "" || element.muValue === "" || element.sigmaValue === "")
          toBreak = true;
      }
      // Random_Walk_Binomial_Distribution
      else if(element.type === elementTypes[11])
      {
        if(element.name === "" || element.x0Vlaue === "" || element.nTrialsValue === "" || element.pValue === "")
          toBreak = true;
      }
      // Random_Walk_Cauchy_Distribution
      else if(element.type === elementTypes[12])
      {
        if(element.name === "" || element.x0Vlaue === "" || element.lambdaValue === "" )
          toBreak = true;
      }
    });
    if(toBreak)
      return false;
    return true;
  }

  /** Function for sending the request to the backend side and change the file name */
  async function handleSubmit()
  {

    if(!checkRowNumberInput(numberOfRows))
    {
      alert("Please enter the number of rows!");
      return;
    } 

    if(nameOfFile === "")
    {
      alert("Please enter the name of the file");
      return;
    }

    if(!checkElementObjectsInput(elementObjects))
    {
      alert("Please fill all input fields!")
      return;
    }
    
    var objectToSend = {
      rowNumbers: numberOfRows, 
      rows: elementObjects
    }
    const result = await axios.post('http://localhost:8080/api/createNewDataset',objectToSend).then();

    var fileDownload = require('js-file-download');
    var fileName = nameOfFile + '.csv'
    fileDownload(result.data, fileName);
  }

  return (
    <>
    <div>
        <UncontrolledDropdown>
        <DropdownToggle caret  >
        Add new column
        </DropdownToggle>
         <DropdownMenu>
          <DropdownItem onClick={handleIntegerElement}>
              Integer Element
          </DropdownItem>
        <DropdownItem divider />
        <DropdownItem onClick={handleFloatElement}>
              Float Element
        </DropdownItem>
        <DropdownItem divider />
        <DropdownItem onClick={handleIdElement}>
              ID Element
        </DropdownItem>
        <DropdownItem divider />
        <DropdownItem onClick={handleTimeStampElement}>
            Time Stamp Element
        </DropdownItem>
        <DropdownItem divider />
        <DropdownItem onClick={handleNormalDistributionElement}>
            Normal Distribution Element
        </DropdownItem>
        <DropdownItem divider />
        <DropdownItem onClick={handleBinomialDistributionElement}>
            Binomial Distribution Element
        </DropdownItem>
        <DropdownItem divider />
        <DropdownItem onClick={handleCauchyDistributionElement}>
            Cauchy Distribution Element
        </DropdownItem>
        <DropdownItem divider />
        <DropdownItem onClick={handleRandomWalkNormalDistributionElement}>
            Random Walk (Normal Distribution) Element
        </DropdownItem>
        <DropdownItem divider />
        <DropdownItem onClick={handleRandomWalkBinomialDistributionElement}>
            Random Walk (Binomial Distribution) Element
        </DropdownItem>
        <DropdownItem divider />
        <DropdownItem onClick={handleRandomWalkCauchyDistributionElement}>
            Random Walk (Cauchy Distribution) Element
        </DropdownItem>
        <DropdownItem divider />
        <DropdownItem onClick={handleHawkesElement}> 
            Hawkes Process Element
        </DropdownItem>
        <DropdownItem divider />
        <DropdownItem onClick={handleMathElement}>
            Math Element
        </DropdownItem>
         </DropdownMenu>
        </UncontrolledDropdown>
        <InputGroup>
            <InputGroupText >Enter number of rows:</InputGroupText>
            <Input type="number" value={numberOfRows} onChange={e => updateNumberOfRows(e.target.value)} placeholder="number of rows" bsSize='sm' style={{ fontSize: 12, padding: 3, maxWidth: '20%'}}/>
        </InputGroup>
        <InputGroup>
            <InputGroupText >Enter the name of a file:</InputGroupText>
            <Input type="datetime" value={nameOfFile} onChange={e => updateNameOfFile(e.target.value)} placeholder="name of a file" bsSize='sm' style={{ fontSize: 12, padding: 3, maxWidth: '19.3%'}}/>
        </InputGroup>
    </div>
    <div className={styles.tab}>
        <Table hover>
        <thead>
          <tr className="table-secondary">
            <th width="2%">#</th>
            <th>Type</th>
            <th>Name</th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
        {elementObjects.map((el) => {
              if(el.type === elementTypes[1])
               return <IntegerElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} 
              handleDownArrow={()=>handleDownArrow(el.id)} handleOnChange={(event)=> handleOnChange(event, el.id)}
              handleOnChangeIntegerOnly={(event)=> handleOnChangeIntegerOnly(event, el.id)}
              handleButtonStateChange={(event) => handleButtonStateChange(event, el.id)}
              /> 
              else if(el.type === elementTypes[0])
                return <IdElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} 
                handleDownArrow={()=>handleDownArrow(el.id)} handleOnChange={(event)=> handleOnChange(event, el.id)}
                handleOnChangeIntegerOnly={(event)=> handleOnChangeIntegerOnly(event, el.id)}
                handleButtonStateChange={(event) => handleButtonStateChange(event, el.id)}
                />
              else if(el.type === elementTypes[2])
                return <FloatElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} 
                handleDownArrow={()=>handleDownArrow(el.id)} handleOnChange={(event)=> handleOnChange(event, el.id)}
                handleOnChangeFloatOnly={(event)=> handleOnChangeFloatOnly(event, el.id)}
                handleButtonStateChange={(event) => handleButtonStateChange(event, el.id)}
                /> 
              else if(el.type === elementTypes[3])
                return <TimeStampElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} 
                handleDownArrow={()=>handleDownArrow(el.id)} handleOnChange={(event)=> handleOnChange(event, el.id)} 
                handleOnDateChange={(event)=> handleOnDateChange(event, el.id)}
                handleOnChangeIntegerOnly={(event)=> handleOnChangeIntegerOnly(event, el.id)}
                handleButtonStateChange={(event) => handleButtonStateChange(event, el.id)}
                handleOnTimeUnitChange={(event) => handleOnTimeUnitChange(event, el.id)}
                handleOnTimeValueChange={(event) => handleOnTimeValueChange(event, el.id)} 
                />
              else if(el.type === elementTypes[4])
                return <NormalDistributionElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} 
                handleDownArrow={()=>handleDownArrow(el.id)} handleOnChange={(event)=> handleOnChange(event, el.id)}
                handleOnChangeFloatOnly={(event)=> handleOnChangeFloatOnly(event, el.id)}
                />

              else if(el.type === elementTypes[5])
                return <BinomialDistributionElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} 
                handleDownArrow={()=>handleDownArrow(el.id)} handleOnChange={(event)=> handleOnChange(event, el.id)}
                handleOnChangeFloatOnly={(event)=> handleOnChangeFloatOnly(event, el.id)}
                />  
              else if(el.type === elementTypes[6])
                return <CauchyDistributionElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} 
                handleDownArrow={()=>handleDownArrow(el.id)} handleOnChange={(event)=> handleOnChange(event, el.id)}
                handleOnChangeFloatOnly={(event)=> handleOnChangeFloatOnly(event, el.id)}
                />    
              else if(el.type === elementTypes[8])
                return <HawkesElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} 
                handleDownArrow={()=>handleDownArrow(el.id)} handleOnChange={(event)=> handleOnChange(event, el.id)}
                handleOnChangeFloatOnly={(event)=> handleOnChangeFloatOnly(event, el.id)}
                />
              else if(el.type === elementTypes[9])
                return <MathElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} 
                handleDownArrow={()=>handleDownArrow(el.id)} handleOnChange={(event)=> handleOnChange(event, el.id)}
                handleOnChangeFloatOnly={(event)=> handleOnChangeFloatOnly(event, el.id)}
                />
              else if(el.type === elementTypes[10])
                return <RandomWalkNormalDistributionElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} 
                handleDownArrow={()=>handleDownArrow(el.id)} handleOnChange={(event)=> handleOnChange(event, el.id)}
                handleOnChangeFloatOnly={(event)=> handleOnChangeFloatOnly(event, el.id)}
                />
              else if(el.type === elementTypes[11])
                return <RandomWalkBinomialDistributionElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} 
                handleDownArrow={()=>handleDownArrow(el.id)} handleOnChange={(event)=> handleOnChange(event, el.id)}
                handleOnChangeFloatOnly={(event)=> handleOnChangeFloatOnly(event, el.id)}
                />
                else if(el.type === elementTypes[12])
                return <RandomWalkCauchyDistributionElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} 
                handleDownArrow={()=>handleDownArrow(el.id)} handleOnChange={(event)=> handleOnChange(event, el.id)}
                handleOnChangeFloatOnly={(event)=> handleOnChangeFloatOnly(event, el.id)}
                />         
              return  <IntegerElement key={el.id} {...el}  handleDelete={() => handleDelete(el.id)} handleUpArrow={()=>handleUpArrow(el.id)} handleDownArrow={()=>handleDownArrow(el.id)}/>
        })}

          <tr>
            <th>
          <Button onClick={handleSubmit}>Submit</Button>        
          </th>
          <th>
          <Button onClick={clearAllElements} >Clear</Button>
          </th>
          </tr>
        </tbody>
        </Table>
    </div>
    </>
  )
}
