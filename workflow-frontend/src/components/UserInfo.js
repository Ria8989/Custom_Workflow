import * as React from 'react';

const UserScreen = ({userInfo}) => {
    console.log(userInfo.userName);
    return (
        <h2 style={{marginTop:"5%"}}>Welcome {userInfo.userName}!</h2>
    );
}

export default UserScreen;