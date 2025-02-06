import client from "./client";

export const login = async (code: string) => {
  try {
    const response = await client.post("api/oauth/gitlab/login", code);
    return response.data;
  } catch (error) {
    console.log(error);
    throw new Error("로그인에 실패했습니다." + error);
  }
};
