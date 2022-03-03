import React from 'react';
import './emailSent.css';
import debounce from '#utils/debounce';
import PropTypes from 'prop-types';
import { withRouterHook } from '#utils/withRouter';
import MyRequest from '#common/myRequest';
import AlertModal from '#components/modal/alertModal';

class EmailSent extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      modal: {
        isOpen: false,
        content: '',
      },
    };
  }

  /* TODO: refresh 일 때 전송하지 않게 수정 필요 */
  componentDidMount() {
    console.log(this.isReload);
    const { isSent } = this.props.locationState;
    if (isSent === false) {
      this.handlerSentBtn();
    }
  }

  setModalOpenFalse() {
    this.setState({
      modal: { ...this.state.modal, isOpen: false },
    });
  }

  handlerSentBtn() {
    const { email } = this.props.locationState;

    MyRequest.resendEmailVerfication(email).then(() => {
      this.setState({
        modal: { isOpen: true, content: '인증 메일이 전송되었습니다' },
      });
    });
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
    if (this.props.locationState === null) {
      return <div>잘못된 접근 입니다.</div>;
    }

    return (
      <>
        <div className="email-sent-container">
          <span>{this.props.locationState.email}로 인증 메일이 전송되었습니다</span>
          <button onClick={debounce(() => this.handlerSentBtn(), 400)}>재전송</button>
        </div>
        {this.renderAlertModal()}
      </>
    );
  }
}

EmailSent.propTypes = {
  locationState: PropTypes.object.isRequired,
};

export default withRouterHook(EmailSent);
