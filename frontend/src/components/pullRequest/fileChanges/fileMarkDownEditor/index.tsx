import React, { useState } from "react";
import MDEditor from "@uiw/react-md-editor";
import { useFileUpload } from "../../../../hooks/useFileUpload"; // 경로는 실제 위치에 맞게 조정
import { useParams } from "react-router-dom";
import { ChangedFile } from "../../../../api/types/ChangedFile";
import { useCreateFileComment } from "../../../../api/queries/useCreateFileComment";
import { usePRStore } from "../../../../store/pullRequestStore";
import { Reply } from "../../../../api/types/Reply";
type Comment = {
  commentId: number;
  prId: number;
  repoId: number;
  userId: number;
  userName: string;
  avatarUrl: string;
  disId: string;
  content: string;
  commentType: number;
  createAt: string;
  position: {
    baseSha: null | string;
    startSha: null | string;
    headSha: null | string;
    oldPath: string;
    newPath: string;
    positionType: string;
    newLine: number;
    oldLine: number;
    newStartLine: number;
    newEndLine: number;
    oldStartLine: number;
    oldEndLine: number;
    lineRange: {
      start: {
        lineCode: string;
      };
      end: {
        lineCode: string;
      };
    };
  } | null;
  reCommentList: Reply[];
};
interface FileMarkdownEditorProps {
  onClose: () => void;
  newStartLine: number | null;
  newEndLine: number | null;
  oldStartLine: number | null;
  oldEndLine: number | null;
  file: ChangedFile;
  lineType: number;
  comment?: Comment;
}

export function FileMarkDownEditor({
  onClose,
  newStartLine,
  newEndLine,
  oldEndLine,
  oldStartLine,
  file,
  lineType,
}: FileMarkdownEditorProps) {
  const [category, setCategory] = useState<"comment" | "suggest" | "review">(
    "comment"
  );
  const [comment, setComment] = useState("");
  const { baseRepoId, prId } = useParams();
  const { prDetail } = usePRStore();
  const { mutate: addComment } = useCreateFileComment(
    Number(baseRepoId),
    Number(prId)
  );
  const { handleFileDrop, handleDragOver } = useFileUpload(
    Number(baseRepoId),
    setComment
  );

  function handleCategory(event: React.ChangeEvent<HTMLSelectElement>) {
    setCategory(event.target.value as "comment" | "suggest" | "review");
  }

  // const handleSubmitComment = () => {
  //   if (!comment.trim()) return alert("내용을 입력해주세요");
  //   const commentData = {
  //     fileId: file.fileId,
  //     baseSha: prDetail.baseSha,
  //     startSha: prDetail.startSha,
  //     headSha: prDetail.headSha,
  //     oldPath: file.oldPath,
  //     newPath: file.newPath,
  //     oldEndLine: oldEndLine,
  //     newEndLine: newEndLine,
  //     oldStartLine: oldStartLine,
  //     newStartLine: newStartLine,
  //     newOrOld: lineType === 1 ? 2 : 1,
  //     positionType: "text",
  //     body: comment,
  //   };
  //   addComment(commentData);
  //   onClose();
  // };

  const handleAddReview = () => {
    if (!comment.trim()) return alert("내용을 입력해주세요");
    const commentData = {
      fileId: file.fileId,
      baseSha: prDetail.baseSha,
      startSha: prDetail.startSha,
      headSha: prDetail.headSha,
      oldPath: file.oldPath,
      newPath: file.newPath,
      oldEndLine: oldEndLine,
      newEndLine: newEndLine,
      oldStartLine: oldStartLine,
      newStartLine: newStartLine,
      newOrOld: 0,
      positionType: "text",
      body: comment,
    };
    if (lineType === 1) commentData.newOrOld = 2;
    else if (lineType === 2) commentData.newOrOld = 1;
    else commentData.newOrOld = 3;
    addComment(commentData);
    onClose();
  };

  return (
    <div
      className="bg-white rounded-xl box-sizing border p-3"
      onDrop={handleFileDrop}
      onDragOver={handleDragOver}
    >
      <div className="flex gap-[10px] items-center mb-1">
        <select
          onChange={handleCategory}
          value={category}
          className="border border-gray-300 p-1 rounded-md"
        >
          <option value="comment">comment</option>
          <option value="suggest">suggest</option>
          <option value="review">review</option>
        </select>
        <span>
          <span>from</span> {newStartLine} <span>to</span> {newEndLine}
        </span>
      </div>

      <MDEditor
        value={comment}
        onChange={(val) => setComment(val || "")}
        preview="edit"
      />

      <div className="mt-3 text-right">
        <button
          onClick={onClose}
          className="px-2 border mr-2 border-gray-300 p-1 rounded-md"
        >
          취소
        </button>
        {/* <button
          onClick={handleSubmitComment}
          className="px-2 border mr-2 border-gray-300 p-1 rounded-md"
        >
          댓글달기
        </button> */}
        <button
          onClick={handleAddReview}
          className="px-2 text-white border border-sky-400 bg-sky-400 p-1 rounded-md"
        >
          리뷰추가
        </button>
      </div>
    </div>
  );
}
