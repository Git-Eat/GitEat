import { SimpleTreeView, TreeItem } from "@mui/x-tree-view";
import { Node, getFileTree } from "../../../../utils/getTreeStructure";
import { usePRStore } from "../../../../store/pullRequestStore";
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
  const { files } = usePRStore();
  const fileTree = getFileTree(files.map((file) => file.newPath));
  return (
    <div>
      <SimpleTreeView>{renderTree(fileTree)}</SimpleTreeView>
    </div>
  );
}
