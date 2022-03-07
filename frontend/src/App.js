import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from '#components/home/home.js';
import SignIn from '#components/signIn/signIn.js';
import SignUp from '#components/signUp/signUp.js';
import MyPage from '#components/myPage/myPage.js';
import NotFound from '#components/notFound/notFound';
import EmailSent from '#components/emailSent/emailSent';
import EmailAuth from '#components/emailAuth/emailAuth';
import ResetPassword from '#components/resetPassword/resetPassword';
import ChangePassword from '#components/changePassword/changePassword';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLogin: false,
    };
  }

  /* TODO: path 인증, 렌더링 관점에서 개선 필요*/
  /* TODO: path 이름 수정 */
  render() {
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home></Home>}></Route>
          <Route path="/signIn" element={<SignIn></SignIn>}></Route>
          <Route path="/signUp" element={<SignUp></SignUp>}></Route>
          <Route path="/resetPassword" element={<ResetPassword></ResetPassword>}></Route>
          <Route path="/changePassword" element={<ChangePassword></ChangePassword>}></Route>
          <Route path="/myPage" element={<MyPage></MyPage>}></Route>
          <Route path="/emailSent" element={<EmailSent></EmailSent>}></Route>
          <Route path="/emailAuth" element={<EmailAuth></EmailAuth>}></Route>
          <Route path="*" element={<NotFound></NotFound>}></Route>
        </Routes>
      </BrowserRouter>
    );
  }
}

export default App;
