import { useQuery } from "react-query";
import { getCommits } from "../pullRequest";

export const useGetCommits = (prId: number, repoId: number) => {
  return useQuery(
    `getCommits${prId}${repoId}`,
    () => getCommits(prId, repoId),
    {
      staleTime: 1000 * 60 * 5,
    }
  );
};
