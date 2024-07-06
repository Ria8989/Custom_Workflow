import React, { useEffect, useState } from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Chip, Divider, Table, TableBody, TableRow, tableCellClasses, FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import ActionSelector from './ActionSelector';


const LinkCard = ({ taskInfo, alltasks, handleDropdownChange }) => {
    const [allActionList, setAllActionList] = useState([]);
    const [taskAction, setTaskAction] = useState({
      taskId: taskInfo.taskId,
      action: '',
      nextTaskId: []
    });
    const extractActionsToList = (eligibleActionString) => {
      if (!eligibleActionString) return []; // Return an empty array if eligibleActionString is falsy
    
      // Split the eligibleActionString by comma and trim each action value
      const actionsList = eligibleActionString.split(',').map((action) => action.trim());
      return actionsList;
    };

    const actionsList = extractActionsToList(taskInfo.eligibleAction);
    console.log("ActionLista",actionsList);
    console.log(alltasks)
   
    console.log(allActionList.length)
   
    const handleChange = (actionName, prevList) => {
      const existingIndex = allActionList.findIndex((item) => item.taskId === taskInfo.taskId && item.action === actionName);
      console.log("all", allActionList, existingIndex, actionName)
      const newTaskAction = { taskId: taskInfo.taskId, action: actionName, nextTaskId: prevList };
      console.log("newadd",newTaskAction )
      if (existingIndex !== -1) {
        // Replace the existing item with the new one
        const updatedList = [...allActionList];
        updatedList[existingIndex] = newTaskAction;
        setAllActionList(updatedList);
        console.log("updated")
      } else {
        // Add the new item to the list
        setAllActionList((prevList) => [...prevList, newTaskAction]);
        console.log("created")
      }
    };
  
    useEffect(() => {
      if (allActionList.length === actionsList.length) {
        handleDropdownChange(allActionList);
      }
    }, [allActionList]);
  
    

    return (
        <Card sx={{ maxWidth: "70%", ml: "15%", mr: "15%", mt: "20px", textAlign: "left", backgroundColor: "#edf4fb" }}>
          <CardContent>
            <Typography sx={{ fontWeight: "bold" }} variant='h5' color="text.primary" gutterBottom>
              {taskInfo.taskName}
    
            </Typography>
            <Divider sx={{ opacity: 1 }} />
            <Typography sx={{ mb: 1.2, mt: 1.5 }} variant="body1">
              {taskInfo.taskDescription}
            </Typography>
            {
              actionsList.map((item) => {
                {console.log("item", item)}
                return <ActionSelector currentTask={taskInfo} action={item} listTasks={alltasks} handleChange={handleChange}/>
              })
            }
          </CardContent>
        </Card>
      );
}

export default LinkCard;