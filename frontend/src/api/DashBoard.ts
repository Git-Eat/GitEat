import client from "./client";
import { Participants } from "./types/DashBoard";

export const getTotalCommits = async (repoId: string) => {
  try {
    const res = await client.get(`/statistics/repo/${repoId}/commit`);
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
    const res = await client.get(`/statistics/repo/${repoId}/participants`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};
