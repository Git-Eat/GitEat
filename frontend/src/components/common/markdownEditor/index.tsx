import React, { useRef } from "react";
import MDEditor from "@uiw/react-md-editor";
import { useState } from "react";
import { useFileUpload } from "../../../hooks/useFileUpload";

interface MarkdownEditorProps {
  onAddSingleComment: (value: string, category: number) => void;
  onStartReview: (value: string) => void;
  onUpdateComment: (value: string, category: number) => void;
  initialValue?: string;
  initialCategory?: number;
  isEditing?: boolean;
  repoId: number;
}

export function MarkdownEditor({
  onAddSingleComment,
  onStartReview,
  onUpdateComment,
  initialValue = "",
  initialCategory = 0,
  isEditing = false,
  repoId,
}: MarkdownEditorProps) {
  const [category, setCategory] = useState<number>(initialCategory);
  const [value, setValue] = useState<string>(initialValue);
  const editorRef = useRef<HTMLDivElement>(null);
  const { handleFileDrop, handleDragOver } = useFileUpload(repoId, setValue);

  function handleCategory(event: React.ChangeEvent<HTMLSelectElement>) {
    setCategory(Number(event.target.value) as number);
  }

  function handleReset() {
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

  function handleUpdateComment() {
    if (!value.trim()) return alert("내용을 입력해주세요.");
    if (isEditing && onUpdateComment) {
      onUpdateComment(value, category);
    } else if (onAddSingleComment) {
      onAddSingleComment(value, category);
    }
    setValue("");
  }

  return (
    <div
      ref={editorRef}
      className="bg-white my-5 p-5 rounded-xl"
      onDrop={handleFileDrop}
      onDrag={handleDragOver}
    >
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
          onClick={handleReset}
          className="px-2 border mr-2 border-gray-300 p-1 rounded-md"
        >
          초기화
        </button>
        {isEditing ? (
          <button
            onClick={handleUpdateComment}
            className="px-2 text-white border border-sky-400 bg-sky-400 p-1 rounded-md"
          >
            저장
          </button>
        ) : (
          <>
            <button
              onClick={handleAddSingleComment}
              className="px-2 border mr-2 border-gray-300 p-1 rounded-md"
            >
              제출
            </button>
            <button
              onClick={handleStartReview}
              className="px-2 text-white border border-sky-400 bg-sky-400 p-1 rounded-md"
            >
              리뷰 시작
            </button>
          </>
        )}
      </div>
    </div>
  );
}
