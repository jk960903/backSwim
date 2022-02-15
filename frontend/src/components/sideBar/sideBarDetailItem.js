import PropTypes from 'prop-types';
import React, { Component } from 'react';
import MyTime from '#utils/myTime';
import './sideBarDetailItem.css';

export default class SideBarDetailItem extends Component {
  constructor(props) {
    super(props);
  }

  isOpen(poolDetail) {
    const [curDay, curTime] = [MyTime.getCurDay(), MyTime.getCurTime()];
    let ret = false;
    switch (curDay) {
      case 0:
        if (
          MyTime.diffTime(poolDetail.sunOpen, curTime) === true &&
          MyTime.diffTime(curTime, poolDetail.sunClose) === true
        ) {
          ret = true;
        }
        break;
      case 1:
        if (
          MyTime.diffTime(poolDetail.monOpen, curTime) === true &&
          MyTime.diffTime(curTime, poolDetail.monClose) === true
        ) {
          ret = true;
        }
        break;
      case 2:
        if (
          MyTime.diffTime(poolDetail.tueOpen, curTime) === true &&
          MyTime.diffTime(curTime, poolDetail.tueClose) === true
        ) {
          ret = true;
        }
        break;
      case 3:
        if (
          MyTime.diffTime(poolDetail.wedOpen, curTime) === true &&
          MyTime.diffTime(curTime, poolDetail.wedClose) === true
        ) {
          ret = true;
        }
        break;
      case 4:
        if (
          MyTime.diffTime(poolDetail.thurOpen, curTime) === true &&
          MyTime.diffTime(curTime, poolDetail.thurClose) === true
        ) {
          ret = true;
        }
        break;
      case 5:
        if (
          MyTime.diffTime(poolDetail.friOpen, curTime) === true &&
          MyTime.diffTime(curTime, poolDetail.friClose) === true
        ) {
          ret = true;
        }
        break;
      case 6:
        if (
          MyTime.diffTime(poolDetail.satOpen, curTime) === true &&
          MyTime.diffTime(curTime, poolDetail.satClose) === true
        ) {
          ret = true;
        }
        break;
      default:
        break;
    }

    return ret;
  }

  render() {
    const pool = this.props.pool;
    const poolDetail = this.props.poolDetail;
    const isOpen = this.isOpen(poolDetail);
    console.log('Rendering: SideBarDetailItem');

    return (
      <div className="detail-item">
        <div className="detail-name detail-text-overflow">{pool.placeName}</div>
        <div className="detail-container">
          <div className="detail-phone-img">
            <i className="fas fa-phone-alt"></i>
          </div>
          <div className="detail-phone">{pool.phone.length === 0 ? '-' : pool.phone}</div>
        </div>
        <div className="detail-container">
          <div className="detail-location-img">
            <i className="fas fa-map-marker-alt"></i>
          </div>
          <div className="detail-location">
            {pool.roadAddressName.length === 0 ? '-' : pool.roadAddressName}
          </div>
        </div>
        <div className="detail-container">
          <div className="detail-open-img">
            <i className="far fa-clock"></i>
          </div>
          <div className="detail-is-open">{isOpen === true ? '운영 중' : '닫음'}</div>
        </div>
        <div className="detail-container">
          <div className="detail-cost-img">
            <i className="fas fa-won-sign"></i>
          </div>
          <div className="detail-cost">{poolDetail.price}</div>
        </div>
        <div className="detail-url detail-text-overflow">{poolDetail.url}</div>
      </div>
    );
  }
}

SideBarDetailItem.propTypes = {
  pool: PropTypes.object.isRequired,
  poolDetail: PropTypes.object.isRequired,
};
