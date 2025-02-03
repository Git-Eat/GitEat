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
    return instance;
  };
  const getLinesAndType = (
    side: number,
    diffFile: DiffFile,
    lineNumber: number
  ) => {
    // oldCode 선택한 경우
    if (side === 1) {
      console.log(diffFile.getBundle().splitLeftLines);
      const idx = diffFile
        .getBundle()
        .splitLeftLines.findIndex((item) => item.lineNumber === lineNumber);
      const oldline = diffFile.getBundle().splitLeftLines[idx].lineNumber;
      const newline =
        diffFile.getBundle().splitLeftLines[idx].diff?.oldLineNumber;
      const linetype = diffFile.getBundle().splitLeftLines[idx].diff?.type;
      console.log(oldline, newline, linetype);
      return { oldline, newline, linetype };
    } else {
      // newCode 선택한 경우
      const idx = diffFile
        .getBundle()
        .splitRightLines.findIndex((item) => item.lineNumber === lineNumber);
      const newline = diffFile.getBundle().splitRightLines[idx].lineNumber;
      const oldline =
        diffFile.getBundle().splitRightLines[idx].diff?.oldLineNumber;
      const linetype = diffFile.getBundle().splitRightLines[idx].diff?.type;
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
          const { oldline, newline, linetype } = getLinesAndType(
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
