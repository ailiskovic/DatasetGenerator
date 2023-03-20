import React from "react";
import { UncontrolledDropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';
import {useState} from 'react';
import CreateNewSample from "./CreateNewSample";
import SelectExistingSample from "./SelectExistingSample";

/**
 * Function which creates a new dataset. 
 * @constructor
 */
export default function GenerateDataset(){

    const [isCreateNewSample, setIsCreateNewSample] = useState(true);
    const [isSelectExistingSample, setIsSelectExistingSample] = useState(false);

    /** Handler for creating a new dataset sample */
    function handleCreateNewSample(){
        if(!isCreateNewSample)
            setIsCreateNewSample(current => !current)
        if(isSelectExistingSample)
            setIsSelectExistingSample(current => !current)
    }

    /** Handler for selecting existing dataset sample*/
    function handleSelectExistingSample(){
        if(!isSelectExistingSample)
            setIsSelectExistingSample(current => !current)
        if(isCreateNewSample)
            setIsCreateNewSample(current => !current)
    }

    return(
       <>
       <div>
        <UncontrolledDropdown >
        <DropdownToggle caret >
        Sample Option
        </DropdownToggle>
         <DropdownMenu>
        <DropdownItem onClick={handleCreateNewSample}>
         Create New Sample 
        </DropdownItem>
        <DropdownItem divider />
        <DropdownItem onClick={handleSelectExistingSample}>
            Select Existing Sample
            </DropdownItem>
         </DropdownMenu>
        </UncontrolledDropdown>
        {isCreateNewSample && <CreateNewSample/>}
        {isSelectExistingSample && <SelectExistingSample/>}
        </div>
       </>
    );
}