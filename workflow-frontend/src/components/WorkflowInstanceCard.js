import React, { useState } from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Divider } from '@mui/material';



const WorkflowInstanceCard = ({workflowInfo}) => {

  return (
    <Card sx={{maxWidth: "70%", ml:"15%", mr:"15%", mt:"20px", textAlign:"left", backgroundColor:"#edf4fb"  }}>
      <CardContent>
        <Typography sx={{ fontWeight: "bold" }} variant='h5' color="text.primary" gutterBottom>
          {workflowInfo.workflowName}
        </Typography>
        <Divider sx={{ opacity: 1 }}/>
        <Typography sx={{ mb: 1.2, mt: 1.5 }} variant="body1">
          {workflowInfo.workflowDescription}
        </Typography>
      </CardContent>
    </Card>
  );
}

export default WorkflowInstanceCard;