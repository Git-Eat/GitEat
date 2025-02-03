import { DiffView, DiffModeEnum } from "@git-diff-view/react";
import { generateDiffFile } from "@git-diff-view/file";
import "@git-diff-view/react/styles/diff-view.css";
import { useMemo } from "react";
import { MarkdownEditor } from "../../common/markdownEditor";
interface DiffViewerProps {
  oldCode: string;
  newCode: string;
}

export function DiffViewer({ oldCode, newCode }: DiffViewerProps) {
  const getDiffFile = () => {
    const instance = generateDiffFile(
      "oldFileName",
      oldCode,
      "newFileName",
      newCode,
      "java",
      "java"
    );
    instance.initRaw();
    console.log(instance);
    return instance;
  };

  const diffFile = useMemo(() => getDiffFile(), [oldCode, newCode]);

  return (
    <div className="w-full">
      <DiffView
        diffFile={diffFile}
        diffViewAddWidget
        renderWidgetLine={({ side, lineNumber }) => {
          console.log(side, lineNumber);
          return (
            <MarkdownEditor
              onAddSingleComment={() => {}}
              onStartReview={() => {}}
            />
          );
        }}
        diffViewTheme={"light"}
        diffViewHighlight={true}
        diffViewMode={DiffModeEnum.SplitGitLab}
        diffViewWrap={true}
      />
    </div>
  );
}
