import React from 'react';
import Header from '../header/header.js';
import Footer from '../footer/footer.js';

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLogin: false,
    };
  }

  render() {
    return (
      <div>
        <Header isLogin={this.state.isLogin}></Header>
        <Footer></Footer>
      </div>
    );
  }
}

export default Home;
