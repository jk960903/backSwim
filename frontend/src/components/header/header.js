import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import './header.css';

function ItemNode(text, link, id) {
  Object.assign(this, { text, link, id });
}

class Header extends React.Component {
  renderItemContainer() {
    const items =
      this.props.isLogin === false
        ? [
            new ItemNode('Sign In', '/signIn', 'btn-signIn'),
            new ItemNode('Sign Up', '/signUp', 'btn-signUp'),
          ]
        : [new ItemNode('My Page', '/myPage', 'btn-myPage')];

    return (
      <nav className="header-item-container">
        {items.map((value, idx) => {
          return (
            <Link key={idx} to={value.link} id={value.id} className="item">
              {value.text}
            </Link>
          );
        })}
      </nav>
    );
  }

  render() {
    return (
      <div className="header">
        <Link to={'/'} className="header-logo-link">
          <i className="header-logo fas fa-swimmer"></i>
        </Link>
        {this.renderItemContainer()}
      </div>
    );
  }
}

Header.propTypes = {
  isLogin: PropTypes.bool.isRequired,
};

export default Header;
