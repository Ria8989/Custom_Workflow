import React, { useState } from 'react';
import { FormControl, TextField, Select, MenuItem, FormGroup, Button, Typography, InputLabel, FormLabel, Table, TableRow, TableCell, tableCellClasses, TableBody } from '@mui/material';
import TaskCard from './TaskCard';
import WorkflowCard from './WorkflowCard';
import WorkflowInstanceCard from './WorkflowInstanceCard';

const UserTaskAction = ({userInfo, taskInfo, handleWorkflowSelection}) => {
    
  return (
    <form >
      <Typography variant="h5" sx={{fontWeight: "bold"}} gutterBottom>
        Task Action
      </Typography>
      <Typography variant="h6" sx={{mt:"20px"}} gutterBottom>
        Workflow Information
      </Typography>
      <WorkflowInstanceCard workflowInfo={taskInfo.taskInstance.task.workflow} />
      <Typography variant="h6" sx={{mt:"20px"}} gutterBottom>
        Task Information
      </Typography>
      <TaskCard key={taskInfo.taskInstance.task.taskId} taskInfo={taskInfo.taskInstance.task}/>
      <Table sx={{
        [`& .${tableCellClasses.root}`]: {
          borderBottom: "none"
        }, maxWidth: "70%", ml:"15%", mr:"15%"
      }}
      aria-label="a dense table"
      size="small"
      >
        <colgroup>
          <col width={"30%"} />
          <col width={"70%"} />
        </colgroup>
      <TableBody>
      <TableRow>
        <TableCell >
          <FormLabel sx={{fontSize:"1.23rem"}}>Task Initiator Name</FormLabel>
        </TableCell>
        <TableCell >
          <FormControl fullWidth margin='normal'>
            <TextField
              name="taskInitiator"
              value={taskInfo.taskInstance.workflowInstance.workflowInstantiator.userName}
              label="Task Initiator Name"
              sx={{backgroundColor:"#edf4fb"}}
              disabled
            />
          </FormControl>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell >
          <FormLabel sx={{fontSize:"1.23rem"}}>Task Initiator Email</FormLabel>
        </TableCell>
        <TableCell >
          <FormControl fullWidth margin='normal'>
            <TextField
              name="taskInitiator"
              value={taskInfo.taskInstance.workflowInstance.workflowInstantiator.userEmail}
              label="Task Initiator Email"
              sx={{backgroundColor:"#edf4fb"}}
              disabled
            />
          </FormControl>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <FormLabel sx={{fontSize:"1.23rem"}}>Task Action</FormLabel>
        </TableCell>
        <TableCell>
          <FormControl fullWidth margin="normal">
          <TextField
              name="actionStatus"
              value={taskInfo.actionStatus}
              sx={{backgroundColor:"#edf4fb"}}
              disabled
            />
          </FormControl>
        </TableCell>
      </TableRow>
      { taskInfo.taskInstance.task.enableUpload &&
      <TableRow>
          <TableCell>
            <FormLabel sx={{ fontSize: '1.23rem' }}>Uploaded Files</FormLabel>
          </TableCell>
          <TableCell>
            <FormControl fullWidth margin="normal">
            <TextField
              name="uploadFilePath"
              value={taskInfo.uploadFilePath}
              sx={{backgroundColor:"#edf4fb"}}
              disabled
            />
            </FormControl>
          </TableCell>
        </TableRow>
      }
      <TableRow>
        <TableCell >
          <FormLabel sx={{fontSize:"1.23rem"}}>Task Comment</FormLabel>
        </TableCell>
        <TableCell >
          <FormControl fullWidth margin='normal'>
            <TextField
              name="comment"
              value={taskInfo.comment}
              sx={{backgroundColor:"#edf4fb"}}
              disabled
            />
          </FormControl>
        </TableCell>
      </TableRow>
      </TableBody>
      </Table>
      <FormGroup row sx={{ alignItems:"center", justifyContent:"center" , mt:"20px", mb:"30px"}}>
        <Button type="button" variant="contained" color="primary" sx={{ml:"10px", mr:"10px", width:"15%"}}
        onClick={() => handleWorkflowSelection("MyTaskInbox", taskInfo)}>
          Go Back
        </Button>
      </FormGroup> 
      
    </form>
    
  );
};

export default UserTaskAction;
