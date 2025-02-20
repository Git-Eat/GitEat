import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import { Login } from "./pages/login";
import { DashBoard } from "./pages/dashboard";
import { PullRequest } from "./pages/pullRequest";
import { RepositoryList } from "./pages/repositoryList";
import { Conversation } from "./components/pullRequest/conversation";
import { AuthLayout } from "./pages/authLayout";
import { Loading } from "./pages/loading";
import { Error } from "./pages/error";
import { QueryClientProvider, QueryClient } from "react-query";
import { FileChanges } from "./components/pullRequest/fileChanges";
import { PullRequestList } from "./pages/pullRequestList";
import { FrontendStatistics } from "./pages/frontendStatistics";
import { DashBoardList } from "./pages/dashBoardList";
import { PerformanceList } from "./pages/performanceList";
import { Guide } from "./pages/guide";
import { Commits } from "./components/pullRequest/commits";
function App() {
  const queryClient = new QueryClient();
  return (
    <>
      <QueryClientProvider client={queryClient}>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Login />} />
            <Route path="/loading" element={<Loading />} />
            <Route path="/error" element={<Error />} />
            <Route element={<AuthLayout />}>
              <Route path="/repos" element={<RepositoryList />} />
              <Route path="/dashboard" element={<DashBoardList />} />
              <Route path="/report" element={<PerformanceList />} />
              <Route path="/guide" element={<Guide />} />
              <Route
                path="/repos/:repoId/dashboard/:owner/:title/"
                element={<DashBoard />}
              />
              <Route
                path="/repos/:repoId/performance/:owner/:title"
                element={<FrontendStatistics />}
              />
              <Route
                path="/repos/:repoId/:owner/:title"
                element={<PullRequestList />}
              />
              <Route
                path="/repos/:baseRepoId/:prId/:owner/:title"
                element={<PullRequest />}
              >
                <Route path="conversation" element={<Conversation />} />
                <Route path="commits" element={<Commits />} />
                <Route path="file-changes" element={<FileChanges />} />
              </Route>
            </Route>
          </Routes>
        </BrowserRouter>
      </QueryClientProvider>
    </>
  );
}

export default App;
