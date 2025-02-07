import React from "react";
import MDEditor from "@uiw/react-md-editor";
import { useState } from "react";

interface MarkdownEditorProps {
  onAddSingleComment: (value: string, category: 0 | 1 | 2) => void;
  onStartReview: (value: string) => void;
}

export function MarkdownEditor({
  onAddSingleComment,
  onStartReview,
}: MarkdownEditorProps) {
  const [category, setCategory] = useState<0 | 1 | 2>(0);
  const [value, setValue] = useState<string>("");

  function handleCategory(event: React.ChangeEvent<HTMLSelectElement>) {
    setCategory(Number(event.target.value) as 0 | 1 | 2);
  }

  function handleCancel() {
    setValue("");
  }

  function handleAddSingleComment() {
    if (!value.trim()) return alert("내용을 입력해주세요.");
    onAddSingleComment(value, category);
    setValue("");
  }

  function handleStartReview() {
    if (!value.trim()) return alert("내용을 입력해주세요.");
    onStartReview(value);
    setValue("");
  }

  return (
    <div className="bg-white my-5 p-5 rounded-xl">
      <select
        onChange={handleCategory}
        value={category}
        className="border border-gray-300 mb-3 p-1 rounded-md"
      >
        <option value={0}>suggest</option>
        <option value={1}>comment</option>
        <option value={2}>review</option>
      </select>

      <MDEditor
        value={value}
        onChange={(val) => setValue(val ?? "")}
        preview="live"
      />

      <div className="mt-3 text-right">
        <button
          onClick={handleCancel}
          className="px-2 border mr-2 border-gray-300 p-1 rounded-md"
        >
          Cancel
        </button>
        <button
          onClick={handleAddSingleComment}
          className="px-2 border mr-2 border-gray-300 p-1 rounded-md"
        >
          Add single comment
        </button>
        <button
          onClick={handleStartReview}
          className="px-2 text-white border border-sky-400 bg-sky-400 p-1 rounded-md"
        >
          Start review
        </button>
      </div>
    </div>
  );
}
