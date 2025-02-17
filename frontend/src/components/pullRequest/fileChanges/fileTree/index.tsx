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

const renderTree = (
  node: Node,
  parentId: string = "",
  fileStatusMap: Map<string, number>,
  fileIdMap: Map<string, string>
) => {
  return Array.from(node.next.values()).map((child, index) => {
    // 현재 노드의 전체 경로를 구성 (압축된 경로를 포함)
    const currentPath = parentId + (child.file || "");
    const isFolder = child.next.size > 0;

    // 아이콘 결정: 폴더면 폴더 아이콘, 파일이면 fileStatus 값에 따라 아이콘 결정
    let iconComponent = null;
    if (isFolder) {
      iconComponent = <img src={folder} alt="folder" className="w-[20px]" />;
    } else {
      const status = fileStatusMap.get(currentPath);
      if (status === 1) {
        iconComponent = <img src={add} alt="added" />;
      } else if (status === 2) {
        iconComponent = <img src={minus} alt="modified" />;
      } else if (status === 3) {
        iconComponent = <img src={none} alt="deleted" />;
      } else {
        iconComponent = <img src={file} alt="file" className="w-[20px]" />;
      }
    }

    // 클릭 시, leaf 노드라면 fileId에 해당하는 요소로 스크롤
    const handleClick = (e: React.MouseEvent) => {
      e.preventDefault();
      if (!isFolder) {
        const fileId = fileIdMap.get(currentPath);
        if (fileId) {
          const target = document.getElementById(fileId);
          if (target) {
            target.scrollIntoView({ behavior: "smooth" });
          }
        }
      }
    };

    return (
      <div className="flex" key={currentPath + index}>
        <TreeItem
          className="truncate"
          id={currentPath}
          itemId={currentPath}
          onClick={handleClick}
          label={
            <a className="flex items-center gap-1 font-semibold" href="#">
              {iconComponent}
              <span>{child.file}</span>
            </a>
          }
        >
          {renderTree(child, currentPath + "/", fileStatusMap, fileIdMap)}
        </TreeItem>
      </div>
    );
  });
};

export function FileTree() {
  const { files } = usePRStore();

  // 파일 배열에서 각 파일의 newPath를 키로 fileStatus와 fileId를 저장하는 Map 생성
  const fileStatusMap = new Map<string, number>();
  const fileIdMap = new Map<string, string>();
  files.forEach((file) => {
    fileStatusMap.set(file.newPath, file.fileStatus);
    fileIdMap.set(file.newPath, file.fileId);
  });

  // 파일 경로 배열을 이용해 트리를 생성 후 압축
  const fileTree = compressFileTree(
    getFileTree(files.map((file) => file.newPath))
  );

  return (
    <div>
      <SimpleTreeView>
        {renderTree(fileTree, "", fileStatusMap, fileIdMap)}
      </SimpleTreeView>
    </div>
  );
}
