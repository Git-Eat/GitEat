import client from "./client";

const API_BASE = import.meta.env.VITE_API_BASE;
export const login = async (code: string) => {
  try {
    const response = await client.post(API_BASE + "/user/login", code);
    return response.data;
  } catch (error) {
    throw new Error("로그인에 실패했습니다." + error);
  }
};
