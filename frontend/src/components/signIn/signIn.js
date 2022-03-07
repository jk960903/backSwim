import React from 'react';
import PropTypes from 'prop-types';
import './signIn.css';
import AlertModal from '#components/modal/alertModal';
import MyRequest from '#common/myRequest';
import { SignInError } from '#common/myError';
import { withRouterHook } from '#utils/withRouter';
import debounce from '#utils/debounce';
import { emailValidation } from '#utils/validation';

class SignIn extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      errMsg: null,
      formValid: false,
      modal: {
        isOpen: false,
        content: '',
      },
    };
    this.email = '';
    this.password = '';
  }

  setModalOpenFalse() {
    this.setState({
      modal: { ...this.state.modal, isOpen: false },
    });
  }

  handlerChangeItem(e) {
    if (e.target.id === 'sign-in-item-email') {
      this.email = e.target.value;
    } else if (e.target.id === 'sign-in-item-pwd') {
      this.password = e.target.value;
    }

    if (this.email.length === 0 || this.password.length === 0) return;

    if (emailValidation(this.email) === false) {
      this.setState({ errMsg: '유효한 이메일을 입력해주세요', formValid: false });
    } else {
      this.setState({ errMsg: null, formValid: true });
    }
  }

  submitHandler(e) {
    e.preventDefault();

    if (this.formValid === false) return;

    MyRequest.signIn(this.email, this.password)
      .then((value) => {
        console.log(value);
        if (value.statusCode === 401) {
          if (value.message === 'AUTH_FIRST') {
            this.props.navigate('/emailSent', {
              state: { email: this.email, isSent: false },
            });
          } else if (value.message === 'WRONG_PASSWORD') {
            throw new SignInError('잘못된 패스워드 입니다');
          } else throw value;
        } else if (value.data === null) {
          throw new SignInError('이메일 또는 패스워드를 확인해주세요');
        } else {
          this.props.navigate('/', { state: { isLogin: true } });
        }
      })
      .catch((err) => {
        if (err instanceof SignInError) {
          this.setState({
            modal: { isOpen: true, content: err.message },
          });
        } else {
          console.error(err);
        }
      });
  }

  renderAlert() {
    const { errMsg } = this.state;

    if (errMsg === null) return;

    return <span className="sign-in-item sign-in-item-alert">{errMsg}</span>;
  }

  renderAlertModal() {
    const modal = this.state.modal;
    if (modal.isOpen === false) return;

    return (
      <AlertModal
        isOpen={modal.isOpen}
        content={modal.content}
        closeHandler={this.setModalOpenFalse.bind(this)}
      ></AlertModal>
    );
  }

  renderResetPassword() {
    function handlerResetPassword() {
      this.props.navigate('/resetPassword');
    }

    return (
      <div
        className="sign-in-item sign-in-item-reset-password"
        onClick={handlerResetPassword.bind(this)}
      >
        비밀번호를 잃어버리셨나요?
      </div>
    );
  }

  render() {
    return (
      <>
        <form onSubmit={(e) => this.submitHandler(e)}>
          <div
            className="sign-in-container"
            onChange={debounce((e) => this.handlerChangeItem(e), 200)}
          >
            <div className="sign-in-item">
              <label htmlFor="">이메일</label>
              <input id="sign-in-item-email" type="email" placeholder="이메일" />
            </div>
            <div className="sign-in-item">
              <label htmlFor="">비밀번호</label>
              <input
                id="sign-in-item-pwd"
                type="password"
                placeholder="비밀번호"
                autoComplete="on"
              />
            </div>
            {this.renderResetPassword()}
            {this.renderAlert()}
            <button
              type="submit"
              className="sign-in-item sign-in-item-btn"
              disabled={!this.state.formValid}
            >
              로그인
            </button>
          </div>
        </form>
        {this.renderAlertModal()}
      </>
    );
  }
}

SignIn.propTypes = {
  navigate: PropTypes.func.isRequired,
};

export default withRouterHook(SignIn);
