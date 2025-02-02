import { DiffView, DiffModeEnum } from "@git-diff-view/react";
import { generateDiffFile } from "@git-diff-view/file";
import "@git-diff-view/react/styles/diff-view.css";
import { useMemo } from "react";
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
    return instance;
  };

  const diffFile = useMemo(() => getDiffFile(), [oldCode, newCode]);

  return (
    <div className="w-full">
      <DiffView
        diffFile={diffFile}
        diffViewAddWidget
        renderWidgetLine={({ onClose }) => {
          return (
            <div
              style={{
                display: "flex",
                border: "1px solid",
                padding: "10px",
                justifyContent: "space-between",
              }}
            >
              123
              <button
                style={{
                  border: "1px solid",
                  borderRadius: "2px",
                  padding: "4px 8px",
                }}
                onClick={onClose}
              >
                close
              </button>
            </div>
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
