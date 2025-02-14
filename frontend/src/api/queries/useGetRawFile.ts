import { useMutation } from "react-query";

import { getRawFile } from "../pullRequest";
import { ChangedFile } from "../types/ChangedFile";

export const useGetRawFile = (repoId: number, prId: number) => {
  return useMutation(`getRawFile${repoId}${prId}`, (file: ChangedFile) =>
    getRawFile(repoId, prId, file)
  );
};
