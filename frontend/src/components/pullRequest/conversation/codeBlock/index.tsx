import React, { useEffect, useMemo } from "react";
import { DiffView, DiffModeEnum } from "@git-diff-view/react";
import { generateDiffFile } from "@git-diff-view/file";
import "@git-diff-view/react/styles/diff-view.css";
import { useParams } from "react-router-dom";
import { usePRStore } from "../../../../store/pullRequestStore";
import { useGetRawFile } from "../../../../api/queries/useGetRawFile";
import { getFileType } from "../../../../utils/getFileType";

interface PartialDiffViewerProps {
  minLine: number; // 예: 4
  maxLine: number; // 예: 10
  newPath: string;
  oldPath: string;
}

export const CodeBlock: React.FC<PartialDiffViewerProps> = ({
  minLine,
  maxLine,
  newPath,
  oldPath,
}) => {
  const { baseRepoId, prId } = useParams();
  const { files } = usePRStore();
  const { mutate, data: code } = useGetRawFile(
    Number(baseRepoId),
    Number(prId)
  );
  useEffect(() => {
    mutate(
      files.filter(
        (file) => file.newPath === newPath || file.oldPath === oldPath
      )[0]
    );
  }, []);
  const getDiffFile = () => {
    if (code) {
      const instance = generateDiffFile(
        "oldFileName",
        code.oldCode === null ? "" : code.oldCode,
        "newFileName",
        code.newCode === null ? "" : code.newCode,
        getFileType(code.fileName),
        getFileType(code.fileName)
      );
      instance.init();
      instance.buildSplitDiffLines();
      instance.buildUnifiedDiffLines();
      return instance;
    }
  };

  const diff = useMemo(() => getDiffFile(), [code?.newCode, code?.oldCode]);

  const dynamicStyle = `
    .diff-line:nth-child(-n+${minLine > 1 ? minLine - 1 : 0}),
    .diff-line:nth-child(n+${maxLine < 1 ? maxLine + 1 : ""}) {
      display: none !important;
    }
  `;

  return (
    <div className="w-full border">
      {/* 동적 CSS 삽입 */}
      <style>{dynamicStyle}</style>
      <DiffView
        diffFile={diff}
        diffViewTheme="light"
        diffViewHighlight={true}
        diffViewMode={DiffModeEnum.SplitGitLab}
        diffViewWrap={true}
      />
    </div>
  );
};
