# 1. Node.js 이미지 사용 (v20.10.0)
FROM node:20.10-alpine AS build

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 패키지 파일 복사 및 종속성 설치
COPY package*.json ./
RUN npm install

# 4. 소스 코드 복사 및 빌드
COPY . .
RUN npm run build

# 5. Nginx를 사용하여 정적 파일 서빙
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html

# 6. 기본 Nginx 설정 유지
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
