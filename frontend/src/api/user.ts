import authClient from "./authClient";
import { User } from "./types/User";

export const getMe = async (): Promise<User> => {
  try {
    const response = await authClient.get("/oauth/gitlab/userinfo");
    return response.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};
