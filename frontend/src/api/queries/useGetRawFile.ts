import { useMutation } from "react-query";

import { getRawFile } from "../pullRequest";
import { ChangedFile } from "../types/ChangedFile";

export const useGetRawFile = (
  repoId: number,
  prId: number,
  file: ChangedFile
) => {
  return useMutation(`getRawFile${repoId}${prId}${file.fileId}`, () =>
    getRawFile(repoId, prId, file)
  );
};
