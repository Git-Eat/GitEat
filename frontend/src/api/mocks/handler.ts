import { http, HttpResponse } from "msw";
import commentsHandlers from "./handlers/comments";

const MOCK_REGION = [
  {
    id: 1,
    name: "Region 1",
  },
  {
    id: 2,
    name: "Region 2",
  },
  {
    id: 3,
    name: "Region 3",
  },
];

const handler = [
  http.get("https://jsonplaceholder.typicode.com/posts", () => {
    return HttpResponse.json(MOCK_REGION);
  }),
  ...commentsHandlers,
];

export default handler;
