import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from 'Components/home/home.js';
import SignIn from 'Components/signIn/signIn.js';
import SignUp from 'Components/signUp/signUp.js';
import MyPage from 'Components/myPage/myPage.js';
import NotFound from 'Components/notFound/notFound';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLogin: false,
    };
  }

  render() {
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home></Home>}></Route>
          <Route path="/signIn" element={<SignIn></SignIn>}></Route>
          <Route path="/signUp" element={<SignUp></SignUp>}></Route>
          <Route path="/myPage" element={<MyPage></MyPage>}></Route>
          <Route path="*" element={<NotFound></NotFound>}></Route>
        </Routes>
      </BrowserRouter>
    );
  }
}

export default App;
