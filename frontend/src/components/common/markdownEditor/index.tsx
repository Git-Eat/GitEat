import React from "react";
import MDEditor from "@uiw/react-md-editor";
import { useState } from "react";

interface MarkdownEditorProps {
  addReview: (value: string) => void;
  submitComment: (value: string) => void;
  onClose: () => void;
}

export function MarkdownEditor({
  submitComment,
  addReview,
  onClose,
}: MarkdownEditorProps) {
  const [category, setCategory] = useState<"comment" | "suggest" | "review">(
    "comment"
  );
  const [value, setValue] = useState<string>("");

  function handleCategory(event: React.ChangeEvent<HTMLSelectElement>) {
    setCategory(event.target.value as "comment" | "suggest" | "review");
  }

  function handleSubmitComment() {
    if (!value.trim()) return alert("내용을 입력해주세요.");
    submitComment(value);
    setValue("");
  }

  function handleStartReview() {
    if (!value.trim()) return alert("내용을 입력해주세요.");
    addReview(value);
    setValue("");
  }

  return (
    <div className="bg-white my-5 p-5 rounded-xl">
      <select
        onChange={handleCategory}
        value={category}
        className="border border-gray-300 mb-3 p-1 rounded-md"
      >
        <option value="comment">comment</option>
        <option value="suggest">suggest</option>
        <option value="review">review</option>
      </select>

      <MDEditor
        value={value}
        onChange={(val) => setValue(val ?? "")}
        preview="live"
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
          onClick={handleStartReview}
          className="px-2 text-white border border-sky-400 bg-sky-400 p-1 rounded-md"
        >
          리뷰추가
        </button>
      </div>
    </div>
  );
}
