import logo from './logo.svg';
import './App.css';
import {
  BrowserRouter,
  Routes,
  Route,
  Navigate,
  Outlet,
} from "react-router-dom";
import { useEffect, useState } from "react";

import ResponsiveAppBar from "./components/MyNavbar";
import LoginForm from './components/Login';
import SignupForm from './components/Signup';
import HomePage from './components/MySidebar';
import NoPage from './components/NoPage';
import TaskCreator from './components/TaskCreator';
import WorkflowCreator from './components/WorkflowCreator';
import TaskAction from './components/TaskAction';

function App() {
  const [user, setUser] = useState(window.sessionStorage.getItem("sessionUser"));
  useEffect(() => {
    const sessionUser = window.sessionStorage.getItem("sessionUser");
    console.log(sessionUser);
    if (sessionUser !== null) {
      setUser(sessionUser);
    } else setUser(null);
  }, []);
  return (
    <div className="App">
      
        {/* <ResponsiveAppBar /> */}
        {/* <ClippedDrawer /> */}
        {/* <LoginForm/> */}
        {/* <SignupForm /> */}
        <BrowserRouter>
        <Routes>
          {console.log("session")}
          {console.log(window.sessionStorage.getItem("sessionUser"))}
          {console.log("user")}
          {console.log(user)}
          {user !== null ? (
            <>
            {console.log(user)}
            <Route path="/api/v1" element={<HomePage userInfo={user}/>}>
              {/* <Route path="workflow" element={< msg={user}/>} />
              <Route path="createEmployee" element={<Registration />} />
              <Route path="getEmployee" element={<View />} />
              <Route path="updateEmployee" element={<Update />} />
              <Route path="deleteEmployee" element={<Deletion />} /> */}
              
            </Route>
            <Route path="*" element={<NoPage />}/>
            </>
            

          ) : (
            <Route path="*" element={<Navigate to="/login" />} />
          )}
          <Route path="/login" element={<LoginForm setUser={setUser}/>} />
          {/* <Route path="/login" element={<TaskCreator />} /> */}
          <Route path="/signup" element={<SignupForm />} />
        </Routes>
        <Outlet />
      </BrowserRouter>
    </div>
  );
}

export default App;
