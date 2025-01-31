import ReactDiffViewer, { DiffMethod } from "react-diff-viewer";
import Prism from "prismjs";
import "prismjs/components/prism-javascript"; // JavaScript 언어 정의
import "prismjs/themes/prism-okaidia.css"; // Prism 테마 CSS 불러오기
interface DiffViewerProps {
  oldCode: string;
  newCode: string;
}

export function DiffViewer({ oldCode, newCode }: DiffViewerProps) {
  const highlightSyntax = (str: string) => (
    <pre
      style={{ display: "inline" }}
      dangerouslySetInnerHTML={{
        __html: Prism.highlight(str, Prism.languages.javascript, "javascript"),
      }}
    />
  );
  return (
    <div className="w-full">
      <ReactDiffViewer
        oldValue={oldCode}
        newValue={newCode}
        compareMethod={DiffMethod.WORDS}
        splitView={true}
        renderContent={highlightSyntax}
      />
    </div>
  );
}
