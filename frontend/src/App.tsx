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
              <Route path="/pulls" element={<RepositoryList />} />
              <Route path="/repos/:repoId/dashboard" element={<DashBoard />} />
              <Route
                path="/repos/:repoId/statistics"
                element={<FrontendStatistics />}
              />
              <Route path="/repos/:repoId" element={<PullRequestList />} />
              <Route path="/repos/:baseRepoId/:prId" element={<PullRequest />}>
                <Route path="conversation" element={<Conversation />} />
                <Route path="commits" element={<>commits</>} />
                <Route path="file-changes" element={<FileChanges />} />
              </Route>
              <Route path="wiki" element={<>wiki</>} />
            </Route>
          </Routes>
        </BrowserRouter>
      </QueryClientProvider>
    </>
  );
}

export default App;
