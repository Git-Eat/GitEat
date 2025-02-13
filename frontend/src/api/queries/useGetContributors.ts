import { useQuery } from "react-query";
import { getContributors } from "../DashBoard";

export const useGetContributors = (repoId: string) => {
  return useQuery(["getContributors", repoId], () => getContributors(repoId), {
    useErrorBoundary: true,
    suspense: true,
    staleTime: 1000 * 60 * 5,
  });
};
