/**
 * @jest-environment jsdom
 */

import React from 'react';
import { render, unmountComponentAtNode } from 'react-dom';
import { act } from 'react-dom/test-utils';
import pretty from 'pretty';
import { BrowserRouter } from 'react-router-dom';
import Header from './header.js';

let container = null;
beforeEach(() => {
  container = document.createElement('div');
  document.body.appendChild(container);
});

afterEach(() => {
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});

describe('[Pool] BAC-29 헤더 컴포넌트', () => {
  test('SnapShot test', () => {
    act(() => {
      render(
        <BrowserRouter>
          <Header isLogin={true}></Header>
        </BrowserRouter>,
        container
      );
    });

    expect(pretty(container.innerHTML)).toMatchInlineSnapshot(`
"<div class=\\"header\\"><a class=\\"header-logo-link\\" href=\\"/\\"><i class=\\"header-logo fas fa-swimmer\\"></i></a>
  <nav class=\\"header-item-container\\"><a id=\\"btn-myPage\\" class=\\"item\\" href=\\"/myPage\\">My Page</a></nav>
</div>"
`);

    act(() => {
      render(
        <BrowserRouter>
          <Header isLogin={false}></Header>
        </BrowserRouter>,
        container
      );
    });

    expect(pretty(container.innerHTML)).toMatchInlineSnapshot(`
"<div class=\\"header\\"><a class=\\"header-logo-link\\" href=\\"/\\"><i class=\\"header-logo fas fa-swimmer\\"></i></a>
  <nav class=\\"header-item-container\\"><a id=\\"btn-signIn\\" class=\\"item\\" href=\\"/signIn\\">Sign In</a><a id=\\"btn-signUp\\" class=\\"item\\" href=\\"/signUp\\">Sign Up</a></nav>
</div>"
`);
  });
});
