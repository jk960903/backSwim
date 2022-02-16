module.exports = {
  moduleNameMapper: {
    '\\.(css|less)$': '<rootDir>/__mocks__/styleMock.js',
    '#components/(.*)$': '<rootDir>/src/components/$1',
    '#common/(.*)$': '<rootDir>/src/common/$1',
    '#utils/(.*)$': '<rootDir>/src/utils/$1',
  },
  // 각각의 테스트 파일을 실행하기 전에 configure 또는 setup 하는 module의 path
  setupFilesAfterEnv: ['./src/setupTests.js'],
};
