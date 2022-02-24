import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './sideBar.css';
import SideBarText from '#components/sideBar/sideBarText';
import SideBarLocation from '#components/sideBar/sideBarLocation';
import SideBarFavorite from '#components/sideBar/sideBarFavorite';
import SideBarDetail from '#components/sideBar/sideBarDetail';
import BackButton from '#components/sideBar/backButton';
import { PoolNode, PoolDetailNode } from '#common/myNodes';
import { EXT } from '#common/myTypes';

function SideBarItem(text, id, handler) {
  Object.assign(this, { text, id, handler });
}

export default class SideBar extends Component {
  constructor(props) {
    super(props);
  }

  handlerSideBarButton(targetExt) {
    this.props.setCurExt(targetExt);
  }

  handlerBackBtnClick() {
    this.props.setCurExt(EXT.NONE);
  }

  renderSideBarExt() {
    const curExt = this.props.curExt;

    if (curExt === EXT.NONE) return;

    const classList = 'side-bar-ext-container slide';

    return (
      <div className={classList}>
        {curExt === EXT.TEXT ? (
          <SideBarText setCurPool={this.props.setCurPool}></SideBarText>
        ) : curExt === EXT.LOCATION_SI ? (
          <SideBarLocation></SideBarLocation>
        ) : curExt === EXT.FAVORITE ? (
          <SideBarFavorite></SideBarFavorite>
        ) : (
          <SideBarDetail
            poolList={[this.props.curPool]}
            poolDetailList={[this.props.curPoolDetail]}
          ></SideBarDetail>
        )}
        <BackButton clickHandler={() => this.handlerBackBtnClick()}></BackButton>
      </div>
    );
  }

  renderSideBarButtons() {
    const buttons = [
      new SideBarItem('검색', 'search', this.handlerSideBarButton.bind(this, EXT.TEXT)),
      new SideBarItem('지역', 'location', this.handlerSideBarButton.bind(this, EXT.LOCATION_SI)),
      new SideBarItem('즐겨\n찾기', 'favorite', this.handlerSideBarButton.bind(this, EXT.FAVORITE)),
    ];

    function renderItems(items) {
      return items.map((value, idx) => (
        <button key={idx} className="side-bar-item" id={value.id} onClick={() => value.handler()}>
          {value.text}
        </button>
      ));
    }

    return <div className="side-bar-buttons"> {renderItems(buttons)}</div>;
  }

  render() {
    console.log('Rendering: SideBar');
    return (
      <div className="side-bar">
        {this.renderSideBarButtons()}
        {this.renderSideBarExt()}
      </div>
    );
  }
}

SideBar.propTypes = {
  setCurExt: PropTypes.func.isRequired,
  setCurPool: PropTypes.func.isRequired,
  curExt: PropTypes.symbol.isRequired,
  curPool: PropTypes.instanceOf(PoolNode),
  curPoolDetail: PropTypes.instanceOf(PoolDetailNode),
};
