import { useMutation } from "react-query";

import { getRawFile } from "../pullRequest";
import { ChangedFile } from "../types/ChangedFile";

export const useGetRawFile = (
  repoId: number,
  prId: number,
  file: ChangedFile,
  refType: number
) => {
  return useMutation(`getRawFile${repoId}${prId}${file.fileId}`, () =>
    getRawFile(repoId, prId, file, refType)
  );
};
