import React from "react";
import { UncontrolledDropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';
import {useState} from 'react';
import CreateNewSample from "./CreateNewSample";

/**
 * Function which creates a new dataset. 
 * @constructor
 */
export default function GenerateDataset(){

    const [isCreateNewSample, setIsCreateNewSample] = useState(true);

    /** Handler for creating a new dataset sample */
    function handleCreateNewSample(){
        if(!isCreateNewSample)
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
         </DropdownMenu>
        </UncontrolledDropdown>
        {isCreateNewSample && <CreateNewSample/>}
        </div>
       </>
    );
}