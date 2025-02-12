import { useQuery } from "react-query";
import { getCommentStatistics } from "../DashBoard";

export const useGetCommentStatistics = (repoId: string) => {
  return useQuery(
    ["getCommentStatistics", repoId],
    () => getCommentStatistics(repoId),
    {
      useErrorBoundary: true,
      suspense: true,
      staleTime: 1000 * 60 * 5,
    }
  );
};
