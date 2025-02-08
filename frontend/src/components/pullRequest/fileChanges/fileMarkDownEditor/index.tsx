import React, { useState } from "react";
import MDEditor from "@uiw/react-md-editor";
import { useFileUpload } from "../../../../hooks/useFileUpload"; // 경로는 실제 위치에 맞게 조정
import { useParams } from "react-router-dom";

interface FileMarkdownEditorProps {
  addReview: (value: string) => void;
  submitComment: (value: string) => void;
  onClose: () => void;
  startLine: number | undefined | null;
  endLine: number | undefined | null;
}

export function FileMarkDownEditor({
  submitComment,
  addReview,
  onClose,
  startLine,
  endLine,
}: FileMarkdownEditorProps) {
  const [category, setCategory] = useState<"comment" | "suggest" | "review">(
    "comment"
  );

  // 커스텀 훅을 사용하여 파일 업로드 로직과 comment 상태를 관리합니다.
  const { baseRepoId } = useParams();
  const { comment, setComment, handleFileDrop, handleDragOver } = useFileUpload(
    Number(baseRepoId)
  );

  function handleCategory(event: React.ChangeEvent<HTMLSelectElement>) {
    setCategory(event.target.value as "comment" | "suggest" | "review");
  }

  const handleSubmitComment = () => {
    if (!comment.trim()) return alert("내용을 입력해주세요");
    submitComment(comment);
  };

  const handleAddReview = () => {
    if (!comment.trim()) return alert("내용을 입력해주세요");
    addReview(comment);
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
          <span>from</span> {startLine} <span>to</span> {endLine}
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
        <button
          onClick={handleSubmitComment}
          className="px-2 border mr-2 border-gray-300 p-1 rounded-md"
        >
          댓글달기
        </button>
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
