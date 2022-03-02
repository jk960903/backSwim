import React from 'react';
import PropTypes from 'prop-types';
import './signUp.css';
import debounce from '#utils/debounce';
import { emailValidation, passwordValidation } from '#utils/validation';
import MyRequest from '#common/myRequest';
import { withRouterHook } from '#utils/withRouter';
import AlertModal from '#components/modal/alertModa';
import { SignUpError } from '#common/myError';

/* TODO: ref와 state의 차이 */
class SignUp extends React.Component {
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
    this.password2 = '';
  }

  setModalOpenFalse() {
    this.setState({
      modal: { ...this.state.modal, isOpen: false },
    });
  }

  /**
   *
   * form 필드(id,password,password2)의 수정에 대한 핸들러
   * 유효한지 확인하고 errMsg 수정
   * @param {event} e
   * @return {void}
   * @memberof SignUp
   */
  handlerChangeItem(e) {
    if (e.target.id === 'sign-up-item-email') {
      this.email = e.target.value;
    } else if (e.target.id === 'sign-up-item-pwd') {
      this.password = e.target.value;
    } else if (e.target.id === 'sign-up-item-pwd2') {
      this.password2 = e.target.value;
    }

    if (this.email.length === 0 || this.password.length === 0 || this.password2.length === 0)
      return;

    if (emailValidation(this.email) === false) {
      this.setState({ errMsg: '유효한 이메일을 입력해주세요', formValid: false });
    } else if (passwordValidation(this.password, this.password2) === false) {
      this.setState({ errMsg: '비밀번호를 확인해주세요', formValid: false });
    } else {
      this.setState({ errMsg: null, formValid: true });
    }
  }

  /* TODO: Modal Window 필요 */
  submitHandler(e) {
    e.preventDefault();

    if (this.formValid === false) return;

    MyRequest.signUp(this.email, this.password)
      .then((value) => {
        if (value.data === false) {
          throw new SignUpError('중복된 이메일 입니다');
        } else if (value.data === null) {
          throw new SignUpError('이메일 또는 패스워드를 확인해주세요');
        }
        this.props.navigate('/emailSent', {
          state: { email: this.email, password: this.password },
        });
      })
      .catch((err) => {
        if (err instanceof SignUpError) {
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

    return <span className="sign-up-item sign-up-item-alert">{errMsg}</span>;
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

  // prettier-ignore
  render() {
    return (
      <>
      <form onSubmit={(e) => this.submitHandler(e)}>
        <div
          className="sign-up-container"
          onChange={debounce((e) => this.handlerChangeItem(e), 200)}
        >
          <div className="sign-up-item">
            <label htmlFor="">이메일</label>
            <input id="sign-up-item-email" type="email" placeholder="이메일" />
          </div>
          <div className="sign-up-item">
            <label htmlFor="">비밀번호</label>
            <input id="sign-up-item-pwd" type="password" placeholder="비밀번호" autoComplete="on" />
          </div>
          <div className="sign-up-item">
            <label htmlFor="">비밀번호 확인</label>
            <input id="sign-up-item-pwd2" type="password" placeholder="비밀번호 확인" autoComplete="on" />
          </div>
          {this.renderAlert()}
          <button type="submit" className="sign-up-item sign-up-item-btn" disabled={!this.state.formValid}>회원가입</button>
        </div>
      </form>
      {this.renderAlertModal()}
      </>

    );
  }
}

SignUp.propTypes = {
  navigate: PropTypes.func.isRequired,
};

export default withRouterHook(SignUp);
