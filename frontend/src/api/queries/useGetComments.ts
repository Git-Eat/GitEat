import { useQuery } from "react-query";
import { getComments } from "../getComments";

export const useGetComments = () => {
  return useQuery("getComments", getComments);
};
