module.exports = {
  moduleNameMapper: {
    '\\.(css|less)$': '<rootDir>/__mocks__/styleMock.js',
    '#components/(.*)$': '<rootDir>/src/components/$1',
    '#common/(.*)$': '<rootDir>/src/common/$1',
    '#utils/(.*)$': '<rootDir>/src/utils/$1',
  },
};
