import React, { useState, useEffect } from 'react';
import { FormControl, TextField, Select, MenuItem, Radio, RadioGroup, FormControlLabel, FormGroup, Button, Typography, InputLabel, FormLabel, Table, TableRow, TableCell, tableCellClasses, TableBody } from '@mui/material';

const WorkflowCreator = ({userInfo, handleWorkflowSelection}) => {
  const [allRoles, setAllRoles] = useState([])
  const [formData, setFormData] = useState({
    workflowName: '',
    workflowDescription: '',
    userEmail: userInfo.userEmail,
    isAllRole: 'true',
    workflowRole: [],
  });

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
    },[]
  );

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    if (name === 'isAllRole') {
      if(value === 'true'){
        setFormData((prevData) => ({
          ...prevData,
          [name]: value,
          workflowRole: [],
        }));
      }
      else{
        setFormData((prevData) => ({
          ...prevData,
          [name]: value,
        }));
      }
    } 
    else {
      setFormData((prevData) => ({
        ...prevData,
        [name]: value,
      }));
    }
  };

  const handleMultiSelectChange = (event) => {
    const { name, value } = event.target;
    const selectedValues = Array.isArray(value) ? value : [value]; // Ensure value is always an array
  
    setFormData((prevData) => ({
      ...prevData,
      [name]: selectedValues,
    }));
  };

  const handleActionClick = (workflow, extraData) => {
    console.log(typeof handleWorkflowSelection);
    if (typeof handleWorkflowSelection === 'function') {
      console.log("Yes")
      console.log(workflow)
      handleWorkflowSelection(workflow, extraData);
    }
  };

  const getInputLabelRole = () => {
    if (formData.isAllRole === 'true') {
      return 'The workflow can be initiated by any user';
    }
    return 'Select one or more roles';
  };
  
  const handleSubmit = (event) => {
    event.preventDefault();
    console.log("form", formData);
    fetch('http://localhost:8085/api/v1/workflow', {
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
                const statusMsg = "The workflow has been added successfully. Please create the first task."
                // Swal.fire(
                //     'Registered Successfully',
                //     statusMsg,
                //     'success'
                // );
                console.log(statusMsg)
                console.log("Workflow", resData)
                handleActionClick("Task Creator", resData);
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

  return (
    <form onSubmit={handleSubmit}>
      <Typography variant="h5" sx={{fontWeight: "bold"}} gutterBottom>
        Create New Workflow
      </Typography>
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
          <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Workflow</FormLabel>
        </TableCell>
        <TableCell >
          <FormControl fullWidth margin='normal'>
            <TextField
              name="workflowName"
              value={formData.taskName}
              onChange={handleInputChange}
              label="Workflow Name"
              required={true}
              sx={{backgroundColor:"#edf4fb", '& .MuiFormLabel-asterisk': { display: 'none' }}}
            />
          </FormControl>
        </TableCell>
      </TableRow>
      <TableRow>      
        <TableCell>
          <FormLabel required={true} sx={{fontSize:"1.23rem"}}>Workflow Description</FormLabel>
        </TableCell>
        <TableCell>
          <FormControl fullWidth margin="normal">
          <TextField
            name="workflowDescription"
            value={formData.taskDescription}
            onChange={handleInputChange}
            label="Workflow Description"
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
          <FormLabel required={true} sx={{fontSize:"1.23rem"}}>
            Initiator Type
          </FormLabel>
        </TableCell>
        <TableCell>
          <FormControl component="fieldset" margin="normal">
            <RadioGroup
              row
              name="isAllRole"
              value={formData.isAllRole}
              onChange={handleInputChange}
              required={true}
              defaultValue={true}
            >
              <FormControlLabel value="true" control={<Radio />} label="All User" />
              <FormControlLabel value="false" control={<Radio />} label="Specific Role" />
            </RadioGroup>
          </FormControl>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <FormLabel required={formData.isAllRole !== 'true'} sx={{fontSize:"1.23rem"}}>Initiator's Role</FormLabel>
        </TableCell>
        <TableCell>
          <FormControl fullWidth margin="normal">
          <InputLabel>{getInputLabelRole()}</InputLabel>
            <Select
              name="workflowRole"
              value={formData.workflowRole}
              onChange={handleMultiSelectChange}
              multiple
              label={getInputLabelRole()}
              required={formData.isAllRole !== 'true'} // Set required conditionally
              disabled={formData.isAllRole === 'true' || formData.isAllRole === ''} // Set disabled conditionally
              sx={{backgroundColor:"#edf4fb"}}
            >
              {
                allRoles.map((role) => (
                  <MenuItem key={role.roleId} value={role.roleName}>
                    {role.roleName}
                  </MenuItem>
                ))
              }
            </Select>
          </FormControl>
        </TableCell>
      </TableRow>
      </TableBody>
      </Table>
      <FormGroup row sx={{ alignItems:"center", justifyContent:"center" , mt:"20px", mb:"30px"}}>
        <Button type="submit" variant="contained" color="primary" sx={{ml:"10px", mr:"10px", width:"20%"}}>
          Save and Create First Task
        </Button>
      </FormGroup>      
    </form>
  );
};

export default WorkflowCreator;
