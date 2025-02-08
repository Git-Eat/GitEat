import { useQuery } from "react-query";
import { getComments } from "../getComments";

export const useGetComments = (repoId: number, prId: number) => {
  return useQuery("getComments", () => getComments(repoId, prId));
};
