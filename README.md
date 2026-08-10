# <div align="center"> 🐾 멍냥위키 (mnwiki) </div>

> ### 멍냥위키 레포지토리
> 애완동물 Q&A 커뮤니티 웹 서비스 <br>
> 개발 기간 : 2026.05.07. ~ 2026.05.21.


## <div align="center">프로젝트 소개</div>

**멍냥위키 (mnwiki)** 는 반려동물 보호자들이 질문과 답변을 통해 정보를 공유하는 Q&A 커뮤니티 플랫폼입니다.

반려 가구 600만 시대 진입과 2027년 시장 규모 6조 원 성장이 전망되는 가운데, 기존 반려동물 커뮤니티는 대부분 파편화되어 있고 단순 닉네임 기반 활동으로 인해 질병·행동 교정 같은 민감한 질문에 대한 신뢰성이 낮다는 문제가 있었습니다. 초보 반려인이 사료 선택, 예방접종 시기, 배변 훈련 같은 기초 정보를 한 곳에서 찾기 어렵다는 점도 출발점이 되었습니다.

멍냥위키는 회원 인증 기반의 책임 답변 구조와 반려동물 종류·양육 연차가 담긴 신뢰 프로필을 통해, 질문/꿀팁/분양/나눔/자유 게시판으로 목적별 소통이 가능한 클린한 커뮤니티를 제공합니다.

> ### 기존 서비스 vs 멍냥위키
| 비교 항목 | 네이버 카페 | 포털 Q&A | 전문 플랫폼 | 멍냥위키 |
|---|---|---|---|---|
| 답변자 검증 | 단순 닉네임 | 활동량 등급제 | 전문가 검증(일반 유저 참여 제한) | 신뢰 프로필(반려동물 종류·양육 연차) |
| 정보의 질 | 다수의 양육 꿀팁 | 무분별 가짜 정보 | 신뢰도 보장(단방향 전달 중심) | 회원 인증 기반 책임 답변 |
| 광고/바이럴 | 상업 광고 취약 | 광고 링크 도배 | 자체 광고 위주 | 클린 시스템 |
| 게시판 구조 | 친목/일상 혼재 | 카테고리 광범위 | 커뮤니티 부족 | 목적별 게시판 분리 | 

<br>

## <div align="center">팀원 소개 및 역할 분담</div>

<table align="center">
  <thead>
    <tr>
      <th>
        <a href="https://github.com/alsrud1114">
          <img src="https://github.com/alsrud1114.png" width="100" />
        </a>
      </th>
      <th>
        <a href="https://github.com/Ko0623">
          <img src="https://github.com/Ko0623.png" width="100" />
        </a>
      </th>
      <th>
        <a href="https://github.com/paksak4">
          <img src="https://github.com/paksak4.png" width="100" />
        </a>
      </th>
      <th>
        <a href="https://github.com/HoHyun-Dev">
          <img src="https://github.com/HoHyun-Dev.png" width="100" />
        </a>
      </th>
      <th>
        <a href="https://github.com/avit-D">
          <img src="https://github.com/avit-D.png" width="100" />
        </a>
      </th>
      <th>
        <a href="https://github.com/joonho4">
          <img src="https://github.com/joonho4.png" width="100" />
        </a>
      </th>
      <th>
        <a href="https://github.com/soobin528">
          <img src="https://github.com/soobin528.png" width="100" />
        </a>
      </th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td align="center">송민경<br>회원/인증/권한</td>
      <td align="center">고병욱<br>질문 CRUD</td>
      <td align="center">박재석<br>답변 CRUD</td>
      <td align="center">김호현<br>검색/페이징/정렬</td>
      <td align="center">이다윗<br>UI/레이아웃/Bootstrap</td>
      <td align="center">박준호<br>DB/ERD/통합/테스트</td>
      <td align="center">송수빈<br>DB/ERD/통합/테스트</td>
    </tr>
  </tbody>
