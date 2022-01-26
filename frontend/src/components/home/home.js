import React from 'react';
import Header from '../header/header.js';
import Footer from '../footer/footer.js';
import Pool from '../pool/pool.js';
import SidedBar from '../sideBar/sideBar.js';
import './home.css';

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
        <div className="home-cotainer">
          <SidedBar></SidedBar>
          <Pool></Pool>
        </div>
        <Footer></Footer>
      </div>
    );
  }
}

export default Home;
