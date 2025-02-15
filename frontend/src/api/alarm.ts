import authClient from "./authClient";
import { Alarm } from "./types/Alarm";

export const addAlarm = async (
  repoId: number,
  userId: number,
  url: string
): Promise<Alarm> => {
  try {
    const res = await authClient.post("/noti/addurl", {
      repoId: repoId,
      userId: userId,
      notiToken: url,
    });
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const getAlarm = async (
  repoId: number,
  userId: number
): Promise<Alarm> => {
  try {
    const res = await authClient.get(`/noti/geturl/${repoId}/${userId}`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const deleteAlarm = async (
  repoId: number,
  userId: number
): Promise<Alarm> => {
  try {
    const res = await authClient.delete(`/noti/deleteurl/${repoId}/${userId}`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const updateAlarm = async (
  repoId: number,
  userId: number,
  url: string
): Promise<Alarm> => {
  try {
    const res = await authClient.put(`/noti/updateurl`, {
      repoId: repoId,
      userId: userId,
      notiToken: url,
    });
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};
