// TODO: error 타입 만들고 throw 해줘야함

// closures in charge of interation with the server
const MyRequest = (() => {
  const host = 'http://localhost:3000/backswim/api';
  const defaultHeaders = new Headers({
    'Content-Type': 'application/json',
  });
  const defaultOption = {
    method: 'GET',
    headers: defaultHeaders,
  };

  function requestToServer(url, option) {
    return fetch(url, option)
      .then((response) => response.json())
      .then((data) => {
        if (Object.prototype.hasOwnProperty.call(data, 'statusCode') === false) {
          throw { message: 'there is no statusCode' };
        }

        if (data.statusCode !== 200) {
          throw data;
        }
        return data;
      })
      .catch((err) => {
        console.error(err);
      });
  }

  function getPoolsByGeoLocation(latitude, longitude, mapLevel) {
    const params = '?' + new URLSearchParams({ latitude, longitude, mapLevel }).toString();
    const url = host + '/pool/getpoolmapforlocate' + params;
    const option = { ...defaultOption };
    return requestToServer(url, option);
  }

  function getPoolsByName(inputQuery) {
    const params = '?' + new URLSearchParams({ inputQuery }).toString();
    const url = host + '/search/searchquery' + params;
    const option = { ...defaultOption };
    return requestToServer(url, option);
  }

  function getPoolDetailById(id) {
    const params = '?' + new URLSearchParams({ id }).toString();
    const url = host + '/pooldetail/getpooldetail' + params;
    const option = { ...defaultOption };
    return requestToServer(url, option);
  }

  // prettier-ignore
  function getPoolListByLocation(firstAddress="", secondAddress="", thirdAddress="", fourthAddress="") {
    const params = '?' + new URLSearchParams({ firstAddress, secondAddress, thirdAddress, fourthAddress }).toString();
    const url = host + '/search/searchaddress' + params;
    const option = { ...defaultOption };
    return requestToServer(url, option);
  }

  return {
    getPoolsByGeoLocation: getPoolsByGeoLocation,
    getPoolsByName: getPoolsByName,
    getPoolDetailById: getPoolDetailById,
    getPoolListByLocation: getPoolListByLocation,
  };
})();

export default MyRequest;
