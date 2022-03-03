import { ClientError } from './myError';

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
          throw new ClientError(data, 'there is no statusCode');
        }

        if (data.statusCode !== 401 && data.statusCode !== 200) {
          throw new ClientError(data, 'statuscode error');
        }
        return data;
      })
      .catch((err) => {
        /* catching client error */
        if (err instanceof ClientError) {
          err.print();
        }

        throw err;
      });
  }

  function customRequestToServer(url, option) {
    return fetch(url, option)
      .then((response) => response.json())
      .then((data) => {
        if (Object.prototype.hasOwnProperty.call(data, 'statusCode') === false) {
          throw new ClientError(data, 'there is no statusCode');
        }

        if (data.statusCode !== 401 && data.statusCode !== 200) {
          throw new ClientError(data, 'statuscode error');
        }
        return data;
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

  function signUp(userEmail, password) {
    const data = { userEmail, password };
    const url = host + '/joinmember/addmember';
    const option = { ...defaultOption };
    option.method = 'POST';
    option.body = JSON.stringify(data);
    return requestToServer(url, option);
  }

  function signIn(userEmail, password) {
    const data = { userEmail, password };
    const url = host + '/login/login';
    const option = { ...defaultOption };
    option.method = 'POST';
    option.body = JSON.stringify(data);
    return requestToServer(url, option);
  }

  function resendEmailVerfication(userEmail) {
    const params = '?' + new URLSearchParams({ userEmail }).toString();
    const url = host + '/joinmember/resend-mail' + params;
    const option = { ...defaultHeaders };
    return customRequestToServer(url, option)
      .then((value) => {
        if (value.data === null) {
          throw new ClientError(`paramter error: param: ${userEmail}`);
        }
        return value;
      })
      .catch((e) => {
        if (e instanceof ClientError) {
          e.print();
        }
        throw e;
      });
  }

  function emailAuth(uuid) {
    const params = '?' + new URLSearchParams({ uuid }).toString();
    const url = host + '/joinmember/email-auth' + params;
    const option = { ...defaultHeaders };
    return requestToServer(url, option);
  }

  return {
    getPoolsByGeoLocation: getPoolsByGeoLocation,
    getPoolsByName: getPoolsByName,
    getPoolDetailById: getPoolDetailById,
    getPoolListByLocation: getPoolListByLocation,
    signUp: signUp,
    signIn: signIn,
    resendEmailVerfication: resendEmailVerfication,
    emailAuth: emailAuth,
  };
})();

export default MyRequest;
