FROM openjdk:17-alpine

# 어플리케이션 소스들만 따로 관리하는 Working Directory 생성
WORKDIR /usr/src/app

# 빌드한 jar 파일을 Docker 컨테이너 내부로 옮겨줌.
COPY ./build/libs/Bareuni-BE-0.0.1-SNAPSHOT.jar Bareuni-BE-0.0.1-SNAPSHOT.jar

COPY src/main/resources/static /app/static

CMD ["java","-jar","Bareuni-BE-0.0.1-SNAPSHOT.jar"]