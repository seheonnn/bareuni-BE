### 모든 치과 정보를 한 번에, 바른이

# ✏️ 프로젝트 요약
- 백엔드를 담당하여 커뮤니티 CRUD, 댓글 CRUD, 좋아요 기능, CI/CD 자동화 등에 참여하였습니다.
- 지역별로 치과를 검색하여 치과 정보를 습득할 수 있습니다.
- 치과와의 제휴를 통해 예약 기능을 활성화하여 사용자는 신속하게 예약으로 이어질 수 있습니다.
- 사용자는 커뮤니티를 통해 교정 및 관련 정보를 습득할 수 있습니다.
- 영수증 인증 후기를 통하여 보다 정확한 후기를 참고할 수 있습니다. 

# 🎯 프로젝트 목표 및 역할
- JWT 토큰 및 Spring Security 학습
- [Redis](https://redis.io/)를 이용한 JWT 토큰 관리
- Swagger 및 Postman을 사용한 Api 테스트
- [Amazon AWS, RDS](https://aws.amazon.com/ko/)를 이용한 배포
- Amazon S3를 이용한 이미지 처리
- Inner Class 기반 DTO 관리
- [Docker](https://www.docker.com/) + [Github Actions](https://docs.github.com/ko/actions)를 이용한 CI/CD 자동화
- [ZeroSSL](https://zerossl.com/) 이용하여 SSL 인증서 발급 후 https 적용

# ✨ 트러블슈팅 ✨
- Docker + GitHub Actions 배포 자동화 시 수정 사항 반영 문제 - [블로그](https://seheonnn.tistory.com/4)
- Docker + GitHub Actions 배포 환경 Redis Connection refused 문제 - [블로그](https://seheonnn.tistory.com/5)

# 🛠️ 사용 기술
Front-End : <img src="https://img.shields.io/badge/Swift-FA7343?style=for-the-flat&logo=swift&logoColor=white">
<br>
Back-End : <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-flat&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-flat&logo=spring-boot"> <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-flat&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-flat&logo=redis&logoColor=white"> 
<br>
배포 환경 : <img src="https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-flat&logo=amazonaws&logoColor=white"> <img src="https://img.shields.io/badge/Linux-FCC624?style=for-the-flat&logo=linux&logoColor=black"> <img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-flat&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/Github%20Actions-282a2e?style=for-the-flat&logo=githubactions&logoColor=367cfe">

# 📺 프로젝트 화면 구성

| 메인 화면 | 치과 목록 화면 |
| ------------ | ------------ |
| <img width="500" alt="7" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/1c9f2951-1a3b-4565-947e-5da7133d5466"> | <img width="500" alt="2" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/052714ef-6506-46bd-a346-c5f14448f9fb"> |

|   로그인 화면  |  치과 정보 화면 | 커뮤니티 화면  |  예약 화면 |
| ------------ | ----------- | ----------- | ----------- |
| <img width="500" alt="3" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/d637dd40-a7c2-4378-9583-4a3727a1344d"> | <img width="500" alt="4" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/d774b6cf-01e1-4a10-bb9b-a457cf1a06a9"> | <img width="500" alt="5" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/e7f24a17-4743-4db1-ae98-dbf20108c650"> | <img width="500" alt="6" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/d9b304c4-a4f4-43b6-bb06-0beccbf26c27"> |

| ERD | API |
| ------------ | ------------ |
| <img width="860" alt="ERD" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/a02dc13e-7c9c-49ed-aaf7-0a3db343a8aa"> | <img width="1278" alt="api 명세서" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/a32c67d1-ee62-4d8a-b77d-03a00131fab3"> |

# 📢 프로젝트 홍보 포스터
![포스터2](https://github.com/seheonnn/bareuni-BE/assets/101795921/f1c12a42-7d31-4996-b508-12cd299153e0)
![포스터1](https://github.com/seheonnn/bareuni-BE/assets/101795921/7b39ec07-cf5c-4b99-a34b-bbbc860abe93)


# Bareuni-BE
## Commit Message Convention

|    Type     | Description  |
|:-----------:|---|
|   `Feat`    | 새로운 기능 추가 |
|    `Fix`    | 버그 수정 |
|    `Ci`     | CI관련 설정 수정 |
|   `Docs`    | 문서 (문서 추가, 수정, 삭제) |
|   `Style`   | 스타일 (코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없는 경우) |
| `Refactor`  | 코드 리팩토링 |
|   `Test`    | 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없는 경우) |
|   `Chore`   | 기타 변경사항 (빌드 스크립트 수정 등) |
