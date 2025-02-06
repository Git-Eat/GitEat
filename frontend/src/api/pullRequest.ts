import authClient from "./authClient";
import { PullRequest } from "./types/PullRequest";
import { Repository } from "./types/Repository";

export const getPullRequests = async (
  repoId: number
): Promise<PullRequest[]> => {
  try {
    const res = await authClient.get(`/pr/${repoId}`);
    return res.data;
  } catch (e: unknown) {
    console.log(e);
    return [];
  }
};

export const getRepsitories = async (): Promise<Repository[]> => {
  try {
    const res = await authClient.get("/repo");
    return res.data;
  } catch (e: unknown) {
    console.log(e);
    return [];
  }
};
