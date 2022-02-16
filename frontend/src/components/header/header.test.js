/**
 * @jest-environment jsdom
 */

import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';

import Header from './header.js';

describe('[Pool] BAC-29 헤더 컴포넌트', () => {
  describe('비로그인 경우', () => {
    beforeEach(() => {
      render(
        <BrowserRouter>
          <Header isLogin={false}></Header>
        </BrowserRouter>
      );
    });
    test('로그인 버튼 렌더링', () => {
      expect(screen.getByText(/Sign In/)).toBeInTheDocument();
    });
    test('회원가입 버튼 렌더링', () => {
      expect(screen.getByText(/Sign Up/)).toBeInTheDocument();
    })
  });

  describe('로그인 경우', () => {
    beforeEach(() => {
      render(
        <BrowserRouter>
          <Header isLogin={true}></Header>
        </BrowserRouter>
      );
    });
    test('마이 페이지 버튼 렌더링', () => {
      expect(screen.getByText(/My Page/)).toBeInTheDocument();
    });
  });
});
