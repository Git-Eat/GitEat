export const getParsedDate = (date: string) => {
  const createdDate = new Date(date);
  return `${createdDate.getFullYear()}/${createdDate.getMonth() + 1}/${createdDate.getDate()}`;
};
