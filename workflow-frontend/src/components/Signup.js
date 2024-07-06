import React, { useEffect, useState } from 'react';
import { FormControl, TextField, Select, MenuItem, FormGroup, Button, Typography, InputLabel, FormLabel, Table, TableRow, TableCell, tableCellClasses, TableBody } from '@mui/material';
import Box from '@mui/material/Box';
import InsightsIcon from '@mui/icons-material/Insights';
import { useNavigate } from 'react-router-dom';


const SignupForm = () => {
  const navigate = useNavigate();
  const [allroles, setAllroles] = useState([])
  const [formData, setFormData] = useState({
    userName: '',
    userEmail: '',
    password: '',
    roles: []
  });


    useEffect(() => {
      fetch('http://localhost:8085/api/v1/role/all', {
          method : "GET"
      })
      .then(response => response.json())
      .then(resData => {
          console.log(resData);
          setAllroles(resData)
      })        
      .catch((error)=>{
          console.log(error)
      });
  
      },[]
    );

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  // const handleMultiSelectChange = (event) => {
  //   const { name, value } = event.target;
  //   setFormData((prevData) => ({
  //     ...prevData,
  //     [name]: value,
  //   }));
  // };

  const handleMultiSelectChange = (event) => {
    const { name, value } = event.target;
    const selectedValues = Array.isArray(value) ? value : [value]; // Ensure value is always an array
  
    setFormData((prevData) => ({
      ...prevData,
      [name]: selectedValues,
    }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(formData); // Handle form submission data here
    fetch('http://localhost:8085/api/v1/user', {
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
                const statusMsg = "The user has been added successfully. Please login to access the application."
                // Swal.fire(
                //     'Registered Successfully',
                //     statusMsg,
                //     'success'
                // );
                console.log(statusMsg)
            })
            navigate('/login')
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
      <Box sx={{ display: 'flex', justifyContent: 'center', mb:"30px",  mt:"5%", }}>
            <InsightsIcon color="primary" sx={{ ml:"5px", fontSize:"45px", display: { xs: 'none', md: 'flex' } }} />
            <Typography
              variant="h6"
              noWrap
              sx={{
                ml:"5px",
                fontFamily: 'monospace',
                fontWeight: 900,
                fontSize:"35px",
                letterSpacing: '.3rem',
                color:'inherit',
                textDecoration: 'none',
              }}
            >
              FLOW
            </Typography>
    </Box>
      <Table sx={{
        [`& .${tableCellClasses.root}`]: {
          borderBottom: "none"
        }, maxWidth: "50%", ml:"25%", mr:"25%"
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
          <FormLabel sx={{fontSize:"1.23rem"}}>User Name</FormLabel>
        </TableCell>
        <TableCell >
          <FormControl fullWidth margin='normal'>
            <TextField
              name="userName"
              value={formData.userName}
              onChange={handleInputChange}
              label="Enter User Name"
              sx={{backgroundColor:"#edf4fb"}}
              required={true}
              type='text'
            />
          </FormControl>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell >
          <FormLabel sx={{fontSize:"1.23rem"}}>User Email</FormLabel>
        </TableCell>
        <TableCell >
          <FormControl fullWidth margin='normal'>
            <TextField
              name="userEmail"
              value={formData.taskEmail}
              onChange={handleInputChange}
              label="Enter User Email"
              sx={{backgroundColor:"#edf4fb"}}
              required={true}
              type="email"
            />
          </FormControl>
        </TableCell>
      </TableRow>      
      <TableRow>
        <TableCell >
          <FormLabel sx={{fontSize:"1.23rem"}}>User Password</FormLabel>
        </TableCell>
        <TableCell >
          <FormControl fullWidth margin='normal'>
            <TextField
              name="password"
              value={formData.password}
              onChange={handleInputChange}
              label="Enter Password"
              sx={{backgroundColor:"#edf4fb"}}
              required={true}
              type="password"
            />
          </FormControl>
        </TableCell>
      </TableRow>      
      <TableRow>
        <TableCell>
          <FormLabel sx={{fontSize:"1.23rem"}}>User Roles</FormLabel>
        </TableCell>
        <TableCell>
          <FormControl fullWidth margin="normal">
          <InputLabel>Select one or more roles</InputLabel>
            <Select
              name="roles"
              value={formData.roles}
              onChange={handleMultiSelectChange}
              multiple
              label="Select one or more roles"
              sx={{backgroundColor:"#edf4fb"}}
              required={true}
            >
              {allroles.map((role) => (
                    <MenuItem key={role.roleId} value={role.roleName}>
                      {role.roleName}
                    </MenuItem>
                  ))}
            </Select>
          </FormControl>
        </TableCell>
      </TableRow>
      </TableBody>
      </Table>
      <FormGroup row sx={{ alignItems:"center", justifyContent:"center" , mt:"20px", mb:"30px"}}>
        <Button type="submit" variant="contained" color="primary" sx={{ml:"10px", mr:"10px", width:"15%"}}>
          Sign up
        </Button>
      </FormGroup>      
    </form>
  );
};

export default SignupForm;
