import { DiffView, DiffModeEnum, DiffFile } from "@git-diff-view/react";
import { generateDiffFile } from "@git-diff-view/file";
import "@git-diff-view/react/styles/diff-view.css";
import { useMemo } from "react";
import { FileMarkDownEditor } from "../fileMarkDownEditor";
import { CommentThread } from "../commentThread";
import { Reply } from "../../../../api/types/Reply";

type Comment = {
  commentId: number;
  prId: number;
  repoId: number;
  userId: number;
  userName: string;
  avatarUrl: string;
  disId: string;
  content: string;
  commentType: number;
  createAt: string;
  position: {
    baseSha: null | string;
    startSha: null | string;
    headSha: null | string;
    oldPath: string;
    newPath: string;
    positionType: string;
    newLine: number;
    oldLine: number;
    newStartLine: number;
    newEndLine: number;
    oldStartLine: number;
    oldEndLine: number;
    lineRange: {
      start: {
        lineCode: string;
      };
      end: {
        lineCode: string;
      };
    };
  } | null;
  reCommentList: Reply[];
};

interface DiffViewerProps {
  oldCode: string;
  newCode: string;
  comments: Comment[];
}

export function DiffViewer({ oldCode, newCode, comments }: DiffViewerProps) {
  console.log("oldCode: " + oldCode);
  console.log("newCode: " + newCode);
  const getDiffFile = () => {
    console.log("code start");
    const instance = generateDiffFile(
      "oldFileName",
      oldCode,
      "newFileName",
      newCode,
      "java",
      "java"
    );
    console.log(instance.getBundle());
    instance.init();
    instance.buildSplitDiffLines();
    instance.buildUnifiedDiffLines();
    return instance;
  };
  const getLinesAndType = (
    side: number,
    diffFile: DiffFile,
    lineNumber: number
  ) => {
    // oldCode 선택한 경우
    if (side === 1) {
      const idx = diffFile
        .getBundle()
        .splitLeftLines.findIndex((item) => item.lineNumber === lineNumber);
      const oldline = diffFile.getBundle().splitLeftLines[idx].lineNumber;
      const newline =
        diffFile.getBundle().splitLeftLines[idx].diff?.newLineNumber;
      const linetype = diffFile.getBundle().splitLeftLines[idx].diff?.type;
      console.log(oldline, newline, linetype);
      return { oldline, newline, linetype };
    } else {
      // Code 선택한 경우
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

  const parseComments = (comments: Comment[]) => {
    const extendData: {
      oldFile: { [key: number]: { data: Comment[] } };
      newFile: { [key: number]: { data: Comment[] } };
    } = {
      oldFile: {},
      newFile: {},
    };
    comments.forEach((comment) => {
      if (comment.position?.newLine !== undefined) {
        const currentComments =
          extendData.newFile[comment.position.newLine]?.data ?? [];
        extendData.newFile = {
          ...extendData.newFile,
          [comment.position.newLine]: {
            data: [...currentComments, comment],
          },
        };
      } else if (comment.position?.oldLine !== undefined) {
        const currentComments =
          extendData.oldFile[comment.position.oldLine]?.data ?? [];
        extendData.oldFile = {
          ...extendData.oldFile,
          [comment.position.oldLine]: {
            data: [...currentComments, comment],
          },
        };
      }
    });
    console.log(extendData);
    return extendData;
  };

  const diff = useMemo(() => getDiffFile(), [oldCode, newCode]);

  return (
    <div className="w-full border">
      <DiffView
        diffFile={diff}
        extendData={parseComments(comments)}
        diffViewAddWidget
        renderExtendLine={({ data }) => {
          if (!data) {
            return null;
          }
          return (
            <div className="border p-2" onClick={() => console.log(data)}>
              {data.map((comment: Comment) => (
                <CommentThread key={comment.commentId} comment={comment} />
              ))}
            </div>
          );
        }}
        renderWidgetLine={({ diffFile, side, lineNumber, onClose }) => {
          console.log("side:", side, lineNumber);
          // 0 그대로 , 1 추가, 2 제거
          const { oldline, newline, linetype } = getLinesAndType(
            side,
            diffFile,
            lineNumber
          );
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
        diffViewMode={DiffModeEnum.Split}
        diffViewWrap={true}
      />
    </div>
  );
}