</table>

- **송민경** : 회원가입, 로그인, 로그아웃 / Spring Security 및 권한 설정
- **고병욱** : 질문 등록·조회·수정·삭제 / 작성자·날짜·조회수 처리
- **박재석** : 답변 등록·조회·수정·삭제 / 질문과 연관관계 처리
- **김호현** : 제목/내용 검색 / Pageable 적용 / 정렬 및 조회수 증가
- **이다윗** : 전체 디자인 통일 / 반응형 및 UX 개선 / 모든 화면 템플릿 통합
- **박준호, 송수빈** : ERD 설계 및 검증 / 샘플 데이터 구성 / 통합 테스트 / Git 충돌 관리 

<br>

## <div align="center">기술 스택</div>

> ### Back-End
<table align="center">
  <thead>
    <tr>
      <th>용도</th>
      <th>사용한 스택</th>
      <th>선택 이유</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td align="center">Framework</td>
      <td align="center">
        <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
      </td>
      <td align="center">MVC 구조 기반의 빠른 웹 애플리케이션 개발</td>
    </tr>
    <tr>
      <td align="center">Security</td>
      <td align="center">
        <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
      </td>
      <td align="center">회원 인증/인가 및 작성자 권한 검증</td>
    </tr>
    <tr>
      <td align="center">Data Access</td>
      <td align="center">
        <img src="https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
      </td>
      <td align="center">회원/게시글/답변/신고 엔티티 간 연관관계 매핑</td>
    </tr>
    <tr>
      <td align="center">Database</td>
      <td align="center">
        <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
      </td>
      <td align="center">회원·게시글·답변·신고 데이터의 관계형 관리</td>
    </tr>
  </tbody>
</table>

> ### Front-End
<table align="center">
  <thead>
    <tr>
      <th>용도</th>
      <th>사용한 스택</th>
      <th>선택 이유</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td align="center">Template Engine</td>
      <td align="center">
        <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
      </td>
      <td align="center">Spring Boot와 자연스럽게 연동되는 서버 사이드 렌더링</td>
    </tr>
    <tr>
      <td align="center">CSS Framework</td>
      <td align="center">
        <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
      </td>
      <td align="center">반응형 레이아웃과 통일된 UI 컴포넌트 활용</td>
    </tr>
    <tr>
      <td align="center">Style</td>
      <td align="center">
        <img src="https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=css3&logoColor=white">
      </td>
      <td align="center">화면별 세부 스타일 커스터마이징</td>
    </tr>
  </tbody>
</table>

> ### Collaboration & Version Control
<table align="center">
  <tbody>
    <tr>
      <td align="center">
        <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white">
      </td>
      <td align="center">
        <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white">
      </td>
    </tr>
  </tbody>
</table>

> ### 시스템 구조도
<div align="center">
  <img width="676" height="597" alt="system-architecture" src="https://github.com/user-attachments/assets/a1e6b600-59a5-4320-b234-166e37e4dfa9" />
</div>

<br>

## <div align="center">API 명세서</div>

> ### 계정/인증
| 기능 | Method | URL |
|---|---|---|
| 회원가입 | POST, GET | `/signup` |
| 로그인 | POST, GET | `/login` |
| 로그아웃 | POST | `/logout` |
| 내 정보 조회 | GET | `/mypage` |
| 닉네임 수정 | POST | `/mypage/update` |
| 비밀번호 변경 | POST | `/mypage/password` |
| 회원탈퇴 | POST | `/mypage/delete` |
| 아이디 중복확인 | GET | `/check-nickname` |
| 닉네임 중복확인 | GET | `/check-username` |
| 이메일 중복확인 | GET | `/check-email` |
| 내가 쓴 질문 목록 | GET | `/mypage/questions` |
| 내가 쓴 답변 목록 | GET | `/mypage/answers` |

