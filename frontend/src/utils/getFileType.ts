export const getFileType = (path: string) => {
  return path.split("/")[-1];
};
