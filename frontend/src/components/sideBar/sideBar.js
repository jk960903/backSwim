import React, { Component } from 'react';
import './sideBar.css';

function SideBarItem(text, id, handler) {
  Object.assign(this, { text, id, handler });
}

export default class SidedBar extends Component {
  handlerSearch() {
    console.log('search!!');
  }

  handlerLocation() {
    console.log('location!!');
  }

  handlerFavorite() {
    console.log('favorite!!');
  }

  renderItems() {
    const items = [
      new SideBarItem('검색', 'search', this.handlerSearch),
      new SideBarItem('지역', 'location', this.handlerLocation),
      new SideBarItem('즐겨\n찾기', 'favorite', this.handlerFavorite),
    ];

    return items.map((value, idx) => (
      <button
        key={idx}
        className="side-bar-item"
        id={value.id}
        onClick={() => value.handler()}
      >
        {value.text}
      </button>
    ));
  }

  render() {
    return <div className="side-bar">{this.renderItems()}</div>;
  }
}
