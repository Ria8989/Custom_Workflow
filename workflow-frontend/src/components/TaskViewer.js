import React, { useEffect, useState } from 'react';
import WorkflowCard from "./WorkflowCard";
import {Typography, Button} from '@mui/material/';
import TaskCard from './TaskCard';

const TaskViewer = ({userInfo, workflowInfo, handleWorkflowSelection}) => {
    const [tasks, setTasks] = useState([]);
    const [worflowInstance, setWorkflowInstance] = useState();
    const [formData, setFormData] = useState({
        workflowId: workflowInfo.workflowId,
        workflowInstantiatorEmail: userInfo.userEmail,
      });

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
                console.log(tasks)
            })        
        })
        .catch((error)=>{
            console.log(error)
        })
        },[]);

    const handleOnCLick = (event) => {
        event.preventDefault();
        console.log(workflowInfo);      
        console.log(formData); // Handle form submission data here
        fetch('http://localhost:8085/api/v1/workflow_instance', {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(formData)
        })
        .then((response)=>{
            console.log(response)
            if(response.status===200)
            {
                    response.json().then(resData => {
                    console.log("resData ");
                    const statusMsg = "The workflow has started successfully. Please see the status in \"My Workflows\" section."
                    // Swal.fire(
                    //     'Registered Successfully',
                    //     statusMsg,
                    //     'success'
                    // );
                    console.log(statusMsg)
                    setWorkflowInstance(resData)
                    console.log(resData)
                    handleWorkflowSelection('MyWorkflows', worflowInstance);  
                })
            }
            else{
                // Swal.fire(
                //     'Registeration Failed',
                //     'Capacity of selected department is full!',
                //     'error'
                // );
                console.log("Failed")
            }
            
        })
        .catch((error)=>{
            console.log(error)
        })
    };

    const hasMatchingRole = userInfo.roles.some(userRole =>
        workflowInfo.workflowroles.some(workflowRole => userRole.roleId === workflowRole.roleId)
      );
    
    return (
        <div>
            <Typography variant="h5" sx={{fontWeight: "bold"}} gutterBottom>
                {workflowInfo.workflowName}
            </Typography>
            {
                tasks
                .filter(task => task.taskName !== 'END')
                .sort((a,b) => a.taskId - b.taskId)
                .map((task => (<TaskCard key={task.taskId} taskInfo={task} />)))
            }
            {
            
            (workflowInfo.workflowroles.length === 0 || hasMatchingRole) &&
            (<Button type="button" variant="contained" color="primary" sx={{ml:"10px", mr:"10px", mt:"20px", width:"15%"}} 
            onClick={handleOnCLick}
            >
                Start Workflow
            </Button>)

            }
        </div>
    );
}

//Exporting the component to be used in other React components
export default TaskViewer;