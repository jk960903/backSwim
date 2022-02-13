import React from 'react';
import PropTypes from 'prop-types';
import './pool.css';
import MyRequest from 'Common/myRequest';
import { EXT } from 'Common/myTypes';
import { GeolocationNode, PoolNode, PoolDetailNode } from 'Common/myNodes';

const { kakao } = window;
const markerImgSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
const defaultLat = 37.2253;
const defaultLng = 127.0701;
const defaultMapLv = 6;

export default class Pool extends React.Component {
  map = null;
  markers = new Map();

  constructor(props) {
    super(props);
    this.state = {
      curGeo: new GeolocationNode(defaultLat, defaultLng, defaultMapLv),
      poolMap: new Map(), // 지도에 표시되는 수영장 Map
    };
  }

  componentDidUpdate(preProps, preStates) {
    if (preProps.curPool !== this.props.curPool) {
      const curGeo = this.state.curGeo;
      const curPool = this.props.curPool;
      this.moveMapByLatLng(curPool.latitude, curPool.longitude, curGeo.mapLevel);
    } else if (preStates.curGeo !== this.state.curGeo) {
      const curGeo = this.state.curGeo;
      /* TODO: 너무 많은 마커에 대한 최적화 필요 */
      this.getPoolsAndMarking(curGeo.latitude, curGeo.longitude, curGeo.mapLevel);
    }
  }

  marking(pool, latitude, longitude) {
    /* 기존에 마킹 했다면 스킵 */
    if (this.markers.has(pool.placeName) === true) return;

    this.markers.set(pool.placeName, true);

    // marking
    const imageSize = new kakao.maps.Size(24, 35);
    const markerImage = new kakao.maps.MarkerImage(markerImgSrc, imageSize);
    const marker = new kakao.maps.Marker({
      map: this.map, // 마커를 표시할 지도
      position: new kakao.maps.LatLng(latitude, longitude), // 마커를 표시할 위치
      title: pool.placeName, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      image: markerImage, // 마커 이미지
    });

    /* 마커 클릭 이벤트 핸들러*/
    function markerClickListener(pool, lat, lng) {
      return function () {
        const curGeo = this.state.curGeo;
        this.moveMapByLatLng(lat, lng, curGeo.mapLevel);

        /* TODO: 에러처리 필요 */
        /* TODO: loading 페이지 필요 */
        MyRequest.getPoolDetailById(pool.id).then((res) => {
          const poolDetail = new PoolDetailNode(res.data.poolDetail);
          this.props.setCur(EXT.DETAIL, pool, poolDetail);
        });
      };
    }

    kakao.maps.event.addListener(
      marker,
      'click',
      markerClickListener(pool, latitude, longitude).bind(this)
    );
  }

  getPoolsAndMarking(latitude, longitude, mapLevel) {
    const poolMap = this.state.poolMap;
    MyRequest.getPoolsByGeoLocation(latitude, longitude, mapLevel)
      .then((res) => {
        for (const pool of res.data.pool) {
          if (poolMap.has(pool.id) === false) {
            poolMap.set(pool.id, new PoolNode(pool));
          }
        }
      })
      .then(() => {
        for (const [, value] of poolMap) {
          this.marking(value, value.latitude, value.longitude);
        }
      });
  }

  /**
   *
   * parameter에 맞게 지도를 움직입니다
   * state change: curGeo
   * @param {number} lat
   * @param {number} lng
   * @param {number} mapLevel
   * @memberof Pool
   */
  moveMapByLatLng(lat, lng, mapLevel) {
    this.map.setCenter(new kakao.maps.LatLng(lat, lng));
    this.setState({
      curGeo: new GeolocationNode(lat, lng, mapLevel),
    });
  }

  /**
   *
   * 현재 위도 경도를 가져오며 moveMapByLatLng 함수 호출
   * @memberof Pool
   */
  moveCurrentGeolocation() {
    function success(pos) {
      const curGeo = this.state.curGeo;
      const crd = pos.coords;

      this.moveMapByLatLng(crd.latitude, crd.longitude, curGeo.mapLevel);
    }

    function error(err) {
      console.warn(`ERROR(${err.code}): ${err.message}`);
    }

    navigator.geolocation.getCurrentPosition(success.bind(this), error.bind(this));
  }

  renderKakaoMap() {
    const curGeo = this.state.curGeo;
    const container = document.getElementById('map');
    const options = {
      center: new kakao.maps.LatLng(curGeo.latitude, curGeo.longitude),
      level: curGeo.mapLevel,
    };

    this.map = new kakao.maps.Map(container, options);

    /* kakao copyright 위치 */
    this.map.setCopyrightPosition(kakao.maps.CopyrightPosition.BOTTOMRIGHT, true);

    /* 드래그 이벤트 핸들러 등록 */
    function mapDragEventListener() {
      const curGeo = this.state.curGeo;
      const latlng = this.map.getCenter();

      this.moveMapByLatLng(latlng.getLat(), latlng.getLng(), curGeo.mapLevel);
    }

    kakao.maps.event.addListener(this.map, 'dragend', mapDragEventListener.bind(this));

    /* zoom 이벤트 핸들러 등록 */
    const zoomControl = new kakao.maps.ZoomControl();
    this.map.addControl(zoomControl, kakao.maps.ControlPosition.BOTTOMRIGHT);
    function mapZoomEventListener() {
      const curGeo = this.state.curGeo;
      const mapLevel = this.map.getLevel();

      this.moveMapByLatLng(curGeo.latitude, curGeo.longitude, mapLevel);
    }

    kakao.maps.event.addListener(this.map, 'zoom_changed', mapZoomEventListener.bind(this));
  }

  componentDidMount() {
    this.renderKakaoMap();
    this.moveCurrentGeolocation();
  }

  render() {
    console.log('Rendering: Pool');
    return <div id="map" sytle="width:500px;height:400px;" className="pool"></div>;
  }
}

Pool.propTypes = {
  setCur: PropTypes.func.isRequired,
  curPool: PropTypes.instanceOf(PoolNode),
};
