import React, { Component } from 'react';
import './sideBarLocation.css';
import { locationList } from 'Common/myNodes';
import SideBarDetail from './sideBarDetail';
import MyRequest from 'Common/myRequest';

export default class SideBarLocation extends Component {
  poolList = [];
  poolDetailList = [];
  constructor(props) {
    super(props);
    this.state = {
      curSi: null,
      curGu: null,
    };
  }

  componentDidMount() {
    locationList
      .map((value) => {
        value.children.sort((a, b) => (a.name < b.name ? -1 : 1));
        return value;
      })
      .sort((a, b) => (a.name < b.name ? -1 : 1));
  }

  renderSi() {
    function containerClickListener(e) {
      const target = e.target;
      const curTarget = e.currentTarget;

      for (const child of curTarget.children) {
        if (child !== target) child.classList.remove('click');
      }

      e.target.classList.add('click');
    }

    return (
      <div className="side-bar-location-container" onClick={(e) => containerClickListener(e)}>
        {locationList.map((value, index) => {
          function clickListener() {
            /* TODO: 캐싱 필요해보임 */
            this.poolList = [];
            this.poolDetailList = [];
            this.setState({
              curSi: value,
            });
          }

          return (
            <button
              className="side-bar-location-item"
              key={index}
              onClick={() => clickListener.bind(this)()}
            >
              {value.name}
            </button>
          );
        })}
      </div>
    );
  }

  renderGu() {
    const curSi = this.state.curSi;
    if (curSi === null) return;
    const items = locationList.find((value) => value === curSi).children;

    return (
      <div className="side-bar-location-container">
        {items.map((value, index) => {
          function clickListener(curGu) {
            MyRequest.getPoolListByLocation(curSi.queryName, curGu.queryName).then((res) => {
              this.poolList = res.data.pool.slice();
              const promiseList = [];

              for (const pool of this.poolList) {
                promiseList.push(MyRequest.getPoolDetailById(pool.id));
              }
              Promise.all(promiseList).then((resList) => {
                resList = resList.map((value) =>
                  value === undefined ? undefined : value.data.poolDetail
                );
                this.poolDetailList = resList.slice();
                this.setState({
                  curGu: curGu,
                });
              });
            });
          }

          return (
            <button
              className="side-bar-location-item"
              key={index}
              onClick={() => clickListener.bind(this)(value)}
            >
              {value.name}
            </button>
          );
        })}
      </div>
    );
  }

  renderPoolListByLocation() {
    if (this.poolList.length === 0) return;
    console.log(`debug point`);
    console.log(this.poolList);
    console.log(this.poolDetailList);
    return (
      <SideBarDetail poolList={this.poolList} poolDetailList={this.poolDetailList}></SideBarDetail>
    );
  }

  render() {
    return (
      <div className="side-bar-location">
        {this.renderSi()}
        {this.renderGu()}
        {this.renderPoolListByLocation()}
      </div>
    );
  }
}
