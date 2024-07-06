import * as React from 'react';
import { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import AppBar from '@mui/material/AppBar';
import CssBaseline from '@mui/material/CssBaseline';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import AddBoxIcon from '@mui/icons-material/AddBox';
import CreateNewFolderIcon from '@mui/icons-material/CreateNewFolder';
import ViewListIcon from '@mui/icons-material/ViewList';
import AssignmentIcon from '@mui/icons-material/Assignment';
import MailIcon from '@mui/icons-material/Mail';
import ResponsiveAppBar from './MyNavbar';
import WorkflowCreator from './WorkflowCreator';
import WorkflowViewer from './WorkflowViewer';
import UserScreen from './UserInfo';
import TaskViewer from './TaskViewer';
import UserWorkflowViewer from './UserWorkflows';
import TaskCreator from './TaskCreator';
import TaskLinker from './TaskLinker';
import UserWorkflowTasks from './UserWorkflowTasks';
import UserInbox from './UserInbox';
import TaskAction from './TaskAction';
import UserTaskAction from './UserTaskAction';
import UserCreatedWorkflow from './UserCreatedWorkflow';

const drawerWidth = 280;

const HomePage = ({userInfo}) => {
    console.log("Login success")
    console.log("sidebar", userInfo)
    const user = userInfo;
    //const user = JSON.parse(userInfo);
    //const { userId, userName, userEmail, roles } = user;
    const [selectedWorkflow, setSelectedWorkflow] = useState('User Info');
    const [additionalData, setAdditionalData] = useState(null);
    const [workflowInfo, setWorkflowInfo] = useState(null);

    useEffect(() => {
      console.log("Additional data", additionalData);

    }, [additionalData]); // This ensures the effect runs whenever additionalData changes
    

    const handleWorkflowSelection = (workflow, data) => {
      setSelectedWorkflow(workflow);
      console.log("selected", selectedWorkflow)
      setAdditionalData(data);
      console.log("Additional data", data)
    };
  
    const renderMainContent = () => {
      switch (selectedWorkflow) {
        case 'User Info':
           return <UserScreen userInfo={user}/>
        case 'View Workflows':
          return <WorkflowViewer handleWorkflowSelection={handleWorkflowSelection} userInfo={user}/>;
        case 'MyWorkflows':
          return <UserWorkflowViewer handleWorkflowSelection={handleWorkflowSelection} userInfo={user} />;
        case 'MyTaskInbox':
          return <UserInbox handleWorkflowSelection={handleWorkflowSelection} userInfo={user}/>;
        case 'Task Instance Viewer':
          return <UserWorkflowTasks userInfo={user} workflowInfo={additionalData} handleWorkflowSelection={handleWorkflowSelection}/>
        case 'Task Viewer':
          return <TaskViewer userInfo={user} workflowInfo={additionalData} handleWorkflowSelection={handleWorkflowSelection}/>
        case 'Task Creator':
          return <TaskCreator handleWorkflowSelection={handleWorkflowSelection} userInfo={user} workflowInfo={additionalData}/>
        case 'Task Linker':
          return <TaskLinker handleWorkflowSelection={handleWorkflowSelection} userInfo={user} workflowInfo={additionalData} />
        case 'Task Action':
          return <TaskAction handleWorkflowSelection={handleWorkflowSelection} userInfo={user} taskInfo={additionalData} />
        case 'View Task Action':
          return <UserTaskAction handleWorkflowSelection={handleWorkflowSelection} userInfo={user} taskInfo={additionalData}/>
        case 'Create Workflow':
          return <WorkflowCreator handleWorkflowSelection={handleWorkflowSelection} userInfo={user}/>;
        case 'My Created Workflows':
          return <UserCreatedWorkflow handleWorkflowSelection={handleWorkflowSelection} userInfo={user}/>
        default:
          return <UserScreen userInfo={user}/>;
      }
    };
  
    const userRoles = userInfo && userInfo.roles ? userInfo.roles.map(role => role.roleName) : [];
    const isWorkflowCreator = userRoles.includes('Workflow Creator');


  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
        <ResponsiveAppBar userInfo = {user}/>
      </AppBar>
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
        }}
      >
        <Toolbar />
        <Box sx={{ overflow: 'auto', mt:"20px"}}>
        <List>
            {[
              { text: 'View Workflows', icon: <ViewListIcon /> },
              { text: 'MyWorkflows', icon: <AssignmentIcon /> },
              { text: 'MyTaskInbox', icon: <MailIcon /> },
              //{ text: 'Create Workflow', icon: <AddBoxIcon /> } 
              isWorkflowCreator && { text: 'Create Workflow', icon: <AddBoxIcon /> },
              isWorkflowCreator && { text: 'My Created Workflows', icon: <CreateNewFolderIcon />}

            ].map((item, index) => (
              <ListItem
                sx={{mb:"20px"}}
                key={item.text}
                disablePadding
                onClick={() => handleWorkflowSelection(item.text)}
              >
                <ListItemButton selected={selectedWorkflow === item.text}>
                  <ListItemIcon>{item.icon}</ListItemIcon>
                  <ListItemText primary={item.text} />
                </ListItemButton>
              </ListItem>
            ))}
          </List>
          <Divider />
        </Box>
      </Drawer>
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Toolbar />
        
        {renderMainContent()}
        
      </Box>
    </Box>
  );
}

export default HomePage;
