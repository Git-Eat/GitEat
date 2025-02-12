import { Accordion, AccordionSummary } from "@mui/material";
import AccordionDetails from "@mui/material/AccordionDetails";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import { ErrorBoundary } from "../../../common/errorBoundery";
import { DiffViewer } from "../diffViewer";
import { ChangedFile } from "../../../../api/types/ChangedFile";
import { useGetRawFile } from "../../../../api/queries/useGetRawFile";
import { useEffect } from "react";
import { useBooleanState } from "../../../../hooks/useBooleanState";
import { usePRStore } from "../../../../store/pullRequestStore";

interface FileProps {
  repoId: number;
  prId: number;
  file: ChangedFile;
}
export function FileDiff({ repoId, prId, file }: FileProps) {
  const { mutate: getFile, data: rawFile } = useGetRawFile(repoId, prId, file);
  const { comments } = usePRStore();
  console.log(comments);
  const [isExpand, , , setReverse] = useBooleanState(false);
  useEffect(() => {
    if (isExpand) {
      getFile();
    }
  }, [isExpand]);
  return (
    <Accordion
      key={file.fileId}
      expanded={isExpand}
      onChange={() => setReverse()}
    >
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
        {rawFile && (
          <AccordionDetails>
            <ErrorBoundary>
              <DiffViewer
                oldCode={rawFile.oldCode !== null ? rawFile.oldCode : ""}
                newCode={rawFile.newCode !== null ? rawFile.newCode : ""}
                comments={comments.filter((comment) => comment.position)}
                file={file}
              />
            </ErrorBoundary>
          </AccordionDetails>
        )}
      </div>
    </Accordion>
  );
}
