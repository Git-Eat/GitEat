import React, { useState } from "react";
import { uploadFile } from "../../api/getComments";

export function useFileUpload(repoId: number) {
  const [comment, setComment] = useState<string>("");
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

  return { comment, setComment, handleFileDrop, handleDragOver };
}
