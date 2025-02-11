import React from "react";
import { uploadFile } from "../../api/comment";

export function useFileUpload(
  repoId: number,
  setComment: (update: (prev: string) => string) => void
) {
  const handleFileDrop = async (event: React.DragEvent<HTMLDivElement>) => {
    event.preventDefault();
    const files = event.dataTransfer.files;
    if (files && files.length > 0) {
      const file = files[0];
      const formData = new FormData();
      formData.append("file", file);
      const response = await uploadFile(repoId, formData);

      const uploadedUrl = response.full_path;
      setComment(
        (prev) =>
          `${prev ? prev + "\n" : ""}![${file.name}](https://lab.ssafy.com/${uploadedUrl})\n`
      );
    }
  };

  const handleDragOver = (event: React.DragEvent<HTMLDivElement>) => {
    event.preventDefault();
  };

  return { handleFileDrop, handleDragOver };
}
