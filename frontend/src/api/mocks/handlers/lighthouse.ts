import { http, HttpResponse } from "msw";

const lighthouse = {
  lhId: 2,
  repositoryId: 761731,
  branch: "develop",
  performance: 40.0,
  accessibility: 90.0,
  bestPractices: 96.0,
  seo: 83.0,
  fcp: 3.351,
  lcp: 6.833,
  tbt: 1321.5,
  cls: 0.0,
  si: 3.352,
  create_at: "2025-02-12 10:47:30",
};

const lighthouseHandlers = [
  http.get("*/rest/report/:repoId", () => {
    return HttpResponse.json(lighthouse, { status: 200 });
  }),
];

export default lighthouseHandlers;
