import React from 'react'
import GenerateDataset from './GenerateDataset';
import {useState} from 'react';
import { Button } from 'reactstrap';

/**
 * Function which creates a homepage. 
 * @constructor
 */
export default function HomePage() 
{
    const [isGenerateShown, setIsGenerateShown] = useState(false);

  /** Handler for generating a new dataset */
  function handleGenerateClick(){
    if(!isGenerateShown)
        setIsGenerateShown(current => !current);     
  }

  return (
    <div>
        <Button onClick={handleGenerateClick} >Generate a new dataset</Button>
        {isGenerateShown && <GenerateDataset/>}
    </div>
  )
}
