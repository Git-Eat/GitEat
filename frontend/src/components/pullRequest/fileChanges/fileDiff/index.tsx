import { Accordion, AccordionSummary } from "@mui/material";
import AccordionDetails from "@mui/material/AccordionDetails";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import { ErrorBoundery } from "../../../common/errorBoundery";
import { DiffViewer } from "../diffViewer";
import { file as dummy } from "../dummy";
interface FileProps {
  file: {
    fileId: string;
    commitId: string;
    repoId: number;
    prId: number;
    fileName: string;
    oldPath: string;
    newPath: string;
    // 1 - 추가, 2 - update, 3 - delete
    fileStatus: number;
    targetBranch: string;
    sourceBranch: string;
  };
}
export function FileDiff({ file }: FileProps) {
  return (
    <Accordion key={file.fileId} defaultExpanded>
      <div className="flex justify-between items-center">
        <AccordionSummary
          expandIcon={<ArrowDropDownIcon />}
          aria-controls="panel1-content"
          id="panel1-header"
        >
          <h2 className="text-lg font-bold">{file.newPath}</h2>
        </AccordionSummary>
      </div>
      <div className="flex mt-4">
        <AccordionDetails>
          <ErrorBoundery>
            <DiffViewer
              oldCode={dummy.oldCode}
              newCode={dummy.newCode}
              comments={dummy.comments}
            />
          </ErrorBoundery>
        </AccordionDetails>
      </div>
    </Accordion>
  );
}
