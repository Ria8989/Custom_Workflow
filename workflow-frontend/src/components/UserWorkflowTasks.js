import React, { useEffect, useState } from 'react';
import WorkflowCard from "./WorkflowCard";
import {Typography, Button, FormGroup} from '@mui/material/';
import TaskCard from './TaskCard';
import TaskInstanceCard from './TaskInstanceCard';

const UserWorkflowTasks = ({userInfo, workflowInfo, handleWorkflowSelection}) => {
    const [tasks, setTasks] = useState([]);
    
    useEffect(()=>{
        const url = 'http://localhost:8085/api/v1/workflow_instance/task_user_instance?workflowInstanceId=' + workflowInfo.workflowInstanceId
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

    return (
        <div>
            <Typography variant="h5" sx={{fontWeight: "bold"}} gutterBottom>
                {workflowInfo.workflow.workflowName}
            </Typography>
            {
                tasks.filter(item => item.taskInstance.task.taskName !== 'END' && (item.taskInstance.task.allUser===false ? (item.actionStatus!==null) : (true)))
                .sort((a, b) => a.taskUserInstanceId - b.taskUserInstanceId)
                .map((item => (
                <TaskInstanceCard 
                key={item.taskInstanceId} 
                taskInfo={item.taskInstance.task} 
                allInfo={item}/>)))
            }
            <FormGroup row sx={{ alignItems:"center", justifyContent:"center" , mt:"20px", mb:"30px"}}>
                <Button type="button" variant="contained" color="primary" sx={{ml:"10px", mr:"10px", width:"15%"}}
                onClick={() => handleWorkflowSelection("MyWorkflows", tasks)}>
                Go Back
                </Button>
            </FormGroup> 
        </div>
    );
}

//Exporting the component to be used in other React components
export default UserWorkflowTasks;