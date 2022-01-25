import React, { Component } from 'react';
import './footer.css';

export default class Footer extends Component {
  render() {
    return (
      <div className="footer">
        <div className="footer-container-logo">
          <i className="fas fa-swimmer"></i>
        </div>
        <div className="footer-container-desc">
          <div>초보 개발자 2인이 제작한 홈페이지 입니다</div>
        </div>
        <div className="footer-container-button">
          <div>컨텐츠1</div>
          <div>컨텐츠2</div>
          <div>컨텐츠3</div>
        </div>
      </div>
    );
  }
}
