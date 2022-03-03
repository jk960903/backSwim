import React from 'react';
import PropTypes from 'prop-types';
import './modal.css';

class AlertModal extends React.Component {
  constructor(props) {
    super(props);
  }

  closeModal(e) {
    if (e.target.id === 'modal') {
      this.props.closeHandler();
    }
  }

  render() {
    return (
      <div id="modal" onClick={this.closeModal.bind(this)}>
        <div className="modal-window">
          <div className="modal-content">{this.props.content}</div>
          <div className="button-container">
            <button className="modal-close-btn" onClick={this.props.closeHandler}>
              닫기
            </button>
          </div>
        </div>
      </div>
    );
  }
}

AlertModal.propTypes = {
  isOpen: PropTypes.bool.isRequired,
  content: PropTypes.string.isRequired,
  closeHandler: PropTypes.func.isRequired,
};

export default AlertModal;
