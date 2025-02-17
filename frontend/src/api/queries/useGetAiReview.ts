import { useQuery } from "react-query";
import { getAiReview } from "../pullRequest";

export const useGetAiReview = (repoId: number, prId: number) => {
  return useQuery(
    ["getAireview" + repoId + prId],
    () => getAiReview(repoId, prId),
    {
      useErrorBoundary: true,
      suspense: true,
      staleTime: 1000 * 60 * 5,
    }
  );
};
