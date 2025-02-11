import client from "./client";

export const getTotalCommits = async (repoId: string) => {
  try {
    const res = await client.get(`/statistics/repo/${repoId}/commit`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};
