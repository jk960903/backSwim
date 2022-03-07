import React from 'react';
import debounce from '#utils/debounce';
import { emailValidation } from '#utils/validation';
import './resetPassword.css';
import AlertModal from '#components/modal/alertModal';
import MyRequest from '#common/myRequest';
import { ResetPasswordError } from '#common/myError';

class ResetPassword extends React.Component {
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
  }

  setModalOpenFalse() {
    this.setState({
      modal: { ...this.state.modal, isOpen: false },
    });
  }

  handlerChangeItem(e) {
    if (e.target.id === 'find-password-item-email') {
      this.email = e.target.value;
    }

    if (this.email.length === 0) return;

    if (emailValidation(this.email) === false) {
      this.setState({ errMsg: '유효한 이메일을 입력해주세요', formValid: false });
    } else {
      this.setState({ errMsg: null, formValid: true });
    }
  }

  submitHandler(e) {
    e.preventDefault();

    if (this.formValid === false) return;

    MyRequest.resetPassword(this.email)
      .then((value) => {
        if (value.data === false) {
          throw new ResetPasswordError('잘못된 이메일 입니다');
        }
        console.log(value);
      })
      .catch((err) => {
        /* TODO: UserError로 전부 바꾸고 상속 구조 만들 것*/
        if (err instanceof ResetPasswordError) {
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

    return <span className="find-password-item find-password-item-alert">{errMsg}</span>;
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

  render() {
    return (
      <>
        <form onSubmit={(e) => this.submitHandler(e)}>
          <div
            className="find-password-container"
            onChange={debounce((e) => this.handlerChangeItem(e), 200)}
          >
            <div className="find-password-item">
              <label htmlFor="">이메일</label>
              <input id="find-password-item-email" type="email" placeholder="이메일" />
            </div>
            {this.renderAlert()}
            <button
              type="submit"
              className="find-password-item find-password-item-btn"
              disabled={!this.state.formValid}
            >
              비밀번호 초기화
            </button>
          </div>
        </form>
        {this.renderAlertModal()}
      </>
    );
  }
}

export default ResetPassword;
