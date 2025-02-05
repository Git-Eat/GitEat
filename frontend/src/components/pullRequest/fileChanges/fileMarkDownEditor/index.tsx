import React, { useState } from "react";
import MDEditor from "@uiw/react-md-editor";

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
  const [comment, setComment] = useState<string>();
  const [category, setCategory] = useState<"comment" | "suggest" | "review">(
    "comment"
  );
  function handleCategory(event: React.ChangeEvent<HTMLSelectElement>) {
    setCategory(event.target.value as "comment" | "suggest" | "review");
  }
  // 추후 에러 핸들링 toast로 구현
  const handleSubmitComment = () => {
    if (!comment?.trim()) return alert("내용을 입력해주세요");
    submitComment(comment);
  };
  const handleAddReview = () => {
    if (!comment?.trim()) return alert("내용을 입력해주세요");
    addReview(comment);
  };
  return (
    <div className="bg-white rounded-xl box-sizing border">
      <div className="flex gap-[10px] items-center">
        <select
          onChange={handleCategory}
          value={category}
          className="border border-gray-300 mb-1 p-1 rounded-md"
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
        onChange={(val) => setComment(val)}
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
