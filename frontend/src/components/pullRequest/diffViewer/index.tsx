import { DiffView, DiffModeEnum, DiffFile } from "@git-diff-view/react";
import { generateDiffFile } from "@git-diff-view/file";
import "@git-diff-view/react/styles/diff-view.css";
import { useMemo } from "react";
import { FileMarkDownEditor } from "../fileMarkDownEditor";
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
    // console.log(instance);
    return instance;
  };
  const getLines = (side: number, diffFile: DiffFile, lineNumber: number) => {
    if (side === 1) {
      const oldline =
        diffFile.getBundle().splitLeftLines[lineNumber - 1].lineNumber;
      const newline =
        diffFile.getBundle().splitLeftLines[lineNumber - 1].diff?.oldLineNumber;
      const linetype =
        diffFile.getBundle().splitLeftLines[lineNumber - 1].diff?.type;
      console.log(oldline, newline, linetype);
      return { oldline, newline, linetype };
    } else {
      const newline =
        diffFile.getBundle().splitRightLines[lineNumber - 1].lineNumber;
      const oldline =
        diffFile.getBundle().splitRightLines[lineNumber - 1].diff
          ?.oldLineNumber;
      const linetype =
        diffFile.getBundle().splitRightLines[lineNumber - 1].diff?.type;
      console.log(oldline, newline, linetype);
      return { oldline, newline, linetype };
    }
  };

  const diff = useMemo(() => getDiffFile(), [oldCode, newCode]);

  return (
    <div className="w-full border">
      <DiffView
        diffFile={diff}
        diffViewAddWidget
        renderWidgetLine={({ diffFile, side, lineNumber, onClose }) => {
          console.log("side:", side, lineNumber);
          // 0 그대로 , 1 추가, 2 제거
          const { oldline, newline, linetype } = getLines(
            side,
            diffFile,
            lineNumber
          );
          console.log(oldline, newline, linetype);
          return (
            <FileMarkDownEditor
              startLine={linetype === 2 ? oldline : newline}
              endLine={linetype === 2 ? oldline : newline}
              submitComment={() => {}}
              addReview={() => {}}
              onClose={onClose}
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
