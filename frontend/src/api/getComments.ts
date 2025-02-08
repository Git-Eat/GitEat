import client from "./client";

export async function getComments(repoId: number, prId: number) {
  try {
    const response = await client.get(`/pr/${repoId}/${prId}/comment`);
    return response.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
}
