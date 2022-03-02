/**
 *
 * 유저 에러가 아닌 Client 사이드에서 발생하는 에러 입니다
 * TODO: 해당 에러를 로그에 저장하고 서버에 전송하는 로직이 필요
 * @class ClientError
 * @extends {Error}
 */
class ClientError extends Error {
  constructor(response, ...params) {
    super(...params);

    this.name = 'ClientError';
    this.response = response;
  }

  print() {
    console.error(`${this.name}: ${this.message}`);
    console.error(this.response);
  }
}

/**
 *
 * 유저 에러입니다. e.g) 중복된 회원가입, 아이디 및 패스워드 오류
 * @class SignUpError
 * @extends {Error}
 */
class SignUpError extends Error {
  constructor(...params) {
    super(...params);

    this.name = 'SignUpError';
  }

  print() {
    console.error(`${this.name}: ${this.message}`);
  }
}

export { SignUpError, ClientError };
