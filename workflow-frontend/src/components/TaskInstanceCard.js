import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Chip, Divider } from '@mui/material';
import { RollerShades } from '@mui/icons-material';


const TaskInstanceCard = ({taskInfo, allInfo}) => {

const formatDate = (dateString) => {
  const date = new Date(dateString);
  // Format the date and time as per your requirements
  const formattedDate = date.toLocaleDateString(); // Format date
  const formattedTime = date.toLocaleTimeString(); // Format time
  return `${formattedDate} ${formattedTime}`;
};

    console.log("all", allInfo)


  return (
    <Card sx={{maxWidth: "70%", ml:"15%", mr:"15%", mt:"20px", textAlign:"left", backgroundColor:"#edf4fb"  }}>
      <CardContent>
        <Typography sx={{ fontWeight: "bold"}} variant='h5' color="text.primary" gutterBottom>
          {taskInfo.taskName}
          {allInfo && (allInfo.taskUserStatus ==='COMPLETE' ? (
          <Chip label={`Action Status: ${allInfo.actionStatus}`} variant="filled" color='primary' sx={{ ml: "30px", mb: "5px" }} />) : (
            <Chip label={`Task Status: ${allInfo.taskUserStatus}`} variant="filled" color='primary' sx={{ ml: "30px", mb: "5px" }} />
          )) 
          }
        </Typography>
        <Divider sx={{ opacity: 1 }}/>
        <Typography sx={{ mb: 1.2, mt: 1.5 }} variant="body1">
        {taskInfo.taskDescription}
        </Typography>
        { allInfo && allInfo.actionStatus===null &&
          <Typography variant="body1">
            The User has not performed the task.
          </Typography>
        }
        {
          allInfo && 
          <div>
          <Typography variant="body1">
            Task Performed by: {allInfo.user.userName} 
          </Typography>
          <Typography variant="body1">
            Role: {allInfo.user.roles.map((role) => role.roleName).join(', ')}
          </Typography>
          <Typography variant="body1">
            Comment: {allInfo.comment===null || allInfo.comment===''? "None" : allInfo.comment}
          </Typography>
          <Typography variant="body1">
            Uploaded File: {allInfo.uploadFilePath===null || allInfo.uploadFilePath===''? "None" : allInfo.uploadFilePath}
          </Typography>
          <Typography variant="body1">
            Task Instance Creation Time: {allInfo.taskUserInstanceCreated===null || allInfo.taskUserInstanceCreated===''? "None" : formatDate(allInfo.taskUserInstanceCreated)}
          </Typography>
          <Typography variant="body1">
            Task Instance Last Update Time: {allInfo.taskUserInstanceLastUpdate===null || allInfo.taskUserInstanceLastUpdate===''? "None" : formatDate(allInfo.taskUserInstanceLastUpdate)}
          </Typography>

          </div>
        }
      </CardContent>
      {/* {
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
      } */}
    </Card>
  );
}

export default TaskInstanceCard;