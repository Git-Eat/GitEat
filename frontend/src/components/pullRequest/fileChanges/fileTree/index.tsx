import { Box } from "@mui/material";
import { SimpleTreeView, TreeItem } from "@mui/x-tree-view";
import { Node, getFileTree } from "../../../../utils/getTreeStructure";
const files = [
  "src/components/Button.tsx",
  "src/components/Input.tsx",
  "src/utils/helpers.ts",
  "src/index.tsx",
  "public/index.html",
];
const renderTree = (node: Node, parentId: string = "") => {
  return Array.from(node.next.values()).map((child) => (
    <TreeItem
      key={parentId + child.file}
      itemId={parentId + child.file}
      label={child.file!}
    >
      {renderTree(child, parentId + child.file + "/")}
    </TreeItem>
  ));
};
export function FileTree() {
  const fileTree = getFileTree(files);
  return (
    <Box>
      <SimpleTreeView>{renderTree(fileTree)}</SimpleTreeView>
    </Box>
  );
}
