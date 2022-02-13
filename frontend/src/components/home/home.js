import React from 'react';
import Header from 'Components/header/header.js';
import Footer from 'Components/footer/footer.js';
import Pool from 'Components/pool/pool.js';
import SideBar from 'Components/sideBar/sideBar.js';
import './home.css';
import { EXT } from 'Common/myTypes.js';
import { PoolNode } from 'Common/myNodes.js';

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

  setCurPool(nxtPool) {
    if (nxtPool instanceof PoolNode === false) {
      console.error(`Param Error: ${nxtPool}`);
      return;
    }

    this.setState({
      curPool: nxtPool,
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
            setCurPool={(value) => this.setCurPool(value)}
            curExt={this.state.curExt}
            curPool={this.state.curPool}
            curPoolDetail={this.state.curPoolDetail}
          ></SideBar>
          <Pool
            setCur={(nxtExt, nxtPool, nxtPoolDetail) => this.setCur(nxtExt, nxtPool, nxtPoolDetail)}
            curPool={this.state.curPool}
          ></Pool>
        </div>
        <Footer></Footer>
      </div>
    );
  }
}

export default Home;
