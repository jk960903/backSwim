import PropTypes from 'prop-types';
import React, { Component } from 'react';
import SideBarDetailItem from './sideBarDetailItem';
import './sideBarDetail.css';

export class SideBarDetail extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const poolList = this.props.poolList;
    const poolDetailList = this.props.poolDetailList;
    console.log(poolList);
    console.log(poolList.length);

    console.log('Rendering: SideBarDetail');
    return (
      <div className="side-bar-detail">
        {poolList.map((value, idx) => {
          console.log(poolDetailList[idx]);
          if (poolDetailList[idx] === undefined) return;

          return (
            <SideBarDetailItem
              key={idx}
              pool={value}
              poolDetail={poolDetailList[idx]}
            ></SideBarDetailItem>
          );
        })}
      </div>
    );
  }
}

export default SideBarDetail;

SideBarDetail.propTypes = {
  poolList: PropTypes.array.isRequired,
  poolDetailList: PropTypes.array.isRequired,
};
