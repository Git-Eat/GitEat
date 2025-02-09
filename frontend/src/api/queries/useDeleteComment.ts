import { useMutation } from "react-query";
import { deleteComment } from "../getComments";

export const useUpdateComment = (
  repoId: number,
  prId: number,
  commentId: number
) => {
  return useMutation("deleteComment", () =>
    deleteComment(repoId, prId, commentId)
  );
};
