import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
//import Swal from 'sweetalert2';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import { TextField, InputLabel, Button} from '@mui/material';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
//import AdbIcon from '@mui/icons-material/Adb';
import InsightsIcon from '@mui/icons-material/Insights';


function LoginForm({setUser}) {
  const [userEmail, setUseremail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  window.sessionStorage.removeItem("sessionUser");
  setUser(null);

  const gotToSignupPage=()=>{
    console.log("Yes")
    navigate("/signup");
  }

  const handleLogin = (event) => {
      event.preventDefault(); 
      const userCred = {
        email : userEmail,
        password: password
      }
      console.log(userCred)
      const url = 'http://localhost:8085/api/v1/user?email='+ userEmail
        console.log(url)
        fetch(url, {
            method : "GET"
        })
      .then((response)=>{
        console.log(response)
        if(response.status===200)
        {
            const data = response.json().then(resData => {
                console.log("resData ");
                console.log(resData);
                if(resData){
                  let userData = JSON.stringify(resData);
                  setUser(resData)
                  window.sessionStorage.setItem("sessionUser", userData);
                  console.log(window.sessionStorage.getItem('sessionUser'))
                  navigate('/api/v1');
                }
                else{
                  // Swal.fire(
                  //   'Failure',
                  //   'Unauthorized access!',
                  //   'error'
                  // );
                }
            })
        }
        else{
          alert("Invalid Credentials");
        }
    })
    .catch((error)=>{
        console.log(error)
    })

    };

  return (

    <div className="Container" style={{width:"30%", marginTop:"2%", marginLeft:"35%", justifyContent:"center"}}>
    {/* <h1 style={{marginBottom:"30px"}}>Login</h1> */}
    <Box sx={{ display: 'flex', justifyContent: 'center', mb:"30px" }}>
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
                color: 'inherit',
                textDecoration: 'none',
              }}
            >
              FLOW
            </Typography>
    </Box>
    <Form onSubmit={handleLogin} style={{border:"2px solid #555", padding:"5%"}} className='card'>
                <Row className="mb-3">
                    <Form.Group as={Col} controlId="formGridFirstName">
                        <InputLabel component="legend">User Email ID</InputLabel>
                        <TextField
                        type="email"
                        variant='outlined'
                        color='secondary'
                        label="User Email"
                        onChange={(e) => setUseremail(e.target.value)}
                        value={userEmail}
                        required
                        fullWidth
                        />
                    </Form.Group>
                  </Row>
                  <Row className="mb-3">
                    <Form.Group as={Col} controlId="formGridLastName">
                        <InputLabel component="legend">Password</InputLabel>
                        <TextField
                        type="password"
                        variant='outlined'
                        color='secondary'
                        label="Password"
                        onChange={(e) => setPassword(e.target.value)}
                        value={password}
                        required
                        fullWidth
                        />
                    </Form.Group>

                </Row>
                <Button variant="contained" type="submit" color='primary' sx={{ mb:"10px" }} >
                    Login
                </Button>
                <Button variant="contained" type="submit" color='primary' onClick={() => gotToSignupPage()}>
                    New User? Sign up!
                </Button>
            </Form>
    </div>
  );
}

export default LoginForm;
