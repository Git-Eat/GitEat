import { useQuery } from "react-query";
import { getComments } from "../comment";

export const useGetComments = (repoId: number, prId: number) => {
  return useQuery(["comments", repoId, prId], () => getComments(repoId, prId));
};
