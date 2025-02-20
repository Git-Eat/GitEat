import { useRef } from "react";
import MDEditor from "@uiw/react-md-editor";
import { useState } from "react";
import { useFileUpload } from "../../../hooks/useFileUpload";

interface MarkdownEditorProps {
  onAddSingleComment: (value: string, category: number) => void;
  onUpdateComment: (value: string, category: number) => void;
  initialValue?: string;
  initialCategory?: number;
  isEditing?: boolean;
  repoId: number;
}

export function MarkdownEditor({
  onAddSingleComment,
  onUpdateComment,
  initialValue = "",
  initialCategory = 0,
  isEditing = false,
  repoId,
}: MarkdownEditorProps) {
  const [category] = useState<number>(initialCategory);
  const [value, setValue] = useState<string>(initialValue);
  const editorRef = useRef<HTMLDivElement>(null);
  const { handleFileDrop, handleDragOver } = useFileUpload(repoId, setValue);

  // function handleCategory(event: React.ChangeEvent<HTMLSelectElement>) {
  //   setCategory(Number(event.target.value) as number);
  // }

  function handleReset() {
    setValue("");
  }

  function handleAddSingleComment() {
    if (!value.trim()) return alert("내용을 입력해주세요.");
    onAddSingleComment(value, category);
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
      {/* <select
        onChange={handleCategory}
        value={category}
        className="border border-gray-300 mb-3 p-1 rounded-md"
      >
        <option value={0}>suggest</option>
        <option value={1}>comment</option>
        <option value={2}>review</option>
      </select> */}
      <MDEditor
        value={value}
        onChange={(val) => setValue(val ?? "")}
        preview="edit"
      />
      <div className="mt-3 text-right">
        <button
          onClick={handleReset}
          className="px-8 border mr-2 border-gray-300 p-1 rounded-2xl hover:bg-stone-100 font-extrabold"
        >
          초기화
        </button>
        {isEditing ? (
          <button
            onClick={handleUpdateComment}
            className="px-8 text-blue-600 border rounded-2xl  bg-black p-1 hover:bg-stone-100 font-extrabold"
          >
            작성
          </button>
        ) : (
          <>
            <button
              onClick={handleAddSingleComment}
              className="px-8 text-blue-600 border rounded-2xl bg-white p-1 hover:bg-stone-100 font-extrabold"
            >
              작성
            </button>
          </>
        )}
      </div>
    </div>
  );
}
