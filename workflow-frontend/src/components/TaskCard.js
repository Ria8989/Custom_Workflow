import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Chip, Divider } from '@mui/material';


const TaskCard = ({taskInfo, allInfo, onAction}) => {
  
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
  
  

//   const formatDate = (dateString) => {
//     const date = new Date(dateString);
//     const options = {
//         year: 'numeric',
//         month: 'short',
//         day: 'numeric',
//         hour: '2-digit',
//         minute: '2-digit',
//         second: '2-digit',
//         hour12: false, // Use 24-hour format
//     };
//     const formatter = new Intl.DateTimeFormat('en-US', options);
//     return formatter.format(date);
// };

// const formatDate = (dateString) => {
//   const date = new Date(dateString);
//   const formattedDate = date.toLocaleDateString(); // Format date

//   // Extract and format time components
//   const hours = date.getHours().toString().padStart(2, '0');
//   const minutes = date.getMinutes().toString().padStart(2, '0');
//   const seconds = date.getSeconds().toString().padStart(2, '0');
//   const formattedTime = `${hours}:${minutes}:${seconds}`;

//   return `${formattedDate} ${formattedTime}`;
// };

  return (
    <Card sx={{maxWidth: "70%", ml:"15%", mr:"15%", mt:"20px", textAlign:"left", backgroundColor:"#edf4fb"  }}>
      <CardContent>
        <Typography sx={{ fontWeight: "bold"}} variant='h5' color="text.primary" gutterBottom>
          {taskInfo.taskName}
          {allInfo && allInfo.taskUserStatus==='COMPLETE' && allInfo.actionStatus && <Chip label={`Status: ${allInfo.actionStatus}`} variant="filled" color='primary' sx={{ ml: "30px", mb: "5px" }} />}
        </Typography>
        <Divider sx={{ opacity: 1 }}/>
        <Typography sx={{ mb: 1.2, mt: 1.5 }} variant="body1">
        {taskInfo.taskDescription}
        </Typography>
        {
          allInfo && (
            <div>
            <Typography sx={{ mb: 1.2, mt: 1.5 }} variant="body1">
               Workflow Instantiator: {allInfo.taskInstance.workflowInstance.workflowInstantiator.userName} ({allInfo.taskInstance.workflowInstance.workflowInstantiator.userEmail})
            </Typography>
            <Typography sx={{ mb: 1.2, mt: 1.5 }} variant="body1">
              Workflow Instance Creation Time : {formatDate(allInfo.taskInstance.workflowInstance.workflowInstanceCreated)}
            </Typography>
            <Typography sx={{ mb: 1.2, mt: 1.5 }} variant="body1">
              Task Instance Creation Time : {formatDate(allInfo.taskUserInstanceCreated)}
            </Typography>
            </div>
          ) 
        }
        {
          allInfo && allInfo.comment && <Typography variant="body1">
          Comment: {allInfo.comment}
          </Typography>
        }
      </CardContent>
      {
        allInfo && (allInfo.taskUserStatus==='PENDING'?
        (<CardActions sx={{ ml: 1}}>
          <Button type="button" variant="contained" color="primary" size="small" 
          onClick={() => handleActionClick("Task Action", allInfo)}>Perform Task</Button>
        </CardActions>) : (
          <CardActions sx={{ ml: 1}}>
            <Button type="button" variant="contained" color="primary" size="small" 
            onClick={() => handleActionClick("View Task Action", allInfo)}>View Task</Button>
          </CardActions>)
        )
      }
    </Card>
  );
}

export default TaskCard;