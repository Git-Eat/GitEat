import { useQuery } from "react-query";
import { getLighthouseResult } from "../statistics";

export const useGetLighthouseResult = (repoId: number) => {
  return useQuery(["lighthouse", repoId], () => getLighthouseResult(repoId));
};
