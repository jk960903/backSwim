const defaultDelay = 300; // 0.3s

const debounce = (callback, delay = defaultDelay) => {
  let timerId;
  return (event) => {
    if (timerId) clearTimeout(timerId);
    timerId = setTimeout(callback, delay, event);
  };
};

export default debounce;
