import { useQuery } from "react-query";
import { getReviewer } from "../reviewer";

export const useGetReviewer = () => {
  return useQuery("getReviewers", getReviewer);
};
