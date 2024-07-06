import React, { useEffect, useState } from 'react';
import { FormControl, Select, MenuItem, Button, Typography, InputLabel, Table, TableBody, TableCell, TableRow, tableCellClasses } from '@mui/material/';
import LinkCard from './LinkCard';

const TaskLinker = ({userInfo, workflowInfo, handleWorkflowSelection}) => {
    const [tasks, setTasks] = useState([]);
    const [actionLink, setActionLink] = useState([])
    const [formData, setFormData] = useState({
        firstTask : '',
        workflowId : workflowInfo.workflowId,
        actionNextTask : []
    })

      const handleDropdownChange = (updatedActionList) => {
        
        updatedActionList.map((updatedTask) => {
            const existingIndex = actionLink.findIndex((item) => item.taskId === updatedTask.taskId && item.action === updatedTask.action);
            if (existingIndex !== -1) {
                // Replace the existing item with the new one
                const updatedList = [...actionLink];
                updatedList[existingIndex] = updatedTask;
                setActionLink(updatedList);
                console.log("main updated", updatedTask)
                console.log(actionLink)
            } else {
                // Add the new item to the list
                setActionLink((prevList) => [...prevList, updatedTask]);
                console.log("main created", updatedTask)
                console.log(actionLink)
            }
        })
        
      };
       

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
          }));
      };

    const handleOnClick = (event) =>{
        event.preventDefault();
        console.log(actionLink)
        console.log(formData); // Handle form submission data here
        fetch('http://localhost:8085/api/v1/task/link', {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(formData)
        })
        .then((response) => {
            console.log(response);
            if (response.status === 200) {
                return response.text(); // Handle text response
            } else {
                console.log("Failed");
                throw new Error("Failed to fetch data");
            }
        })
        .then((textResponse) => {
            console.log("Text response: ", textResponse);
            // Handle the text response here
            const statusMsg = "The workflow tasks linked successfully. Workflow creating completed.";
            console.log(statusMsg);
            handleWorkflowSelection("My Created Workflows", textResponse);
        })
        .catch((error) => {
            console.log(error);
        });
    }

    useEffect(()=>{
        const url = 'http://localhost:8085/api/v1/task/all_task?workflowId=' + workflowInfo.workflowId
        console.log(url)
        fetch(url, {
            method : "GET"
        })
        .then((response)=>{
            const data = response.json()
            .then(resData => {
                console.log(resData);
                setTasks(resData)
            })        
            
        })
        .catch((error)=>{
            console.log(error)
        })
    },[]);

    useEffect(() => {
        setFormData((prevData) => ({
            ...prevData,
            actionNextTask: actionLink,
          }));    
        console.log('Updated formData:', formData);
    }, [actionLink]); 

    return (
        <div>
            <form onSubmit={handleOnClick}>
            <Typography variant="h5" sx={{fontWeight: "bold"}} gutterBottom>
                {workflowInfo.workflowName} Linking
            </Typography>
            <Table sx={{
                [`& .${tableCellClasses.root}`]: {
                borderBottom: "none"
                }, maxWidth: "70%", ml:"15%", mr:"15%"
            }}
            aria-label="a dense table"
            size="small"
            >
                <colgroup>
                <col width={"30%"} />
                <col width={"70%"} />
                </colgroup>
            <TableBody>
            <TableRow>
                <TableCell>
                    <Typography required={true} variant="h6">Select First Workflow Task</Typography>
                </TableCell>
                <TableCell>
                    <FormControl fullWidth margin="normal">
                        <InputLabel>Select First Workflow Task</InputLabel>
                        <Select
                        name="firstTask"
                        value={formData.firstTask}
                        onChange={handleInputChange}
                        label="Select First Workflow Task"
                        required={true} // Set required conditionally
                        sx={{backgroundColor:"#edf4fb"}}
                        >
                        {
                            tasks
                            .filter((task) => task.taskName !== 'END')
                            .map((task) => (
                            <MenuItem key={task.taskId} value={task.taskName}>
                                {task.taskName}
                            </MenuItem>
                            ))
                        }
                        </Select>
                    </FormControl>
                </TableCell>
            </TableRow>
            </TableBody>
            </Table>
            {tasks.filter((task) => task.taskName !== 'END')
            .sort((a,b) => a.taskId - b.taskId)
            .map((task) => (
                
                <LinkCard
                    key={task.taskId}
                    taskInfo={task}
                    alltasks={tasks}
                    handleDropdownChange={handleDropdownChange}
                />
            ))}
            
            <Button type="submit" variant="contained" color="primary" sx={{ml:"10px", mr:"10px", mt:"20px", width:"15%"}}>
                Link Workflow Task
            </Button>
            </form>
        </div>
    );
}

//Exporting the component to be used in other React components
export default TaskLinker;