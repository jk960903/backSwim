import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { PoolNode, PoolDetailNode } from '../../common/myNodes';
import SideBarDetailItem from './sideBarDetailItem';
import './sideBarDetail.css';

export class SideBarDetail extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    console.log('Rendering: SideBarDetail');
    return (
      <div className="side-bar-detail">
        <SideBarDetailItem
          curPool={this.props.curPool}
          curPoolDetail={this.props.curPoolDetail}
        ></SideBarDetailItem>
      </div>
    );
  }
}

export default SideBarDetail;

SideBarDetail.propTypes = {
  curPool: PropTypes.instanceOf(PoolNode),
  curPoolDetail: PropTypes.instanceOf(PoolDetailNode),
};
