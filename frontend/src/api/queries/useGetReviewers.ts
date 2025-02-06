import { useQuery } from "react-query";
import { getReviewer } from "../getReviewer";

export const useGetReviewer = (repoId: number, prId: number) => {
  return useQuery("getReviewers", () => getReviewer(repoId, prId), {
    suspense: true,
  });
};
