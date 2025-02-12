import { useQuery } from "react-query";
import { getContributors } from "../dashBoard";

export const useGetContributors = (repoId: string) => {
  return useQuery(
    ["getCommentStatistics", repoId],
    () => getContributors(repoId),
    {
      useErrorBoundary: true,
      suspense: true,
      staleTime: 1000 * 60 * 5,
    }
  );
};
