import React from 'react';
import './pool.css';
import MyRequest from '../../common/myRequest';

const { kakao } = window;
const markerImgSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
const defaultLat = 37.2253;
const defaultLng = 127.0701;
const defaultMapLv = 6;

function GeolocationNode(latitude, longitude, mapLevel) {
  Object.assign(this, { latitude, longitude, mapLevel });
}

function PoolNode({ id, placeName, roadAddressName, latitude, longitude, phone }) {
  Object.assign(this, {
    id,
    placeName,
    roadAddressName,
    latitude,
    longitude,
    phone,
  });
}

class Pool extends React.Component {
  map = null;

  constructor(props) {
    super(props);
    this.state = {
      curGeo: new GeolocationNode(defaultLat, defaultLng, defaultMapLv),
      poolList: [],
    };
  }

  marking(placeName, latitude, longitude) {
    // marking
    const imageSize = new kakao.maps.Size(24, 35);
    const markerImage = new kakao.maps.MarkerImage(markerImgSrc, imageSize);
    const marker = new kakao.maps.Marker({
      map: this.map, // 마커를 표시할 지도
      position: new kakao.maps.LatLng(latitude, longitude), // 마커를 표시할 위치
      title: placeName, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      image: markerImage, // 마커 이미지
    });

    // adding event listener

    function markerClickListener(placeName) {
      return function () {
        console.log(placeName);
      };
    }

    kakao.maps.event.addListener(marker, 'click', markerClickListener(placeName));
  }

  getPoolsAndMarking(latitude, longitude, mapLevel) {
    const poolList = this.state.poolList;
    MyRequest.getPoolsByGeoLocation(latitude, longitude, mapLevel)
      .then((res) => {
        for (const pool of res.data.pool) {
          poolList.push(new PoolNode(pool));
        }
      })
      .then(() => {
        for (const pool of poolList) {
          this.marking(pool.placeName, pool.latitude, pool.longitude);
        }
      });
  }

  moveCurrentGeolocation() {
    function success(pos) {
      const curGeo = this.state.curGeo;
      const crd = pos.coords;

      // move center to current location
      this.map.setCenter(new kakao.maps.LatLng(crd.latitude, crd.longitude));

      // getting the pools on current location and marking
      this.getPoolsAndMarking(crd.latitude, crd.longitude, curGeo.mapLevel);

      this.setState({
        curGeo: new GeolocationNode(crd.latitude, crd.longitude, curGeo.mapLevel),
      });
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
  }

  componentDidMount() {
    this.renderKakaoMap();
    this.moveCurrentGeolocation();
  }

  render() {
    return <div id="map" sytle="width:500px;height:400px;" className="pool"></div>;
  }
}

export default Pool;
