import React, { useState } from 'react';
import GenerateDataset from './GenerateDataset';
import { Button } from 'reactstrap';

/**
 * Function which creates a homepage.
 * @constructor
 */
export default function HomePage() {
  const [isGenerateShown, setIsGenerateShown] = useState(false);

  /** Handler for generating a new dataset */
  function handleGenerateClick() {
    setIsGenerateShown(!isGenerateShown);
    
  }

  return (
    <div>
      <Button onClick={handleGenerateClick}>
        {isGenerateShown ? 'Close generate a new dataset' : 'Generate a new dataset'}
      </Button>
      {isGenerateShown && <GenerateDataset />}
    </div>
  );
}