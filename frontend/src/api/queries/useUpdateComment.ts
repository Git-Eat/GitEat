import { useMutation } from "react-query";
import { updateComment } from "../getComments";

export const useUpdateComment = (
  repoId: number,
  prId: number,
  commentId: number,
  comment: string
) => {
  return useMutation("updateComment", () =>
    updateComment(repoId, prId, commentId, comment)
  );
};
