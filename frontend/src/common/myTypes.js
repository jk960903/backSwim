const EXT = Object.freeze({
  NONE: Symbol('none'),
  TEXT: Symbol('text'),
  LOCATION_SI: Symbol('location-si'),
  LOCATION_GU: Symbol('location-gu'),
  FAVORITE: Symbol('favorite'),
  DETAIL: Symbol('detail'),
});

const INC_DEC = Object.freeze({
  INCREASE: Symbol('increase'),
  DECREASE: Symbol('decrease'),
});

export { EXT, INC_DEC };
