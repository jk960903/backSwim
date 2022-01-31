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
        if (data.statusCode !== 200) {
          throw new Error(data);
        }
        return data;
      })
      .catch((err) => {
        console.error(err);
      });
  }

  function getPoolsByGeoLocation(latitude, longitude, mapLevel) {
    const params = new URLSearchParams({ latitude, longitude, mapLevel }).toString();
    const url = host + '/pool/getpoolmapforlocate?' + params;
    const option = { ...defaultOption };
    return requestToServer(url, option);
  }

  return {
    getPoolsByGeoLocation: getPoolsByGeoLocation,
  };
})();

export default MyRequest;
