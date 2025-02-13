import { useQuery } from "react-query";
import { getTotalCommits } from "../DashBoard";

export const useGetTotalCommit = (repoId: string) => {
  return useQuery(["getTotalCommit", repoId], () => getTotalCommits(repoId), {
    useErrorBoundary: true,
    suspense: true,
    staleTime: 1000 * 60 * 5,
  });
};
