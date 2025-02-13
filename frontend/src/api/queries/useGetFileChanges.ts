import { useQuery } from "react-query";
import { getFileChanges } from "../pullRequest";

export const useGetFileChanges = (repoId: number, prId: number) => {
  return useQuery(
    `getFileChanges${repoId}${prId}`,
    () => getFileChanges(repoId, prId),
    {
      staleTime: 1000 * 60 * 5,
      suspense: true,
      useErrorBoundary: true,
    }
  );
};
