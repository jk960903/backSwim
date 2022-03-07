import React from 'react';
import debounce from '#utils/debounce';
import PropTypes from 'prop-types';
import AlertModal from '#components/modal/alertModal';
import { withRouterHook } from '#utils/withRouter';
import MyRequest from '#common/myRequest';
import './changePassword.css';
import { ChangePasswordError } from '#common/myError';

class ChangePassword extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: {
        isOpen: false,
        content: '',
      },
    };
    this.password = '';
  }

  setModalOpenFalse() {
    this.setState({
      modal: { ...this.state.modal, isOpen: false },
    });
  }

  submitHandler(e) {
    e.preventDefault();

    if (this.formValid === false) return;

    const uuid = this.props.query.get('uuid');

    MyRequest.changePassword(uuid, this.password)
      .then((value) => {
        if (value.data === false) {
          throw new ChangePasswordError('잘못된 정보입니다');
        }
        this.props.navigate('/signIn');
      })
      .catch((err) => {
        if (err instanceof ChangePasswordError) {
          this.setState({
            modal: { isOpen: true, content: err.message },
          });
        } else {
          console.error(err);
        }
      });
  }

  handlerChangeItem(e) {
    if (e.target.id === 'change-password-item-pwd') {
      this.password = e.target.value;
    }

    if (this.password.length === 0) return;

    this.setState({ formValid: true });
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
            className="change-password-container"
            onChange={debounce((e) => this.handlerChangeItem(e), 200)}
          >
            <div className="change-password-item">
              <label htmlFor="">비밀번호</label>
              <input
                id="change-password-item-pwd"
                type="password"
                placeholder="비밀번호"
                autoComplete="on"
              />
            </div>
            <button
              type="submit"
              className="change-password-item change-password-item-btn"
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

ChangePassword.propTypes = {
  query: PropTypes.object.isRequired,
  navigate: PropTypes.func.isRequired,
};

export default withRouterHook(ChangePassword);
