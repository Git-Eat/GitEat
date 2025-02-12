import { useQuery } from "react-query";
import { getParticipants } from "../dashBoard";

export const useGetParticipants = (repoId: string) => {
  return useQuery(["getParticipants", repoId], () => getParticipants(repoId), {
    useErrorBoundary: true,
    suspense: true,
    staleTime: 1000 * 60 * 5,
  });
};
