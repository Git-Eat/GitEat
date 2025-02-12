import React from "react";
import { SimpleTreeView, TreeItem } from "@mui/x-tree-view";
import folder from "../../../../assets/images/folder.svg";
import file from "../../../../assets/images/tree_file.svg";
import add from "../../../../assets/images/plus_box.svg";
import minus from "../../../../assets/images/minus_box.svg";
import none from "../../../../assets/images/box.svg";
import {
  Node,
  compressFileTree,
  getFileTree,
} from "../../../../utils/getTreeStructure";
import { usePRStore } from "../../../../store/pullRequestStore";

// 재귀적으로 트리를 렌더링하며, fileStatusMap을 사용해 파일 상태에 따른 아이콘을 적용
const renderTree = (
  node: Node,
  parentId: string = "",
  fileStatusMap: Map<string, number>
) => {
  return Array.from(node.next.values()).map((child, index) => {
    // 현재 노드의 전체 경로 (압축된 경우에도 전체 경로를 구성)
    const currentPath = parentId + (child.file || "");
    // 자식이 있으면 폴더, 없으면 파일로 판단
    const isFolder = child.next.size > 0;
    let iconComponent = null;

    if (isFolder) {
      // 폴더인 경우: 폴더 아이콘 표시
      iconComponent = <img src={folder} alt="folder" />;
    } else {
      // 파일인 경우: fileStatus 값을 참고하여 아이콘 결정
      const status = fileStatusMap.get(currentPath);
      if (status === 1) {
        iconComponent = <img src={add} alt="added" />;
      } else if (status === 2) {
        iconComponent = <img src={none} alt="modified" />;
      } else if (status === 3) {
        iconComponent = <img src={minus} alt="deleted" />;
      } else {
        iconComponent = <img src={file} alt="file" />;
      }
    }

    return (
      <div className="flex" key={currentPath + index}>
        <TreeItem
          className="truncate"
          itemId={currentPath}
          label={
            <div className="flex items-center gap-1">
              {iconComponent}
              <span>{child.file}</span>
            </div>
          }
        >
          {renderTree(child, currentPath + "/", fileStatusMap)}
        </TreeItem>
      </div>
    );
  });
};

export function FileTree() {
  const { files } = usePRStore();

  // 파일 배열에서 각 파일의 newPath를 키로 fileStatus를 저장하는 Map 생성
  const fileStatusMap = new Map<string, number>();
  files.forEach((file) => {
    fileStatusMap.set(file.newPath, file.fileStatus);
  });

  // 파일 경로 배열로 트리 생성 후 압축 적용
  const fileTree = compressFileTree(
    getFileTree(files.map((file) => file.newPath))
  );

  return (
    <div>
      <SimpleTreeView>{renderTree(fileTree, "", fileStatusMap)}</SimpleTreeView>
    </div>
  );
}
