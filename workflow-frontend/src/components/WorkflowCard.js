import React, { useState } from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Chip, Divider } from '@mui/material';



const WorkflowCard = ({workflowInfo, onAction, allInfo}) => {

  const handleActionClick = (workflow, extraData) => {
    console.log(typeof onAction);
    // Call the onAction function passed from the parent component
    if (typeof onAction === 'function') {
      console.log("Yes")
      console.log(workflow)
      onAction(workflow, extraData);
    }
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    // Format the date and time as per your requirements
    const formattedDate = date.toLocaleDateString(); // Format date
    const formattedTime = date.toLocaleTimeString(); // Format time
    return `${formattedDate} ${formattedTime}`;
  };

  return (
    <Card sx={{maxWidth: "70%", ml:"15%", mr:"15%", mt:"20px", textAlign:"left", backgroundColor:"#edf4fb"  }}>
      <CardContent>
        <Typography sx={{ fontWeight: "bold" }} variant='h5' color="text.primary" gutterBottom>
          {workflowInfo.workflowName}
          {allInfo && allInfo.workflowStatus && <Chip label={`Status: ${allInfo.workflowStatus}`} variant="filled" color='primary' sx={{ ml: "30px", mb: "5px" }} />}
        </Typography>
        <Divider sx={{ opacity: 1 }}/>
        <Typography sx={{ mb: 1.2, mt: 1.5 }} variant="body1">
          {workflowInfo.workflowDescription}
        </Typography>
        {
          allInfo && (
            <div>
            <Typography sx={{ mb: 1.2, mt: 1.5 }} variant="body1">
              Workflow Instance Creation Time : {formatDate(allInfo.workflowInstanceCreated)}
            </Typography>
            {/* <Typography sx={{ mb: 1.2, mt: 1.5 }} variant="body1">
              Workflow Instance Last Update Time : {formatDate(allInfo.workflowInstanceLastUpdate)}
            </Typography> */}
            </div>
          ) 
        }
      </CardContent>
      <CardActions sx={{ ml: 1}}>
        <Button type="button" variant="contained" color="primary" size="small" 
        onClick={allInfo ? () => handleActionClick("Task Instance Viewer", allInfo) : () => handleActionClick("Task Viewer", workflowInfo)}>View Workflow</Button>
      </CardActions>
    </Card>
  );
}

export default WorkflowCard