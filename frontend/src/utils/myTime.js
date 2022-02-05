const MyTime = (() => {
  function getCurDay() {
    const curDate = new Date();

    return curDate.getDay();
  }

  function getCurTime() {
    const curDate = new Date();

    return curDate.toTimeString().split(' ')[0];
  }

  /* parameter은 HH:MM:SS 포맷 */
  function time2Sec(time) {
    const [hh, mm, ss] = time.split(':').map((value) => parseInt(value));

    return ss + mm * 60 + hh * 3600;
  }

  /* 시간을 비교하는 함수로 time1이 fastTime이 slowTime보다 빠르면 true 아니면 false*/
  /* parameter는 HH:MM:SS 포맷 */
  function diffTime(fastTime, slowTime) {
    if (time2Sec(fastTime) <= time2Sec(slowTime)) return true;

    return false;
  }

  return {
    diffTime: diffTime,
    getCurTime: getCurTime,
    getCurDay: getCurDay,
  };
})();

export default MyTime;
