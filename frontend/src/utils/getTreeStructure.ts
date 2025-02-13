export class Node {
  file: string | null;
  next: Map<string, Node>;

  constructor(file: string | null) {
    this.file = file;
    this.next = new Map();
  }
}

export const getFileTree = (files: string[]): Node => {
  const root = new Node(null);

  files.forEach((filePath) => {
    const parts = filePath.split("/");
    let current = root;

    parts.forEach((part) => {
      if (!current.next.has(part)) {
        current.next.set(part, new Node(part));
      }
      current = current.next.get(part)!;
    });
  });

  return root;
};

export const compressFileTree = (node: Node): Node => {
  node.next.forEach((child, key) => {
    const compressedChild = compressFileTree(child);
    node.next.set(key, compressedChild);
  });

  if (node.file !== null) {
    while (node.next.size === 1) {
      const [childKey, childNode] = Array.from(node.next.entries())[0];
      node.file = node.file + "/" + childKey;

      node.next = childNode.next;
    }
  }
  return node;
};
