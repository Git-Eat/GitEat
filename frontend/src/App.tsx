import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import { Login } from "./pages/login";
import { DashBoard } from "./pages/dashboard";
import { PullRequests } from "./pages/pullRequestList";
import { RepositoryList } from "./pages/repositoryList";
import { AuthLayout } from "./pages/authLayout";
import { Loading } from "./pages/loading";
import { Error } from "./pages/error";
import { QueryClientProvider, QueryClient } from "react-query";

function App() {
  const queryClient = new QueryClient();

  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/loading" element={<Loading />} />
          <Route path="/error" element={<Error />} />
          <Route element={<AuthLayout />}>
            <Route path="/repos" element={<RepositoryList />} />
            <Route path="/dashboard" element={<DashBoard />} />
            <Route path="/pulls" element={<PullRequests />}>
              <Route path="conversation" element={<Conversation />} />
              <Route path="commits" element={<>commits</>} />
              <Route path="file-changes" element={<>fileChanges</>} />
            </Route>
            <Route path="/wiki" element={<PullRequests />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </QueryClientProvider>
  );
}

export default App;
