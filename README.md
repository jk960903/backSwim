## 프로젝트 개요
유저에게 전국 수영장의 위치와 요금, 시간, 강의목록 등의 정보를 지도와 함께 한 눈에 전달할 수 있는 웹 어플리케이션 입니다


## 프론트엔드 개발 과정
[0. 프로젝트 개요, 관리 및 브랜치 전략](https://velog.io/@whow1101/0.-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B0%9C%EC%9A%94-%EA%B4%80%EB%A6%AC-%EB%B0%8F-%EB%B8%8C%EB%9E%9C%EC%B9%98-%EC%A0%84%EB%9E%B5)

[1. 함수형 컴포넌트와 클래스 컴포넌트](https://velog.io/@whow1101/1.-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%8B%9C%EC%9E%91-React-%ED%95%A8%EC%88%98%ED%98%95-%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8%EC%99%80-%ED%81%B4%EB%9E%98%EC%8A%A4-%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8)

[2. React-Router](https://velog.io/@whow1101/2.-React-router-dom)

[3. Header 컴포넌트와 테스트](https://velog.io/@whow1101/3.-Header-%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8%EC%99%80-%ED%85%8C%EC%8A%A4%ED%8A%B8)

[4. Footer 컴포넌트와 grid](https://velog.io/@whow1101/4.-Footer-%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8%EC%99%80-grid)

[5. Pool 컴포넌트와 Request 모듈(1/2)](https://velog.io/@whow1101/5.-Pool-%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8%EC%99%80-Request-%EB%AA%A8%EB%93%88)

[6. Pool 컴포넌트와 Request 모듈(2/2)](https://velog.io/@whow1101/6.-Pool-%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8%EC%99%80-Request-%EB%AA%A8%EB%93%8822)

[7. 수영장 검색과 debounce](https://velog.io/@whow1101/7.-%EC%88%98%EC%98%81%EC%9E%A5-%EA%B2%80%EC%83%89%EA%B3%BC-debounce)

[8. 스프린트 회고](https://velog.io/@whow1101/8.-%EC%8A%A4%ED%94%84%EB%A6%B0%ED%8A%B8-%ED%9A%8C%EA%B3%A0)

## 백엔드 개발 과정

## Infra 
### git commit template 적용
``` bash
git config commit.template ./commit-template/feature-template
```

### git hooks 적용
아래는 git hooks 적용사항 입니다.
- pre-commit
    - eslint
``` bash
chmod +x frontend/git_hooks/copy_git_hooks.sh
frontend/git_hooks/copy_git_hooks.sh
```