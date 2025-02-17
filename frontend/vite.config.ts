import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import fs from "fs";

export default defineConfig({
  plugins: [react()],
  server: {
    // 호스트를 명시적으로 설정 (예: localhost 또는 '0.0.0.0')
    host: "localhost",
    https: {
      key: fs.readFileSync("./localhost+2-key.pem"),
      cert: fs.readFileSync("./localhost+2.pem"),
    },
    proxy: {
      "/api": {
        target: "https://i12b108.p.ssafy.io/api",
        changeOrigin: true,
        secure: false, // 백엔드의 self-signed 인증서 사용 시
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
});