> ### 질문 등록/조회/수정/삭제
| 기능 | Method | URL |
|---|---|---|
| 질문 상세 조회 | GET | `/questions/detail/{question_id}` |
| 질문 등록 | POST | `/questions/add` |
| 질문 등록 FORM | GET | `/questions/add` |
| 질문 수정 | POST | `/questions/edit/{question_id}` |
| 질문 수정 FORM | GET | `/questions/edit/{question_id}` |
| 질문 삭제 | POST | `/questions/delete/{question_id}` |

> ### 검색/정렬/페이징
| 기능 | Method | URL |
|---|---|---|
| 검색 | GET | `/questions/{category}?keyword=&searchType=` |
| 질문 목록(페이징) | GET | `/questions/{category}?page=` |
| 정렬 | GET | `/questions/{category}?sort=createdAt&direction=desc` |
| 조회수 증가 | GET | `/questions/detail/{question_id}` |

> ### 답변 등록/조회/수정/삭제
| 기능 | Method | URL |
|---|---|---|
| 답변 등록 | POST | `/answers/{questionId}` |
| 답변 수정 화면 | GET | `/answers/edit/{answerId}` |
| 답변 수정 | POST | `/answers/edit/{answerId}` |
| 답변 삭제 | POST | `/answers/delete/{answerId}` |

> ### 관리자/신고 관리
| 기능 | Method | URL |
|---|---|---|
| 신고 목록 조회 | GET | `/admin/reports` |
| 미처리 신고 조회 | GET | `/admin/reports/pending` |
| 신고 승인/삭제 | POST | `/admin/reports/{report_id}/delete` |
| 신고 반려 | POST | `/admin/reports/{report_id}/reject` |
| 게시글 신고 | POST | `/questions/{question_id}/report` |
| 답변 신고 | POST | `/answers/{answer_id}/report` |

<br>

## <div align="center">주요 기능 소개</div>

1. **회원가입 및 로그인** : 회원가입/로그인/로그아웃, 비밀번호 암호화, 로그인 상태별 메뉴 접근 제어, 작성자 권한 검증
2. **게시판** : 질문 / 자유 / 분양 / 애완용품 나눔 / 꿀팁, 목적별 게시판 분리
3. **질문 CRUD** : 등록/조회/수정/삭제, 작성자·작성일 표시, 조회수 증가, 제목/내용 검색, 이미지 업로드, 페이징·최신순 정렬
4. **답변 CRUD** : 질문 게시글 답변 등록·조회, 답변 수정/삭제, 질문-답변 연관관계 처리
5. **신고 기능** : 게시물/답변 신고 처리, 신고 처리(승인/반려) 상태 관리, 신고된 게시글·답변의 안전한 연관데이터 정리 후 삭제

<br>

## <div align="center">개발 시작하기</div>

> ### application.properties 설정하기

프로젝트 루트의 `src/main/resources/application.properties`에서 아래 값을 본인 환경에 맞게 수정하세요.

```
spring.datasource.url=jdbc:mysql://localhost:3306/mnwiki?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

> ### 로컬 서버 접속하기

```
./mvnw spring-boot:run
```

> ### 사전 준비
- MySQL 서버가 실행 중이어야 하며, `mnwiki` 데이터베이스가 미리 생성되어 있어야 합니다.
```sql
  CREATE DATABASE mnwiki;
