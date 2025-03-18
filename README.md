![Image](https://github.com/user-attachments/assets/1543de70-eb8d-4231-8b61-5a260d4d3da3)

# 💻 AI코드리뷰 서비스 GIT-EAT

## 1. 프로젝트 개요

📅 **개발 기간**
| 개발기간 | 2025. 01. 06 - 2025. 02. 21 |
| --- | --- |

😎 **팀원 소개**
| 팀원 | 역할 |
| --- | --- |
| 이해루 | 팀장, FE |
| 신지혜 | BE, 디자인 |
| 송용인 | Infra(CI/CD), BE |
| 이다영 | BE |
| 조창훈 | BE, DB |
| 최이화 | FE, 디자인 |

**기획 의도**

코드리뷰에 소모되는 시간을 단축시키기 위한 AI 코드리뷰 서비스

## 2. 기능 소개

**GitLab Outh**
![Image](https://github.com/user-attachments/assets/ff723936-ac64-47e6-adb5-593de2ffe9b1)

- SSAFY GIT(GitLab)을 기반으로 한 Oauth 로그인

**프로젝트 연동**
![Image](https://github.com/user-attachments/assets/28d2ec28-4105-4def-8746-8444fc21fe46)
![Image](https://github.com/user-attachments/assets/945e5fc4-bbb9-4ee7-803e-7cfa30a6c260)

- 프로젝트 ID 등록후, 관련 데이터 연동
  - MergeRequest 이벤트 관련 웹훅 연동
  - 프로젝트 관련 커밋 및 MergeRequest 데이터 연동
  - MergeRequest 이벤트 발생 시, 데이터 동기화

**MergeRequest 상세보기 및 코드리뷰**
![Image](https://github.com/user-attachments/assets/4652c220-36dd-41b2-953f-32ab44b0dba0)

- MergeRequest 관련 데이터 상세보기
  - 코드 변경점
  - MergeRequest 상세
  - 댓글 내역
- MergeRequest 이벤트 발생 시, 변경점 기반 AI 코드 리뷰 등록
- 코드리뷰 시, GitLab 연동
  - GitLab api와 연동하여 댓글 작성 시, GitLab에 자동 등록

**프로젝트 대시보드**
![Image](https://github.com/user-attachments/assets/e0d6bcd4-0552-418e-93d9-77c7467cade4)

- 개인별 커밋 내역, MR 횟수 등 통계 데이터 제공

**성능측정**
![Image](https://github.com/user-attachments/assets/d348219b-d371-4098-b05e-8405397ced18)
![Image](https://github.com/user-attachments/assets/256875cb-cc09-483c-9882-a2010425b578)

- FE 프로젝트 Lighthouse 기반 성능 측정 데이터 제공

## 3. 아키텍처

![Image](https://github.com/user-attachments/assets/e89df3a1-1b69-4369-8463-d1d51793d530)

## FrontEnd / BackEnd 상세

| 분야     | README                                                               |
| -------- | -------------------------------------------------------------------- |
| FrontEnd | [README.md](https://github.com/Git-Eat/GitEat/tree/develop/frontend) |
| BackEnd  | [README.md](https://github.com/Git-Eat/GitEat/tree/develop/backend)  |
