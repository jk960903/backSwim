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

export { GeolocationNode, PoolNode, PoolDetailNode };
