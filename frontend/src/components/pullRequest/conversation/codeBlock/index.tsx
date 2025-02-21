import React, { useMemo } from "react";
import "@git-diff-view/react/styles/diff-view.css";
import { Link, useParams } from "react-router-dom";
import { usePRStore } from "../../../../store/pullRequestStore";

interface PartialDiffViewerProps {
  minLine: number; // 예: 4
  maxLine: number; // 예: 10
  newPath: string;
  oldPath: string;
  fileId: string;
}

export const CodeBlock: React.FC<PartialDiffViewerProps> = ({
  fileId,
  minLine,
  maxLine,
}) => {
  const { baseRepoId, prId, owner, title } = useParams();
  const { files } = usePRStore();
  const file = useMemo(() => {
    return files.filter((file) => file.fileId === fileId)[0];
  }, [fileId]);

  return (
    file && (
      <Link to={`/repos/${baseRepoId}/${prId}/${owner}/${title}/file-changes`}>
        <div className="w-full hover:bg-gray-100 py-5 rounded-xl px-2 mb-5">
          {file.fileName} {Math.max(minLine, maxLine)}번째 줄 바로가기
        </div>
      </Link>
    )
  );
};
