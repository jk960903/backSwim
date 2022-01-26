/**
 * @jest-environment jsdom
 */

import React from 'react';
import { render, unmountComponentAtNode } from 'react-dom';
import { act } from 'react-dom/test-utils';
import pretty from 'pretty';
import SideBar from './sideBar.js';

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

describe('[Pool] BAC-31 SideBar 컴포넌트', () => {
  test('SnapShot test', () => {
    act(() => {
      render(<SideBar></SideBar>, container);
    });

    expect(pretty(container.innerHTML)).toMatchInlineSnapshot(`
"<div class=\\"side-bar\\"><button class=\\"side-bar-item\\" id=\\"search\\">검색</button><button class=\\"side-bar-item\\" id=\\"location\\">지역</button><button class=\\"side-bar-item\\" id=\\"favorite\\">즐겨
    찾기</button></div>"
`);
  });
});
