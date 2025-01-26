# 1. OpenJDK 이미지 사용
FROM openjdk:17-jdk-alpine

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. JAR 파일 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 4. 실행 명령어 설정
EXPOSE 8080
## Docker security 적용 할 때 사용
# ENTRYPOINT ["java", "-jar", "app.jar"] 

## Docker security 적용 하기 전 사용
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8080", "--spring.main.web-application-type=none"]
