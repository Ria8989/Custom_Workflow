import React, { useEffect, useState } from 'react';
import WorkflowCard from "./WorkflowCard";
import Typography from '@mui/material/Typography';

const UserCreatedWorkflow = ({userInfo, handleWorkflowSelection}) => {
    const [workflows, setWorkflows] = useState([]);
    console.log(typeof handleWorkflowSelection)
    useEffect(()=>{
        const url = 'http://localhost:8085/api/v1/workflow/creator?email=' + userInfo.userEmail
        console.log(url)
        fetch(url, {
            method : "GET"
        })
        .then((response)=>{
            const data = response.json()
            .then(resData => {
                console.log(resData);
                setWorkflows(resData)
                console.log(workflows)
            })        
        })
        .catch((error)=>{
            console.log(error)
        })
        },[]);

    return (
        <div>
            <Typography variant="h5" sx={{fontWeight: "bold"}} gutterBottom>
                All Workflows
            </Typography>
            {
                workflows
                .sort((a,b) => b.workflowId - a.workflowId)
                .map((workflow => (
                <WorkflowCard 
                key={workflow.workflowId}
                workflowInfo={workflow} 
                onAction={handleWorkflowSelection}/>
            )))
            }
        </div>
    );
}

//Exporting the component to be used in other React components
export default UserCreatedWorkflow;