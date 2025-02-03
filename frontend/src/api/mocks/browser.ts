import { setupWorker } from "msw/browser";
import handler from "./handler";

// 브라우저 환경에서 MSW 설정
export const worker = setupWorker(...handler);
