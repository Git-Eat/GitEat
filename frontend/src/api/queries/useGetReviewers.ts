import { useQuery } from "react-query";
import { getReviewer } from "../reviewer";

export const useGetReviewer = (repoId: number, prId: number) => {
  return useQuery("getReviewers", () => getReviewer(repoId, prId));
};
