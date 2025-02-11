import client from "./client";

export const login = async (code: string) => {
  try {
    const response = await client.post("/oauth/gitlab/login", { code: code });
    console.log(response.headers);
    return response.headers["Authorization"];
  } catch (error) {
    throw new Error("로그인에 실패했습니다." + error);
  }
};
