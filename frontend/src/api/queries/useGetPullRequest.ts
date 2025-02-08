import { useQuery } from "react-query";
import { getPullRequest } from "../pullRequest";

export const useGetPullRequest = (repoId: number, prId: number) => {
  return useQuery(
    ["getPullRequest" + repoId + prId],
    () => getPullRequest(repoId, prId),
    {
      staleTime: 1000 * 60 * 5,
      useErrorBoundary: true,
      suspense: true,
    }
  );
};
