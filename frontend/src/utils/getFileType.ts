export const getFileType = (path: string) => {
  const tmp = path.split(".");
  return tmp[tmp.length - 1];
};
