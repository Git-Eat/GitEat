import { useQuery } from "react-query";
import { getPullRequests } from "../pullRequest";

export const useGetPullRequests = (repoId: number) => {
  return useQuery(["getPullRequests", repoId], () => getPullRequests(repoId), {
    staleTime: 1000 * 60 * 5,
    retry: 0,
    suspense: true,
    useErrorBoundary: true,
  });
};
