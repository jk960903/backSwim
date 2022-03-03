import React from 'react';
import PropTypes from 'prop-types';
import { withRouterHook } from '#utils/withRouter';
import MyRequest from '#common/myRequest';
import AlertModal from '#components/modal/alertModal';
import { SignUpError } from '#common/myError';
class EmailAuth extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: {
        isOpen: false,
        content: '',
      },
    };
  }

  componentDidMount() {
    console.log(this.props.query.get('uuid'));
    const uuid = this.props.query.get('uuid');

    MyRequest.emailAuth(uuid)
      .then((value) => {
        if (value.data === false) {
          throw new SignUpError('잘못된 인증 정보 입니다');
        }
        this.props.navigate('/signIn');
      })
      .catch((err) => {
        if (err instanceof SignUpError) {
          this.setState({
            modal: { isOpen: true, content: err.message },
          });
        }
      });
  }

  setModalOpenFalse() {
    this.setState({
      modal: { ...this.state.modal, isOpen: false },
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
    return (
      <>
        <div>이메일 인증 중 입니다</div>;{this.renderAlertModal()}
      </>
    );
  }
}

EmailAuth.propTypes = {
  query: PropTypes.object.isRequired,
  navigate: PropTypes.func.isRequired,
};

export default withRouterHook(EmailAuth);
