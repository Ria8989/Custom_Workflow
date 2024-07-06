import React, { useEffect, useState } from 'react';
import { Typography, Divider } from '@mui/material';
import WorkflowCard from "./WorkflowCard";


const UserWorkflowViewer = ({userInfo, handleWorkflowSelection}) => {
    const [workflows, setWorkflows] = useState([]);
    console.log(typeof handleWorkflowSelection)
    useEffect(()=>{
        const url = 'http://localhost:8085/api/v1/workflow_instance/user?email='+ userInfo.userEmail
        console.log(url)
        fetch(url, {
            method : "GET"
        })
        .then((response)=>{
            const data = response.json()
            .then(resData => {
                console.log(resData);
                setWorkflows(resData)
            })        
        })
        .catch((error)=>{
            console.log(error)
        })
        },[]);

    return (
        <div>
             <div>
            {/* <Typography variant="h5" sx={{fontWeight: "bold"}} gutterBottom>
                My Workflows
            </Typography> */}
            <Typography variant="h5" sx={{fontWeight: "bold"}} gutterBottom>
                Pending Workflows
            </Typography>
            {/* {
                workflows
                .sort((a,b) => new Date(b.workflowInstanceCreated) - new Date(a.workflowInstanceCreated))
                .map((workflow => (
                <WorkflowCard 
                key={workflow.workflowInstanceId}
                workflowInfo={workflow.workflow} 
                allInfo={workflow}
                onAction={handleWorkflowSelection}/>
            )))
            } */}

            {
                workflows
                .filter((workflow) => workflow.workflowStatus==='PENDING')
                .sort((a,b) => new Date(b.workflowInstanceCreated) - new Date(a.workflowInstanceCreated))
                .map((workflow => (
                <WorkflowCard 
                key={workflow.workflowInstanceId}
                workflowInfo={workflow.workflow} 
                allInfo={workflow}
                onAction={handleWorkflowSelection}/>
                )))
            }
            <Divider sx={{mt:"30px", opacity:"1"}}/>
            <Typography variant="h5" sx={{fontWeight: "bold", mt:"30px"}} gutterBottom>
                Completed Workflows
            </Typography>
            {
                workflows
                .filter((workflow) => workflow.workflowStatus==='COMPLETE')
                .sort((a,b) => new Date(b.workflowInstanceCreated) - new Date(a.workflowInstanceCreated))
                .map((workflow => (
                <WorkflowCard 
                key={workflow.workflowInstanceId}
                workflowInfo={workflow.workflow} 
                allInfo={workflow}
                onAction={handleWorkflowSelection}/>
                )))
            }

        </div>
        </div>
    );
}

//Exporting the component to be used in other React components
export default UserWorkflowViewer;