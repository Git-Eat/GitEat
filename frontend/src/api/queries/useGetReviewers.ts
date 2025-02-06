import { useQuery } from "react-query";
import { getReviewer } from "../getReviewer";

export const useGetReviewer = () => {
  return useQuery("getReviewers", getReviewer);
};
