/**
 * @jest-environment jsdom
 */

import React from 'react';
import { render, unmountComponentAtNode } from 'react-dom';
import { act } from 'react-dom/test-utils';
import pretty from 'pretty';
import Footer from './footer.js';

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

describe('[Pool] BAC-30 Footer 컴포넌트', () => {
  test('SnapShot test', () => {
    act(() => {
      render(<Footer></Footer>, container);
    });

    expect(pretty(container.innerHTML)).toMatchInlineSnapshot(`
"<div class=\\"footer\\">
  <div class=\\"footer-container-logo\\"><i class=\\"fas fa-swimmer\\"></i></div>
  <div class=\\"footer-container-desc\\">
    <div>초보 개발자 2인이 제작한 홈페이지 입니다</div>
  </div>
  <div class=\\"footer-container-button\\">
    <div>컨텐츠1</div>
    <div>컨텐츠2</div>
    <div>컨텐츠3</div>
  </div>
</div>"
`);
  });
});
