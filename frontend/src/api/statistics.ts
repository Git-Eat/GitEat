import authClient from "./authClient";

export async function getLighthouseResult(repoId: number) {
  try {
    const res = await authClient.get(`/rest/report/${repoId}`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
}
