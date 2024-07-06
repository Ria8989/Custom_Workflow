import React, { useEffect, useState } from 'react';
import { FormControl, TextField, Select, MenuItem, Radio, RadioGroup, FormControlLabel, FormGroup, Button, Typography, InputLabel, FormLabel, Table, TableRow, TableCell, tableCellClasses, TableBody, Box } from '@mui/material';
import Drawer from '@mui/material/Drawer';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import AssignmentTurnedInIcon from '@mui/icons-material/AssignmentTurnedIn';

const TaskCreator = ({userInfo, workflowInfo, handleWorkflowSelection}) => {
  const [allUsers, setAllUsers] = useState([])
  const [allRoles, setAllRoles] = useState([])
  const [actionList, setActionList] = useState('')
  const [saveDone, setSaveDone] = useState(false)
  const [allTask, setAllTask] = useState([])
  const [formData, setFormData] = useState({
    workflowId: workflowInfo.workflowId,
    taskName: '',
    taskDescription: '',
    taskActions: [],
    enableUpload: 'true',
    uploadType: "MANDATORY",
    taskUserType: "INITIATOR",
    taskUsers: [],
    taskRoles: [],
    allUser: true,
  });


  const resetForm = () => {
    setFormData({
      workflowId: workflowInfo.workflowId,
      taskName: '',
      taskDescription: '',
      taskActions: [],
      enableUpload: 'true',
      uploadType: "MANDATORY",
      taskUserType: "INITIATOR",
      taskUsers: [],
      taskRoles: [],
      allUser: true,
    });
    setSaveDone(false)
    setActionList('')
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    if (name === 'taskUserType') {
      setFormData((prevData) => ({
        ...prevData,
        [name]: value,
        taskUsers: [], // Reinitialize taskUser only if taskUserType changes
        taskRoles: []
      }));
    } 
    else if (name === 'enableUpload') {
      if(value === 'false'){
        setFormData((prevData) => ({
          ...prevData,
          [name]: value,
          uploadType: null, // Reset uploadType to empty string if enableUpload is false
        }));
      }
      else{
        setFormData((prevData) => ({
          ...prevData,
          [name]: value,
        }));
      }
    }else if (name === 'actionList'){
      setActionList(value)
    } 
    else {
      setFormData((prevData) => ({
        ...prevData,
        [name]: value,
      }));
    }
  };

  useEffect(() => {
      const newValue = actionList.split(',').map(item => item.trim());
      
      setFormData((prevData) => ({
        ...prevData,
        taskActions: newValue,
      }));
    },[actionList]
  )

  // useEffect(() => {
  //   const url = 'http://localhost:8085/api/v1/task/all_task?workflowId=' + workflowInfo.workflowId
  //   fetch(url, {
  //       method : "GET"
  //   })
  //   .then(response => response.json())
  //   .then(resData => {
  //       console.log(resData);
  //       setAllTask(resData)
  //   })        
  //   .catch((error)=>{
  //       console.log(error)
  //   });
  //   },[saveDone]
  // )
  
  const getTaskList = () => {
    const url = 'http://localhost:8085/api/v1/task/all_task?workflowId=' + workflowInfo.workflowId
    fetch(url, {
        method : "GET"
    })
    .then(response => response.json())
    .then(resData => {
        console.log(resData);
        setAllTask(resData)
        console.log("all", resData)
    })        
    .catch((error)=>{
        console.log(error)
    });
  };


  const handleMultiSelectChange = (event) => {
    const { name, value } = event.target;
    const selectedValues = Array.isArray(value) ? value : [value]; // Ensure value is always an array
  
    setFormData((prevData) => ({
      ...prevData,
      [name]: selectedValues,
    }));
  };

  const getInputLabelTaskUser = () => {
    if (formData.taskUserType === 'INITIATOR') {
      return 'The initiator will perform this task';
    }
    return 'Select one or more users/roles';
  };

  const getInputLabelUpload = () => {
    if (formData.enableUpload === 'true') {
      return '(Enabled)';
    }
    return '(Disabled)';
  };

  const handleActionClick = (workflow, extraData) => {
    console.log(typeof handleWorkflowSelection);
    if (typeof handleWorkflowSelection === 'function') {
      console.log("Yes")
      console.log(workflow)
      resetForm()
      handleWorkflowSelection(workflow, extraData);
    }
  };

  const handleEndWorkflowClick = (workflow, extraData) => {
    console.log('Button clicked!');
    handleWorkflowSelection(workflow, extraData);
  };
  

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(formData); // Handle form submission data here
    fetch('http://localhost:8085/api/v1/task', {
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
                const statusMsg = "The task has been added successfully. Please create the next task or end the workflow."
                // Swal.fire(
                //     'Registered Successfully',
                //     statusMsg,
                //     'success'
                // );
                console.log(statusMsg)
                console.log("Task", resData)
                setSaveDone(true)
                getTaskList()
                //handleActionClick("Task Creator", resData);
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
    fetch('http://localhost:8085/api/v1/role/all', {
        method : "GET"
    })
    .then(response => response.json())
    .then(resData => {
        console.log(resData);
        setAllRoles(resData)
    })        
    .catch((error)=>{
        console.log(error)
    });

    fetch('http://localhost:8085/api/v1/user/all', {
        method : "GET"
    })
    .then(response => response.json())
    .then(resData => {
        console.log(resData);
        setAllUsers(resData)
    })        
    .catch((error)=>{
        console.log(error)
    });

    },[]
  );

  const drawerWidth = 280

  return (
    <div style={{ width: '100%' }}>
      <Box sx={{
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'flex-start',
          p: 1,
          m: 1,
        }} >

    <Box sx={{width:"80%"}}>
        <form onSubmit={handleSubmit}> 
      <Typography variant="h5" sx={{fontWeight: "bold", alignItems:"center", justifyContent:"center" }} gutterBottom>
        Create New Task for {workflowInfo.workflowName}
      </Typography>
      <Table sx={{
        [`& .${tableCellClasses.root}`]: {
          borderBottom: "none"
        }, maxWidth: "90%", ml:"5%", mr:"5%"
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
          <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Task</FormLabel>
        </TableCell>
        <TableCell >
          <FormControl fullWidth margin='normal'>
            <TextField
              name="taskName"
              value={formData.taskName}
              onChange={handleInputChange}
              label="Task Name"
              required={true}
              sx={{backgroundColor:"#edf4fb", '& .MuiFormLabel-asterisk': { display: 'none' }}}
            />
          </FormControl>
        </TableCell>
      </TableRow>
      <TableRow>      
        <TableCell>
          <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Task Description</FormLabel>
        </TableCell>
        <TableCell>
          <FormControl fullWidth margin="normal">
          <TextField
            name="taskDescription"
            value={formData.taskDescription}
            onChange={handleInputChange}
            label="Task Description"
            multiline
            rows={2}
            required={true}
            sx={{backgroundColor:"#edf4fb", '& .MuiFormLabel-asterisk': { display: 'none' }}}
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
            <TextField
              name="actionList"
              value={actionList} // Join array values with comma
              onChange={handleInputChange}
              label="Add one or more action (comma separated)"
              required={true}
              sx={{backgroundColor:"#edf4fb", '& .MuiFormLabel-asterisk': { display: 'none' }}}
            />
          </FormControl>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Enable Upload</FormLabel>
        </TableCell>
        <TableCell>
          <FormControl row component="fieldset" margin="normal">
            <RadioGroup
              row
              name="enableUpload"
              value={formData.enableUpload}
              onChange={handleInputChange}
              required={true}
              defaultValue='true'
            >
              <FormControlLabel value='true' control={<Radio />} label="Yes" />
              <FormControlLabel value='false' control={<Radio />} label="No" />
            </RadioGroup>
          </FormControl>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <FormLabel required={formData.enableUpload === 'true'} sx={{fontSize:"1.23rem"}}>Upload Type {getInputLabelUpload()}</FormLabel>
        </TableCell>
        <TableCell>
          <FormControl row component="fieldset" margin="normal">
            <RadioGroup
              row
              name="uploadType"
              value={formData.uploadType}
              onChange={handleInputChange}
              defaultValue="MANDATORY"
            > 
              <FormControlLabel value="MANDATORY" control={<Radio />} label="Mandatory"
              required={!formData.enableUpload} 
              disabled={formData.enableUpload === 'false'}/>
              <FormControlLabel value="OPTIONAL" control={<Radio />} label="Optional" 
              required={!formData.enableUpload}
              disabled={formData.enableUpload === 'false'}/> 
            </RadioGroup>
          </FormControl>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Task User Type</FormLabel>
        </TableCell>
        <TableCell>
          <FormControl component="fieldset" margin="normal">
            <RadioGroup
              row
              name="taskUserType"
              value={formData.taskUserType}
              onChange={handleInputChange}
              required={true}
              defaultValue="INITIATOR"
            >
              <FormControlLabel value="USER" control={<Radio />} label="User" />
              <FormControlLabel value="ROLE" control={<Radio />} label="Role" />
              <FormControlLabel value="INITIATOR" control={<Radio />} label="Workflow Initiator" />
            </RadioGroup>
          </FormControl>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <FormLabel required={formData.taskUserType !== 'INITIATOR'} sx={{fontSize:"1.23rem"}}>Task User/Role</FormLabel>
        </TableCell>
        <TableCell>
          <FormControl fullWidth margin="normal">
          <InputLabel>{getInputLabelTaskUser()}</InputLabel>
            <Select
              name={formData.taskUserType==='USER'?"taskUsers":"taskRoles"}
              value={formData.taskUserType==='USER'?formData.taskUsers:formData.taskRoles}
              //value={formData.taskUsers}
              onChange={handleMultiSelectChange}
              multiple
              label={getInputLabelTaskUser()}
              required={formData.taskUserType !== 'INITIATOR'} // Set required conditionally
              disabled={formData.taskUserType === 'INITIATOR'} // Set disabled conditionally
              sx={{backgroundColor:"#edf4fb"}}
              MenuProps={{ disableScrollLock: true }}
            >
              {formData.taskUserType === 'USER' ? (
                allUsers.map((user) => (
                  <MenuItem key={user.userId} value={user.userEmail}>
                    {user.userName}
                  </MenuItem>
                ))
              ) : (
                allRoles.map((role) => (
                  <MenuItem key={role.roleId} value={role.roleName}>
                    {role.roleName}
                  </MenuItem>
                ))
              )}
            </Select>
          </FormControl>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Task Type</FormLabel>
        </TableCell>
        <TableCell>
          <FormControl component="fieldset" margin="normal">
            <RadioGroup
              row
              name="allUser"
              value={formData.allUser}
              onChange={handleInputChange}
              required={true}
              defaultValue={true}
            >
              <FormControlLabel value="false" control={<Radio />} label="Any" />
              <FormControlLabel value="true" control={<Radio />} label="All" />
            </RadioGroup>
          </FormControl>
        </TableCell>
      </TableRow>
      </TableBody>
      </Table>
      <FormGroup row sx={{ alignItems:"center", justifyContent:"center" , mt:"20px", mb:"30px"}}>
        <Button type="submit" variant="contained" color="primary" sx={{ml:"10px", mr:"10px", width:"25%"}}>
          Save
        </Button>
        {/* It should be "taskInfo" */}
        <Button 
          type="button" 
          variant="contained" 
          color="primary" 
          sx={{ml:"10px", mr:"10px", width:"25%"}}
          disabled={!saveDone} 
          onClick={() => handleActionClick("Task Creator", workflowInfo)}> 
          Create Next Task
        </Button>
        <Button 
          type="button" 
          variant="contained" 
          color="primary" 
          sx={{ml:"10px", mr:"10px", width:"25%"}}
          disabled={!saveDone} 
          onClick={() => handleEndWorkflowClick("Task Linker", workflowInfo)}>
          End and Link Workflow
        </Button>
      </FormGroup>      
    </form>
        
      </Box>

          <Box sx={{width:"20%"}}>
      <Drawer
        variant="permanent"
        anchor="right" // Set anchor to right side
        sx={{
            // width: drawerWidth,
            flexShrink: 0,
            [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
        }}
        >
        <Toolbar />
        <Box sx={{ overflow: 'auto', mt: "20px" }}>
        <Typography variant='h6'>Workflow Tasks</Typography>
        <Divider sx={{opacity:"1", mt:"20px", mb:"20px"}}/>
            <List>
            {
            
            allTask.length >0 ? (
            allTask
            .filter((item) => item.taskName !== 'END')
            .sort((a,b) => a.taskId - b.taskId)
            .map((item) => (
                <ListItem
                sx={{ mb: "20px" }}
                key={item.taskId}
                disablePadding
                onClick={(event) => event.preventDefault()} // Prevent click action
                >
                <ListItemButton sx={{backgroundColor:"#edf4fb"}}>
                    <ListItemIcon><AssignmentTurnedInIcon /></ListItemIcon>
                    <ListItemText primary={item.taskName} />
                </ListItemButton>
                </ListItem>
            ))) : (
              <div>
                <Typography variant='h7'>No task added to workflow</Typography>
                  <br/>
                <Typography variant='h7'>Please create first task</Typography>
              </div>
            )
          
          }
            </List>
        </Box>
      </Drawer>
      </Box>
      
    </Box>
    </div>
    // <form onSubmit={handleSubmit}> 
    //   <Typography variant="h5" sx={{fontWeight: "bold"}} gutterBottom>
    //     Create New Task for {workflowInfo.workflowName}
    //   </Typography>
    //   <Table sx={{
    //     [`& .${tableCellClasses.root}`]: {
    //       borderBottom: "none"
    //     }, maxWidth: "70%", ml:"15%", mr:"15%"
    //   }}
    //   aria-label="a dense table"
    //   size="small"
    //   >
    //     <colgroup>
    //       <col width={"30%"} />
    //       <col width={"70%"} />
    //     </colgroup>
    //   <TableBody>
    //   <TableRow>
    //     <TableCell >
    //       <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Task</FormLabel>
    //     </TableCell>
    //     <TableCell >
    //       <FormControl fullWidth margin='normal'>
    //         <TextField
    //           name="taskName"
    //           value={formData.taskName}
    //           onChange={handleInputChange}
    //           label="Task Name"
    //           required={true}
    //           sx={{backgroundColor:"#edf4fb", '& .MuiFormLabel-asterisk': { display: 'none' }}}
    //         />
    //       </FormControl>
    //     </TableCell>
    //   </TableRow>
    //   <TableRow>      
    //     <TableCell>
    //       <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Task Description</FormLabel>
    //     </TableCell>
    //     <TableCell>
    //       <FormControl fullWidth margin="normal">
    //       <TextField
    //         name="taskDescription"
    //         value={formData.taskDescription}
    //         onChange={handleInputChange}
    //         label="Task Description"
    //         multiline
    //         rows={2}
    //         required={true}
    //         sx={{backgroundColor:"#edf4fb", '& .MuiFormLabel-asterisk': { display: 'none' }}}
    //       />
    //       </FormControl>
    //     </TableCell>
    //   </TableRow>
    //   <TableRow>
    //     <TableCell>
    //       <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Task Action</FormLabel>
    //     </TableCell>
    //     <TableCell>
    //       <FormControl fullWidth margin="normal">
    //         <TextField
    //           name="actionList"
    //           value={actionList} // Join array values with comma
    //           onChange={handleInputChange}
    //           label="Add one or more action (comma separated)"
    //           required={true}
    //           sx={{backgroundColor:"#edf4fb", '& .MuiFormLabel-asterisk': { display: 'none' }}}
    //         />
    //       </FormControl>
    //     </TableCell>
    //   </TableRow>
    //   <TableRow>
    //     <TableCell>
    //       <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Enable Upload</FormLabel>
    //     </TableCell>
    //     <TableCell>
    //       <FormControl row component="fieldset" margin="normal">
    //         <RadioGroup
    //           row
    //           name="enableUpload"
    //           value={formData.enableUpload}
    //           onChange={handleInputChange}
    //           required={true}
    //           defaultValue='true'
    //         >
    //           <FormControlLabel value='true' control={<Radio />} label="Yes" />
    //           <FormControlLabel value='false' control={<Radio />} label="No" />
    //         </RadioGroup>
    //       </FormControl>
    //     </TableCell>
    //   </TableRow>
    //   <TableRow>
    //     <TableCell>
    //       <FormLabel required={formData.enableUpload === 'true'} sx={{fontSize:"1.23rem"}}>Upload Type {getInputLabelUpload()}</FormLabel>
    //     </TableCell>
    //     <TableCell>
    //       <FormControl row component="fieldset" margin="normal">
    //         <RadioGroup
    //           row
    //           name="uploadType"
    //           value={formData.uploadType}
    //           onChange={handleInputChange}
    //           defaultValue="MANDATORY"
    //         > 
    //           <FormControlLabel value="MANDATORY" control={<Radio />} label="Mandatory"
    //           required={!formData.enableUpload} 
    //           disabled={formData.enableUpload === 'false'}/>
    //           <FormControlLabel value="OPTIONAL" control={<Radio />} label="Optional" 
    //           required={!formData.enableUpload}
    //           disabled={formData.enableUpload === 'false'}/> 
    //         </RadioGroup>
    //       </FormControl>
    //     </TableCell>
    //   </TableRow>
    //   <TableRow>
    //     <TableCell>
    //       <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Task User Type</FormLabel>
    //     </TableCell>
    //     <TableCell>
    //       <FormControl component="fieldset" margin="normal">
    //         <RadioGroup
    //           row
    //           name="taskUserType"
    //           value={formData.taskUserType}
    //           onChange={handleInputChange}
    //           required={true}
    //           defaultValue="INITIATOR"
    //         >
    //           <FormControlLabel value="USER" control={<Radio />} label="User" />
    //           <FormControlLabel value="ROLE" control={<Radio />} label="Role" />
    //           <FormControlLabel value="INITIATOR" control={<Radio />} label="Workflow Initiator" />
    //         </RadioGroup>
    //       </FormControl>
    //     </TableCell>
    //   </TableRow>
    //   <TableRow>
    //     <TableCell>
    //       <FormLabel required={formData.taskUserType !== 'INITIATOR'} sx={{fontSize:"1.23rem"}}>Task User/Role</FormLabel>
    //     </TableCell>
    //     <TableCell>
    //       <FormControl fullWidth margin="normal">
    //       <InputLabel>{getInputLabelTaskUser()}</InputLabel>
    //         <Select
    //           name={formData.taskUserType==='USER'?"taskUsers":"taskRoles"}
    //           value={formData.taskUserType==='USER'?formData.taskUsers:formData.taskRoles}
    //           //value={formData.taskUsers}
    //           onChange={handleMultiSelectChange}
    //           multiple
    //           label={getInputLabelTaskUser()}
    //           required={formData.taskUserType !== 'INITIATOR'} // Set required conditionally
    //           disabled={formData.taskUserType === 'INITIATOR'} // Set disabled conditionally
    //           sx={{backgroundColor:"#edf4fb"}}
    //         >
    //           {formData.taskUserType === 'USER' ? (
    //             allUsers.map((user) => (
    //               <MenuItem key={user.userId} value={user.userEmail}>
    //                 {user.userName}
    //               </MenuItem>
    //             ))
    //           ) : (
    //             allRoles.map((role) => (
    //               <MenuItem key={role.roleId} value={role.roleName}>
    //                 {role.roleName}
    //               </MenuItem>
    //             ))
    //           )}
    //         </Select>
    //       </FormControl>
    //     </TableCell>
    //   </TableRow>
    //   <TableRow>
    //     <TableCell>
    //       <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Task Type</FormLabel>
    //     </TableCell>
    //     <TableCell>
    //       <FormControl component="fieldset" margin="normal">
    //         <RadioGroup
    //           row
    //           name="allUser"
    //           value={formData.allUser}
    //           onChange={handleInputChange}
    //           required={true}
    //           defaultValue={true}
    //         >
    //           <FormControlLabel value="false" control={<Radio />} label="Any" />
    //           <FormControlLabel value="true" control={<Radio />} label="All" />
    //         </RadioGroup>
    //       </FormControl>
    //     </TableCell>
    //   </TableRow>
    //   </TableBody>
    //   </Table>
    //   <FormGroup row sx={{ alignItems:"center", justifyContent:"center" , mt:"20px", mb:"30px"}}>
    //     <Button type="submit" variant="contained" color="primary" sx={{ml:"10px", mr:"10px", width:"15%"}}>
    //       Save
    //     </Button>
    //     {/* It should be "taskInfo" */}
    //     <Button 
    //       type="button" 
    //       variant="contained" 
    //       color="primary" 
    //       sx={{ml:"10px", mr:"10px", width:"15%"}}
    //       disabled={!saveDone} 
    //       onClick={() => handleActionClick("Task Creator", workflowInfo)}> 
    //       Create Next Task
    //     </Button>
    //     <Button 
    //       type="button" 
    //       variant="contained" 
    //       color="primary" 
    //       sx={{ml:"10px", mr:"10px", width:"15%"}}
    //       disabled={!saveDone} 
    //       onClick={() => handleEndWorkflowClick("Task Linker", workflowInfo)}>
    //       End and Link Workflow
    //     </Button>
    //   </FormGroup>      
    // </form>

  );
};

export default TaskCreator;