```
- `spring.jpa.hibernate.ddl-auto=update` 설정으로 서버 최초 실행 시 엔티티 기준으로 테이블이 자동 생성됩니다.

<br>

## <div align="center">폴더 구조</div>

```
mnwiki/
├─ 📁 .mvn/
│  └─ 📁 wrapper/
│
└─ 📁 src/
   ├─ 📁 main/
   │  ├─ 📁 java/com/basic/bootbasic4/
   │  │  ├─ 📁 config/       (설정)
   │  │  ├─ 📁 controller/   (컨트롤러)
   │  │  ├─ 📁 dto/          (DTO)
   │  │  ├─ 📁 entity/       (엔티티: member, question, answer, report)
   │  │  ├─ 📁 exception/    (예외 처리)
   │  │  ├─ 📁 Repository/   (JPA Repository)
   │  │  └─ 📁 Service/      (비즈니스 로직)
   │  │
   │  └─ 📁 resources/
   │     ├─ 📁 static/
   │     │  ├─ 📁 css/
   │     │  ├─ 📁 images/
   │     │  └─ 📁 js/
   │     │
   │     └─ 📁 templates/
   │        ├─ 📁 answer/
   │        ├─ 📁 error/
   │        ├─ 📁 layout/
   │        ├─ 📁 main/
   │        ├─ 📁 member/
   │        └─ 📁 question/
   │
   └─ 📁 test/java/com/basic/bootbasic4/
```

<br>

## <div align="center">트러블슈팅</div>

| 문제 | 원인 | 해결방안 |
|---|---|---|
| 답변 작성 시 공백만 입력하면 Thymeleaf 렌더링 중 오류 발생 | `isReported`, `reportedAnswerIds` 변수가 새로 추가됐지만, `AnswerController`에서 검증 실패 시 Model에 담지 않아 발생 | `AnswerController`의 공백 검증 예외 처리 구문에 두 변수를 Model에 추가 |
| 동물 필터 선택 후 검색 시 드롭다운 선택값 초기화 | `th:selected` 조건에서 enum 타입 `pt`와 String 타입 `petType`을 직접 비교해 항상 false | `.name()`으로 두 값을 모두 String으로 맞춰 비교하도록 수정 |
| 게시글·답변 동시 신고 상태에서 게시글 삭제(승인) 시 외래키 참조 오류 | `Report` 엔티티가 `Question`, `Answer`를 참조 중인데 연관데이터를 정리하지 않고 삭제 시도 | `Report`의 Answer·Question 참조를 먼저 null 처리 후, 답변→게시글 순으로 삭제하도록 로직 수정 |
| 관리자가 반려한 신고 답변을 작성자가 삭제 시 에러 발생 | 신고된 답변의 `answer_id`가 `report` 테이블에 외래키로 남아 참조 중 | `AnswerService` 삭제 로직에서 `report` 테이블 참조값을 먼저 null 처리 후 답변 삭제 |
| 동물 필터 "동물 전체" 선택 후 검색 시 500 에러 | "동물 전체" 옵션 값이 빈 문자열로 설정되어 `QuestionPetType.valueOf("")` 호출 시 예외 발생 | "동물 전체" 옵션의 value를 `""`에서 `ALL`로 변경 |

<br>

## <div align="center">개선점</div>

- **사용자 인증 강화 & 정보 신뢰성 확보** : 임의로 설정 가능한 반려동물 양육 기간에 대한 구체적인 인증 프로세스 도입
- **소셜 로그인 도입 확장** : Google OAuth 등 소셜 로그인 연동으로 접근성 및 인증 방식 확장
- **검색 편의성 및 UX 고도화** : 통합 검색 기능에 '인기 검색어', '검색어 자동완성' 추가
- **커뮤니티 활성화와 상호작용 확장** : 대댓글(답변의 답변) 기능, 답변 추천 및 정렬 필터 도입

<br>

## <div align="center">개발 규칙</div>

> ### 폴더/파일명 규칙
- 폴더명 : **kebab-case** (`user-profile`, `utils` 등)
- 로직 / 함수 / css 등 일반 파일명 : **camelCase** (`authController`, `api` 등)
- 컴포넌트 파일명 : **PascalCase** (`UserProfile`, `Home` 등)

<br>

## <div align="center">🌳 팀 협업용 브랜치 전략</div>

> ### 📌 목표
- 충돌 최소화
- 작업 흐름 통일
- 코드 품질 유지

> ### 🧠 핵심 개념
- `develop` = 최신 개발 상태
- `feature` = 내가 작업하는 공간
- 즉, **항상 develop 기준으로 작업**해야 합니다.

> ### 🌳 브랜치 구조
```
main
 └── develop
      ├── feature/login
      ├── feature/signup
      └── feature/post
