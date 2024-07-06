import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Chip, Divider } from '@mui/material';


const WorkflowCardInstance = ({taskInstInfo}) => {
  return (
    <Card sx={{maxWidth: "70%", ml:"15%", mr:"15%", mt:"20px", textAlign:"left", backgroundColor:"#edf4fb"  }}>
      <CardContent>
        <Typography sx={{ fontWeight: "bold"}} variant='h5' color="text.primary" gutterBottom>
          Workflow one
          {/* {taskInstInfo.status && <Chip label={`Status: ${taskInstInfo.status}`} variant="filled" color='primary' sx={{ ml: "30px", mb: "5px" }} />} */}
          {/* <Chip label="Status: Complete" variant="filled" color='primary' sx={{ml:"30px", mb:"5px"}} /> */}
        </Typography>
        <Divider sx={{ opacity: 1 }}/>
        <Typography sx={{ mb: 1.2, mt: 1.5 }} variant="body1">
        Workflow Description
        </Typography>
        
      </CardContent>
    </Card>
  );
}

export default WorkflowCardInstance;