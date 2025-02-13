import { http, HttpResponse } from "msw";

const lighthouse = {
  lhId: 2,
  repositoryId: 761731,
  branch: "develop",
  performance: 40,
  accessibility: 100,
  bestPractices: 100,
  seo: 83,
  fcp: 33.4,
  lcp: 65.1,
  tbt: 580,
  cls: 0,
  si: 33.5,
  create_at: "2025-02-12 10:47:30",
};

const lighthouseHandlers = [
  http.get("*/rest/report/:repoId", () => {
    return HttpResponse.json(lighthouse, { status: 200 });
  }),
];

export default lighthouseHandlers;
