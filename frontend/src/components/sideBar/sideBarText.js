import React, { Component } from 'react';
import './sideBarText.css';
import MyRequest from 'Common/myRequest';
import debounce from 'Utils/debounce';

export default class SideBarText extends Component {
  constructor(props) {
    super(props);
    this.state = {
      searchResult: [],
    };
  }

  handlerChange(event) {
    if (event.target.value.length === 0) {
      return;
    }

    MyRequest.getPoolsByName(event.target.value).then((res) => {
      this.setState({
        searchResult: res.data.pool,
      });
    });
  }

  // 검색어에 맞는 추천 수영장 리스트를 렌더링 합니다.
  renderSearchResult() {
    const searchResult = this.state.searchResult;

    if (searchResult.length === 0) {
      return;
    }

    return (
      <div className="side-bar-search-list">
        {searchResult.map((value, idx) => (
          <div className="side-bar-serach-item" key={idx}>
            {value.placeName}
          </div>
        ))}
      </div>
    );
  }

  render() {
    return (
      <div className="side-bar-text">
        <div className="side-bar-search-bar">
          <button className="search-button">
            <i className="fas fa-search"></i>
          </button>
          <input
            id="side-bar-search-engine"
            type="text"
            placeholder="수영장 이름을 입력해주세요"
            autoComplete="off"
            onChange={debounce((e) => this.handlerChange(e))}
          />
        </div>
        {this.renderSearchResult()}
      </div>
    );
  }
}
