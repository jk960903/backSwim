import React, { Component } from 'react';
import './backButton.css';
import PropTypes from 'prop-types';

export default class BackButton extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <div className="back-btn-container">
        <button className="back-btn" onClick={() => this.props.clickHandler()}>
          <i className="fas fa-arrow-left"></i>
        </button>
      </div>
    );
  }
}

// TODO: function type 자세히
BackButton.propTypes = {
  clickHandler: PropTypes.func.isRequired,
};
