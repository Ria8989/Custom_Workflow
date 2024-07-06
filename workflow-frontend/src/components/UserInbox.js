import React, { useEffect, useState } from 'react';
import WorkflowCard from "./WorkflowCard";
import {Typography, Button, Divider} from '@mui/material/';
import TaskCard from './TaskCard';

const UserInbox = ({userInfo, handleWorkflowSelection}) => {
    const [taskInstances, setTaskInstances] = useState([]);
    console.log("inbox", typeof handleWorkflowSelection)
    useEffect(()=>{
        const url = 'http://localhost:8085/api/v1/task_user_instance?email=' + userInfo.userEmail
        console.log(url)
        fetch(url, {
            method : "GET"
        })
        .then((response)=>{
            const data = response.json()
            .then(resData => {
                console.log(resData);
                setTaskInstances(resData)
                console.log(taskInstances)
            })        
        })
        .catch((error)=>{
            console.log(error)
        })
        },[]);

    return (
        <div>
            <Typography variant="h5" sx={{fontWeight: "bold"}} gutterBottom>
                Pending Tasks
            </Typography>
            {
                taskInstances.filter(item => item.taskUserStatus === 'PENDING')
                .sort((a,b) => new Date(b.taskUserInstanceCreated) - new Date(a.taskUserInstanceCreated))
                .map(item => (
                    <TaskCard 
                    key={item.taskUserInstanceId} 
                    taskInfo={item.taskInstance.task} 
                    allInfo={item} 
                    onAction={handleWorkflowSelection}/>
                ))
            }
            <Divider sx={{mt:"30px", opacity:"1"}}/>
            <Typography variant="h5" sx={{fontWeight: "bold", mt:"30px"}} gutterBottom>
                Completed Tasks
            </Typography>
            {
                taskInstances.filter(item => item.taskUserStatus === 'COMPLETE')
                .sort((a,b) => new Date(b.taskUserInstanceLastUpdate) - new Date(a.taskUserInstanceLastUpdate))
                .map(item => (
                    <TaskCard 
                    key={item.taskUserInstanceId} 
                    taskInfo={item.taskInstance.task} 
                    allInfo={item}
                    onAction={handleWorkflowSelection}/>
                ))
            }
            
        </div>
    );
}

//Exporting the component to be used in other React components
export default UserInbox;