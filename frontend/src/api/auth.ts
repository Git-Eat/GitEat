import client from "./client";

export const login = async (code: string) => {
  try {
    const response = await client.post("/oauth/gitlab/login", { code: code });
    return response.headers["authorization"];
  } catch (error) {
    throw new Error("로그인에 실패했습니다." + error);
  }
};
