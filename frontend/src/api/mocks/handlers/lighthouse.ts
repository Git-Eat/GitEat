import { http, HttpResponse } from "msw";

const lighthouse = [
  {
    lh_id: 1,
    repo_id: 761731,
    branch: "develop",
    PF: 43,
    AB: 100,
    BP: 100,
    SEO: 83,
    FCP: 33.5,
    LCP: 65.2,
    TBT: 490,
    CLS: 0,
    SI: 33.5,
    create_at: "2025-02-12 10:46:39",
  },
  {
    lh_id: 2,
    repo_id: 761731,
    branch: "develop",
    PF: 40,
    AB: 100,
    BP: 100,
    SEO: 83,
    FCP: 33.4,
    LCP: 65.1,
    TBT: 580,
    CLS: 0,
    SI: 33.5,
    create_at: "2025-02-12 10:47:30",
  },
];

const lighthouseHandlers = [
  http.get("*/rest/report/:repoId", () => {
    return HttpResponse.json(lighthouse, { status: 200 });
  }),
];

export default lighthouseHandlers;
