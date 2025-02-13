import authClient from "./authClient";

import {
  CommentStatistics,
  Contributors,
  Participants,
  PRStatistics,
} from "./types/DashBoard";

export const getTotalCommits = async (repoId: string) => {
  try {
    const res = await authClient.get(`/statistics/repo/${repoId}/commit`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const getParticipants = async (
  repoId: string
): Promise<Participants> => {
  try {
    const res = await authClient.get(`/statistics/repo/${repoId}/participants`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const getPrStatistics = async (
  repoId: string
): Promise<PRStatistics> => {
  try {
    const res = await authClient.get(`/statistics/repo/${repoId}/pr`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const getCommentStatistics = async (
  repoId: string
): Promise<CommentStatistics> => {
  try {
    const res = await authClient.get(`/statistics/repo/${repoId}/comment`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const getContributors = async (
  repoId: string
): Promise<Contributors> => {
  try {
    const res = await authClient.get(`/statistics/repo/${repoId}/contributors`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Errorr");
  }
};
