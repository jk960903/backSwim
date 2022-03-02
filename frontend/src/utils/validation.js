/**
 *
 * 이메일이 이메일 포맷인지 확인하는 함수
 * @param {string} email
 */
// prettier-ignore
function emailValidation(email) {
  const regex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return regex.test(String(email).toLocaleLowerCase());
}

function passwordValidation(password, password2) {
  return password === password2;
}

export { emailValidation, passwordValidation };
