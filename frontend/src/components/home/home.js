import React from 'react';
import Header from '../header/header.js';
import Footer from '../footer/footer.js';
import Pool from '../pool/pool.js';
import SideBar from '../sideBar/sideBar.js';
import './home.css';
import { EXT } from '../../common/myTypes.js';

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLogin: false,
      curExt: EXT.NONE,
      curPool: null,
      curPoolDetail: null,
    };
  }

  setCurExt(target) {
    this.setState({
      curExt: target,
    });
  }

  setCur(nxtExt, nxtPool, nxtPoolDetail) {
    this.setState({
      curExt: nxtExt,
      curPool: nxtPool,
      curPoolDetail: nxtPoolDetail,
    });
  }

  render() {
    console.log('Rendering: Home');
    return (
      <div>
        <Header isLogin={this.state.isLogin}></Header>
        <div className="home-container">
          {/* TODO: setCurExt 전달 정리하기 */}
          <SideBar
            setCurExt={(target) => this.setCurExt(target)}
            curExt={this.state.curExt}
            curPool={this.state.curPool}
            curPoolDetail={this.state.curPoolDetail}
          ></SideBar>
          <Pool
            setCur={(nxtExt, nxtPool, nxtPoolDetail) => this.setCur(nxtExt, nxtPool, nxtPoolDetail)}
          ></Pool>
        </div>
        <Footer></Footer>
      </div>
    );
  }
}

export default Home;
