# I-Curriculum(서버) Convention 정리

## 작업 FLOW

### 마일스톤 생성
- 주 마다 마일스톤 생성 
- 이번주에 해야 할일 정하고 나누고, 다같이 todo 만들기

### issue 생성 
- 각자 만든 todo에 따라 issue 생성
  - 제목 : `[{태그}]:{간단한 제목}`
- project -> todo로 issue 생성
  - assignment, label, 해당 주차 milstone 설정하기

#### 작업에 해당하는 브랜치 만들기
#### Todo 에서 In Progress 로 옮기고 작업시작


## 브랜치 관리 
### 브랜치 naming rule
```
feature/{issue 번호}  -> 각자 기능 개발할 때 사용, 로컬에서 각자 테스트

	(Squash and Merge)

hotfix/{issue 번호} -> merge후 오류 수정, 혹은 api 연동시 빠르게 수정하는 경우

develop  -> 개발한 기능을 병합, 로컬에서 테스트 진행

	(Release 해당 Develop 브랜치에서 생성)

release  -> develop 브랜치를 로컬에서 실행 시 문제가 없으면 해당 커밋에 release 브랜치 생성
-> 개발시 테스트용 서버로 배포

	(Commit and Merge)

main  -> 실제 운영할 서버로 배포, release에서 QA 후 main으로 merge
```

### pull request
- pr 이름은 issue랑 똑같이
- 대신 []안에 티켓번호(이슈 번호도 붙이기)
  - ex. [Docs-10] : github pr, commit 컨벤션 관련 README 작성
#### review
- line by line 으로 읽으면서 comment
- 초반에 코드 와르르 올라올때는 진짜 제대로 review 할것
- approve 4개 이상 못받으면 merge 불가

#### merge
- merge는 develop 브랜치를 타켓으로 `squash and merge`

