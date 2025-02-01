# 1. Node.js 이미지 사용 (v20.10.0)
FROM node:20.10-alpine AS build

# 환경변수 설정
ARG VITE_GITLAB_CLIENT_ID
ENV VITE_GITLAB_CLIENT_ID=$VITE_GITLAB_CLIENT_ID

ARG VITE_API_BASE
ENV VITE_API_BASE=$VITE_API_BASE

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 패키지 파일 복사 및 종속성 설치
COPY package*.json ./
RUN npm install --legacy-peer-deps

# 4. 소스 코드 복사 및 빌드
COPY . .
RUN npm run build

# 5. Nginx를 사용하여 정적 파일 서빙
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html

# 6. 기본 Nginx 설정 유지
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
