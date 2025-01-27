import { http, HttpResponse } from "msw";

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

const LOGIN_MOCK = {
  accessToken: "123456",
  refreshToken: "654321",
};
const handler = [
  http.get("https://jsonplaceholder.typicode.com/posts", () => {
    return HttpResponse.json(MOCK_REGION);
  }),
  http.post("http://backendApi:8080/user/login", () => {
    console.log("login mock");
    return HttpResponse.json(LOGIN_MOCK);
  }),
];

export default handler;
