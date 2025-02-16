import { useMutation, useQuery } from "react-query";
import { addAlarm, deleteAlarm, getAlarm, updateAlarm } from "../alarm";
import { Alarm } from "../types/Alarm";

export const useAddAlarm = (userId: number) => {
  return useMutation<
    Alarm, // 성공 시 반환 타입
    Error, // 에러 타입
    { repoId: number; url: string } // mutation 변수 타입
  >("addAlarm", ({ repoId, url }) => addAlarm(repoId, userId, url));
};

export const useGetAlarm = (repoId: number, userId: number) => {
  return useQuery<Alarm, Error>(
    ["getAlarm", repoId, userId],
    ({ queryKey }) => {
      const [, repoId, userId] = queryKey;
      return getAlarm(repoId as number, userId as number);
    },
    {
      enabled: false,
    }
  );
};

export const useUpdateAlarm = (userId: number) => {
  return useMutation<
    Alarm, // 성공 시 반환 타입
    Error, // 에러 타입
    { repoId: number; url: string } // mutation 변수 타입
  >("updateAlarm", ({ repoId, url }) => updateAlarm(repoId, userId, url));
};

export const useDeletelarm = (userId: number) => {
  return useMutation<
    Alarm, // 성공 시 반환 타입
    Error, // 에러 타입
    { repoId: number } // mutation 변수 타입
  >("deleteAlarm", ({ repoId }) => deleteAlarm(repoId, userId));
};