```

> ### 🔥 develop 최신화 타이밍 (가장 중요)

**1. 작업 시작 전 (무조건)**
> 이거 안 하면 충돌 확률 급상승
```
git checkout develop
git pull origin develop
```

**2. 작업 도중 (하루 1~2번 추천)**
> 다른 사람이 merge 했을 가능성 있음

**3. PR 올리기 직전 (필수 🔥)**
> 최신 코드 기준으로 맞춰야 리뷰 가능

> ### 🔥 브랜치 종류

**1️⃣ main**
- 실제 서비스 배포 코드
- ❗ 직접 작업 금지

**2️⃣ develop**
- 개발 통합 브랜치
- 모든 기능이 여기로 모임

**3️⃣ feature 브랜치**
- 기능 개발용 브랜치
- 네이밍 규칙 : `feature/기능명`

예:
```
feature/login
feature/user-api
feature/post-create
```

> ### 🔄 작업 흐름

**1️⃣ develop 최신화**
```
git checkout develop
git pull origin develop
```

**2️⃣ feature 브랜치 생성**
```
git checkout -b feature/기능명
```

**3️⃣ 작업 후 커밋**
```
git add .
git commit -m "feat: 로그인 기능 추가"
```

**4️⃣ 원격 저장소에 푸시**
```
git push origin feature/기능명
```

**5️⃣ Pull Request 생성**
- base: `develop`
- compare: `feature/기능명`

**6️⃣ 코드 리뷰 후 머지**
- 승인되면 develop으로 merge

> ### ✨ 커밋 메시지 규칙
```
feat: 기능 추가
fix: 버그 수정
refactor: 코드 리팩토링
docs: 문서 수정
chore: 기타 작업
```

예:
```
feat: 회원가입 API 구현
fix: 로그인 에러 수정
```

> ### ⚠️ 충돌(Conflict) 발생 시

**언제 생기냐?**
- 같은 파일을 여러 명이 수정했을 때

**해결 방법**
1. 충돌 파일 확인
2. 코드 수정
3. 다시 commit

💡 팁
- 충돌 나면 혼자 해결하지 말고 공유
- 이해 안 되는 코드 삭제 금지

> ### 🚨 절대 하면 안 되는 것
- ❌ main에서 작업
- ❌ develop에 직접 push
- ❌ pull 안 하고 작업 시작
- ❌ PR 없이 merge

> ### 💡 꿀팁
- 🔥 **PR은 작게 쪼개서** — 1000줄짜리 PR은 리뷰하기 힘들어요
- 🔥 **자주 pull 하기** — 충돌은 "늦게 pull 해서" 생깁니다
- 🔥 **기능 하나 = 브랜치 하나** — 절대 여러 기능을 한 브랜치에 섞지 마세요
- 🔥 **작업 오래 하면 무조건 최신화** — 하루 이상 작업 시 무조건 develop merge

> ### 🧩 상황별 가이드

| 상황 | 대응 |
|---|---|
| 작업 시작 | develop pull → feature 생성 |
| 작업 중인데 누가 merge함 | develop 브랜치 최신화 후 feature 브랜치에서 `git merge develop` |
| PR 올리기 전 | 최신화 안 하면 충돌 발생, 꼭 진행 |
| 충돌 발생 | 당황하지 말고 파일 하나씩 해결, 발생 즉시 팀에 공유 (덮어두면 더 꼬임) |

> ### 🔥 한 줄 핵심
> "작업 시작 전 / 작업 중 / PR 전 → 무조건 develop 최신화"
