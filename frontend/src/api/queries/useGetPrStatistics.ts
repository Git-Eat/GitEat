import { useQuery } from "react-query";
import { getPrStatistics } from "../DashBoard";

export const useGetPrStatistics = (repoId: string) => {
  return useQuery(["getPrStatistics", repoId], () => getPrStatistics(repoId), {
    useErrorBoundary: true,
    suspense: true,
    staleTime: 1000 * 60 * 5,
  });
};
