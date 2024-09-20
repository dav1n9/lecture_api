# lecture_api


## 개발 환경

- `Java 17`
- `SpringBoot 3.x`
- `MySql` , `Redis`
- `JPA`



## 주요 기능

spring security 를 사용하여 인증/인가 구현
-  사용자 권한에 따라 접근을 제어합니다.

Access Token 과 Refresh Token 활용한 로그인
- Refresh Token 은 Redis 에 저장하여 빠른 조회와 관리가 가능합니다.

강사 및 강의 등록 / 조회 기능

강의 좋아요 및 무한 댓글 작성 기능

## API

### [api 명세서](https://documenter.getpostman.com/view/28478318/2sA3kUFMan)

## ERD

<img width="622" alt="erd" src="https://github.com/user-attachments/assets/631cae8c-247b-4567-bf8b-36853572b54c">

## 아키텍처

![image](https://github.com/user-attachments/assets/53f8f4be-0b4e-4862-90ad-b307d1fecec6)
