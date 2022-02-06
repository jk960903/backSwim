function GeolocationNode(latitude, longitude, mapLevel) {
  Object.assign(this, { latitude, longitude, mapLevel });
}

// prettier-ignore
function PoolNode({ id, placeName, roadAddressName, latitude, longitude, phone }) {
  Object.assign(this, { id, placeName, roadAddressName, latitude, longitude, phone});
}

// prettier-ignore
function PoolDetailNode({id, monOpen, monClose, tueOpen, tueClose, wedOpen, wedClose, thurOpen, thurClose, friOpen, friClose, satOpen, satClose, sunOpen, sunClose, price, url}) {
  Object.assign(this, {id, monOpen, monClose, tueOpen, tueClose, wedOpen, wedClose, thurOpen, thurClose, friOpen, friClose, satOpen, satClose, sunOpen, sunClose, price, url});
}

// prettier-ignore
function LocationNode(name, children = [], queryName) {
  if(queryName === undefined) queryName = name;
  Object.assign(this, {name, children, queryName});
}

// prettier-ignore
const locationList = [
  new LocationNode('서울',
  [
    new LocationNode('용산구'),new LocationNode('강남구'),new LocationNode('관악구'),new LocationNode('동작구'),new LocationNode('송파구'),
    new LocationNode('종로구'),new LocationNode('영등포구'),new LocationNode('강동구'),new LocationNode('마포구'),new LocationNode('광진구'),
    new LocationNode('강서구'),new LocationNode('양천구'),new LocationNode('구로구'),new LocationNode('금천구'),
  ]),
  new LocationNode('경기', [
    new LocationNode('부천시'),new LocationNode('화성시'),new LocationNode('시흥시'),new LocationNode('남양주시'),new LocationNode('양평군'),
    new LocationNode('안산시'),new LocationNode('광명시'),new LocationNode('여주시'),new LocationNode('성남시'),new LocationNode('안양시'),
    new LocationNode('용인시'),new LocationNode('안성시'),new LocationNode('오산시'),new LocationNode('이천시'),new LocationNode('광주시'),
    new LocationNode('수원시'),new LocationNode('김포시'),new LocationNode('의왕시'),new LocationNode('하남시'),new LocationNode('평택시'),
    new LocationNode('과천시'),
  ]),
  new LocationNode('인천', [
    new LocationNode('부평구'),new LocationNode('중구'),new LocationNode('남동구'),new LocationNode('미추홀구'),new LocationNode('서구'),
    new LocationNode('연수구'),new LocationNode('계양구'),
  ]),
  new LocationNode('부산', [
    new LocationNode('중구'),new LocationNode('해운대구'),new LocationNode('동구'),new LocationNode('부산진구'),new LocationNode('서구'),
    new LocationNode('사하구'),new LocationNode('수영구'),new LocationNode('영도구'),new LocationNode('금정구'),new LocationNode('강서구'),
    new LocationNode('북구'),new LocationNode('사상구'),new LocationNode('기장군'),new LocationNode('연제구'),
  ]),
  new LocationNode('울산', [
    new LocationNode('남구'),new LocationNode('중구'),new LocationNode('동구'),new LocationNode('북구'),
    new LocationNode('울주군'),
  ]),
  new LocationNode('충북', [
    new LocationNode('충주시'),new LocationNode('청주시'),new LocationNode('영동군'),new LocationNode('괴산군'),new LocationNode('진천군'),
    new LocationNode('증평군'),new LocationNode('음성군'),new LocationNode('단양군'),
  ]),
  new LocationNode('전북', [
    new LocationNode('고창군'),new LocationNode('전주시'),new LocationNode('정읍시'),new LocationNode('익산시'),new LocationNode('임실군'),
    new LocationNode('부안군'),new LocationNode('김제시'),new LocationNode('장수군'),new LocationNode('남원시'),new LocationNode('순창군'),
    new LocationNode('무주군'),new LocationNode('군산시'),
  ]),
  new LocationNode('충남', [
    new LocationNode('계룡시'),new LocationNode('부여군'),new LocationNode('서산시'),new LocationNode('천안시'),new LocationNode('당진시'),
    new LocationNode('보령시'),new LocationNode('아산시'),new LocationNode('금산군'),new LocationNode('홍성군'),new LocationNode('청양군'),
    new LocationNode('공주시'),new LocationNode('예산군'),new LocationNode('논산시'),new LocationNode('서천군'),
  ]),
  new LocationNode('강원', [
    new LocationNode('삼척시'),new LocationNode('원주시'),new LocationNode('동해시'),new LocationNode('횡성군'),new LocationNode('영월군'),
  ]),
  new LocationNode('경북', [
    new LocationNode('영덕군'),new LocationNode('영천시'),new LocationNode('문경시'),new LocationNode('청송군'),new LocationNode('김천시'),
    new LocationNode('울진군'),new LocationNode('포항시'),new LocationNode('상주시'),new LocationNode('안동시'),new LocationNode('구미시'),
    new LocationNode('칠곡군'),new LocationNode('경주시'),new LocationNode('영양군'),
  ]),
  new LocationNode('대전', [
    new LocationNode('대덕구'),new LocationNode('중구'),new LocationNode('동구'),new LocationNode('서구'),new LocationNode('유성구'),
  ]),
  new LocationNode('제주', [
    new LocationNode('제주시'), new LocationNode('서귀포시')
  ], '제주특별자치도'),
  new LocationNode('세종', [
    new LocationNode('아름동'),new LocationNode('금남면'),new LocationNode('한솔동'),new LocationNode('전동면'),new LocationNode('장군면'),
    new LocationNode('보람동'),
  ], '세종특별자치시'),
  new LocationNode('전남', [
    new LocationNode('장성군'),new LocationNode('곡성군'),new LocationNode('영암군'),new LocationNode('고흥군'),new LocationNode('나주시'),
    new LocationNode('해남군'),new LocationNode('무안군'),new LocationNode('담양군'),new LocationNode('목포시'),new LocationNode('순천시'),
    new LocationNode('보성군'),new LocationNode('완도군'),new LocationNode('여수시'),new LocationNode('화순군'),new LocationNode('장흥군'),
    new LocationNode('영광군'),new LocationNode('구례군'),new LocationNode('광양시'),new LocationNode('강진군'),
  ]),
  new LocationNode('경남', [
    new LocationNode('양산시'),new LocationNode('사천시'),new LocationNode('김해시'),new LocationNode('거창군'),new LocationNode('합천군'),
    new LocationNode('진주시'),new LocationNode('의령군'),new LocationNode('밀양시'),new LocationNode('남해군'),new LocationNode('하동군'),
    new LocationNode('고성군'),new LocationNode('함양군'),new LocationNode('산청군'),
  ]),
  new LocationNode('광주', [
    new LocationNode('남구'),new LocationNode('동구'),new LocationNode('서구'),new LocationNode('광산구'),new LocationNode('북구'),
  ]),
];

export { GeolocationNode, PoolNode, PoolDetailNode, LocationNode, locationList };
