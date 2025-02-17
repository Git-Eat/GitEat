import authClient from "./authClient";
import { Statistics } from "./types/Statistics";

export async function getLighthouseResult(repoId: number) {
  try {
    const res = await authClient.get(`/rest/report/${repoId}`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
}

export async function addLighthouseResult(
  gitUrl: string,
  frontendPath: string,
  branch: string,
  repoId: string,
  build: string
): Promise<Statistics> {
  try {
    const res = await authClient.post(`/report/lighthouse-pipeline`, {
      gitUrl: gitUrl,
      frontendPath: frontendPath,
      branch: branch,
      repoId: repoId,
      build: build,
    });
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
}

export async function deleteLighthouseResult(repoId: number) {
  try {
    const res = await authClient.delete(`/rest/report/${repoId}`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
}
