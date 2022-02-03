import React, { Component } from 'react';
import './sideBar.css';
import SideBarText from './sideBarText';
import SideBarLocation from './sideBarLocation';
import SideBarFavorite from './sideBarFavorite';
import BackButton from './backButton';

function SideBarItem(text, id, handler) {
  Object.assign(this, { text, id, handler });
}

const EXT = Object.freeze({
  NONE: Symbol('none'),
  TEXT: Symbol('text'),
  LOCATION: Symbol('location'),
  FAVORITE: Symbol('favorite'),
});

export default class SidedBar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      curExt: EXT.NONE,
      slide: true,
    };
  }

  handlerSideBarButton(targetExt) {
    let nxtSlide = false;
    if (this.state.curExt !== targetExt) {
      nxtSlide = true;
    }

    this.setState({
      curExt: targetExt,
      slide: nxtSlide,
    });
  }

  handlerBackBtnClick() {
    this.setState({
      curExt: EXT.NONE,
      slide: true,
    });
  }

  renderSideBarExt() {
    const curExt = this.state.curExt;
    const slide = this.state.slide;

    if (curExt === EXT.NONE) return;

    const classList = slide === true ? 'side-bar-ext-container slide' : 'side-bar-ext-container';

    return (
      <div id="test" className={classList} onAnimationEnd={() => this.setState({ slide: false })}>
        {curExt === EXT.TEXT ? (
          <SideBarText></SideBarText>
        ) : curExt === EXT.LOCATION ? (
          <SideBarLocation></SideBarLocation>
        ) : (
          <SideBarFavorite></SideBarFavorite>
        )}
        <BackButton clickHandler={() => this.handlerBackBtnClick()}></BackButton>
      </div>
    );
  }

  renderSideBarButtons() {
    const buttons = [
      new SideBarItem('검색', 'search', this.handlerSideBarButton.bind(this, EXT.TEXT)),
      new SideBarItem('지역', 'location', this.handlerSideBarButton.bind(this, EXT.LOCATION)),
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
    return (
      <div className="side-bar">
        {this.renderSideBarButtons()}
        {this.renderSideBarExt()}
      </div>
    );
  }
}
