import { useMutation } from "react-query";
import { addLighthouseResult } from "../statistics";
import { Statistics } from "../types/Statistics";

export const useAddLighthouseResult = () => {
  return useMutation(
    ({ gitUrl, frontendPath, branch, repoId, build }: Statistics) =>
      addLighthouseResult(gitUrl, frontendPath, branch, repoId, build),
    {}
  );
};
