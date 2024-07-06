import React, { useState, useEffect } from 'react';
import { FormControl, TextField, Select, MenuItem, FormGroup, Button, Typography, InputLabel, FormLabel, Table, TableRow, TableCell, tableCellClasses, TableBody, Divider } from '@mui/material';
import TaskCard from './TaskCard';
import WorkflowInstanceCard from './WorkflowInstanceCard';
import TaskInstanceCard from './TaskInstanceCard';

const TaskAction = ({userInfo, taskInfo, handleWorkflowSelection}) => {
  console.log("info", taskInfo, taskInfo.taskInstance.task.workflow)
  const [fileSelected, setFileSelected] = useState(false);
  const [prevTask, setPrevTask] = useState(null)
  const [formData, setFormData] = useState({
    taskUserInstanceId : taskInfo.taskUserInstanceId,
    uploadFilePath: null,
    actionStatus: '',
    comment: '',
    taskUserStatus: "COMPLETE"
  });

  const [fileData, setFileData] = useState({
    fileUpload: '',
    file: null,
    savedFile: ''
  })

  const extractActionsToList = (eligibleActionString) => {
    if (!eligibleActionString) return []; // Return an empty array if eligibleActionString is falsy
  
    // Split the eligibleActionString by comma and trim each action value
    const actionsList = eligibleActionString.split(',').map((action) => action.trim());
    return actionsList;
  };

  const actionsList = extractActionsToList(taskInfo.taskInstance.task.eligibleAction);
  console.log(actionsList, "or", taskInfo.taskInstance.task.eligibleAction)
  
  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    console.log(file);
    setFileData((prevData) => ({
      ...prevData,
      file: file,
    }));
    setFileSelected(!!file);
  };

  const handleFileUpload = () => {
    console.log("File", fileData)
    if (fileData.file) {
      const fileName = `${taskInfo.taskInstance.taskInstanceId}_${taskInfo.taskInstance.workflowInstance.workflowInstantiator.userName}_${fileData.file.name}`;
      const filePath = `${fileName}`;
      console.log("Path", filePath)
      // Simulate file upload and saving process (since there's no backend)
      setTimeout(() => {
        setFileData((prevData) => ({
          ...prevData,
          fileUpload: true,
          savedFile: filePath, 
        }));
        setFormData((prevData) => ({
          ...prevData,
          uploadFilePath: filePath, 
        }));
        alert('File uploaded successfully.');
      }, 1000);
    } else {
      alert('Please select a file to upload.');
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(formData); // Handle form submission data here
    
    fetch('http://localhost:8085/api/v1/task_user_instance', {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(formData)
    })
    .then((response)=>{
        console.log(response)
        if(response.status===200)
        {
                response.json().then(resData => {
                console.log("resData ");
                const statusMsg = "Task completed successfully."
                // Swal.fire(
                //     'Registered Successfully',
                //     statusMsg,
                //     'success'
                // );
                console.log(statusMsg)
                console.log(resData)
                handleWorkflowSelection("MyTaskInbox", resData)
            })
        }
        else{
            // Swal.fire(
            //     'Registeration Failed',
            //     'Capacity of selected department is full!',
            //     'error'
            // );
            console.log("Failed")
        }
        
    })
    .catch((error)=>{
        console.log(error)
    })
  };

  useEffect(() => {
    //const url = 'http://localhost:8085/api/v1/task_user_instance/previous_task_user_instance?taskUserInstanceId=' +  taskInfo.taskUserInstanceId
    const url = 'http://localhost:8085/api/v1/workflow_instance/task_user_instance?workflowInstanceId=' + taskInfo.taskInstance.workflowInstance.workflowInstanceId
    fetch(url, {
          method : "GET"
      })
      .then(response => response.json())
      .then(resData => {
          console.log(resData);
          setPrevTask(resData)
          console.log("got res", prevTask)
      })        
      .catch((error)=>{
          console.log(error)
      });
    },[]
  );


  return (
    <form onSubmit={handleSubmit}>
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
              onChange={handleInputChange}
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
              onChange={handleInputChange}
              label="Task Initiator Email"
              sx={{backgroundColor:"#edf4fb"}}
              disabled
            />
          </FormControl>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Task Action</FormLabel>
        </TableCell>
        <TableCell>
          <FormControl fullWidth margin="normal">
          <InputLabel>Select Task Action</InputLabel>
            <Select
              name="actionStatus"
              value={formData.actionStatus}
              onChange={handleInputChange}
              required={true}
              label="Select Task Action"
              sx={{backgroundColor:"#edf4fb", '& .MuiFormLabel-asterisk': { display: 'none' }}}
            >
              {
                actionsList.map((action) => (
                  <MenuItem key={action} value={action}>{action}</MenuItem>
                ))
              }
            </Select>
          </FormControl>
        </TableCell>
      </TableRow>
      { taskInfo.taskInstance.task.enableUpload &&
      <TableRow>
          <TableCell>
            <FormLabel required={taskInfo.taskInstance.task.uploadType==='MANDATORY'} sx={{ fontSize: '1.23rem' }}>
              Upload Files
              </FormLabel>
          </TableCell>
          <TableCell>
            <FormControl fullWidth margin="normal">
              <input 
              type="file"
              name="file"
              required={taskInfo.taskInstance.task.taskType==='MANDATORY'} 
              onChange={handleFileChange} />
            </FormControl>
            <Button
              type="button"
              variant="contained"
              color="primary"
              onClick={handleFileUpload}
              disabled={!fileSelected} // Disable the button when no file is selected
            >
              Upload File
            </Button>
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
              value={formData.comment}
              onChange={handleInputChange}
              label="Task Comment"
              sx={{backgroundColor:"#edf4fb"}}
            />
          </FormControl>
        </TableCell>
      </TableRow>
      </TableBody>
      </Table>
      <FormGroup row sx={{ alignItems:"center", justifyContent:"center" , mt:"20px", mb:"30px"}}>
        <Button type="submit" variant="contained" color="primary" sx={{ml:"10px", mr:"10px", width:"15%"}}>
          Save
        </Button>
      </FormGroup>
      <Divider sx={{ opacity:"1" }}/>
      { prevTask!==null && prevTask.length > 0 && 
        (<div>
          <h6>
          <Typography variant="h6" sx={{mt:"20px"}} gutterBottom>
            Previous Task Information
          </Typography>
          </h6>
           {prevTask
           .filter(item => item.taskInstance.taskInstanceId !== taskInfo.taskInstance.taskInstanceId && item.taskInstance.task.taskName !== 'END' && (item.taskInstance.task.allUser===false ? (item.actionStatus!==null) : (true)))
           .sort((a,b) => new Date(b.taskUserInstanceLastUpdate) - new Date(a.taskUserInstanceLastUpdate))
           .map((item) => <TaskInstanceCard key={item.taskInstanceId} taskInfo={item.taskInstance.task} allInfo={item}/>)}
          
        </div>)
      } 
      {fileData.fileUpload && (
        <div style={{ marginTop:"40px" }}>
          <h5>Uploaded Files: {fileData.savedFile}</h5>
          
        </div>
      )}     
    </form>
    
  );
};

export default TaskAction;
