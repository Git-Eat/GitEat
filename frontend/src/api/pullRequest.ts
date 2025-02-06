import authClient from "./authClient";
import { PullRequest } from "./types/PullRequest";

export const getPullRequests = async (
  repoId: number
): Promise<PullRequest[]> => {
  try {
    const res = await authClient.get(`/api/pr/${repoId}`);
    return res.data;
  } catch (e: unknown) {
    console.log(e);
    return [];
  }
};
