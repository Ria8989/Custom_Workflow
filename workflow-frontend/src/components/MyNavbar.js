import * as React from 'react';
import { useState } from 'react';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
//import AdbIcon from '@mui/icons-material/Adb';
import InsightsIcon from '@mui/icons-material/Insights';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';


const ResponsiveAppBar = ({userInfo}) => {
  const [anchorElUser, setAnchorElUser] = useState(null);
  
  const navigate = useNavigate();
  const logoutHandler = () => {
    window.sessionStorage.removeItem('sessionUser');
    navigate('/login');
  }

  const handleOpenUserMenu = (event) => {
    //console.log(userName)
    console.log("Nav success")
    console.log(userInfo.userName)
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  return (
    
      <Container maxWidth="false">
        <Toolbar sx={{ display: 'flex', justifyContent: 'space-between'}} disableGutters>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <InsightsIcon sx={{ ml:"5px", fontSize:"35px", display: { xs: 'none', md: 'flex' } }} />
            <Typography
              variant="h6"
              noWrap
              component="a"
              href="#app-bar-with-responsive-menu"
              sx={{
                ml:"5px",
                fontFamily: 'monospace',
                fontWeight: 800,
                fontSize:"25px",
                letterSpacing: '.3rem',
                color: 'inherit',
                textDecoration: 'none',
              }}
            >
              FLOW
            </Typography>
          </Box>
          <Box>
            <Tooltip title="Open settings">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                <Avatar alt={userInfo.userName} src="/static/images/avatar/2.jpg" />
              </IconButton>
            </Tooltip>
            <Menu
              sx={{ mt: '45px' }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
            >
              {/* Display user information in profile menu */}
            
            <MenuItem>
              <Typography>{`User Name: ${userInfo.userName}`}</Typography>
            </MenuItem>
            <MenuItem>
              <Typography>{`User Email: ${userInfo.userEmail}`}</Typography>
            </MenuItem>
            <MenuItem>
              <Typography>{`Roles: ${userInfo.roles.map(role => role.roleName).join(', ')}`}</Typography>
            </MenuItem>
            </Menu>
            <Button color="inherit" sx={{ ml:"30px", mr:"10px", fontWeight: 700, fontSize:"17px"}} onClick={logoutHandler}>Logout</Button>
          </Box>
        </Toolbar>
      </Container>
    
  );
}

export default ResponsiveAppBar;
