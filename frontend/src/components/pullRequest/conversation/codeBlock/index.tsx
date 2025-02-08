import React, { useMemo } from "react";
import { DiffView, DiffModeEnum } from "@git-diff-view/react";
import { generateDiffFile } from "@git-diff-view/file";
import "@git-diff-view/react/styles/diff-view.css";

interface PartialDiffViewerProps {
  oldCode: string;
  newCode: string;
  minLine: number; // 예: 4
  maxLine: number; // 예: 10
}

export const CodeBlock: React.FC<PartialDiffViewerProps> = ({
  oldCode,
  newCode,
  minLine,
  maxLine,
}) => {
  const getDiffFile = () => {
    const instance = generateDiffFile(
      "oldFileName",
      oldCode,
      "newFileName",
      newCode,
      "jsx",
      "jsx"
    );
    instance.init();
    instance.buildSplitDiffLines();
    instance.buildUnifiedDiffLines();
    return instance;
  };

  const diff = useMemo(() => getDiffFile(), [oldCode, newCode]);

  const dynamicStyle = `
    .diff-line:nth-child(-n+${minLine - 1}),
    .diff-line:nth-child(n+${maxLine + 1}) {
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
