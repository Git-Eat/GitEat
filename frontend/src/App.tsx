import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import { Login } from "./pages/login";
import { DashBoard } from "./pages/dashboard";
import { PullRequests } from "./pages/pullRequestList";
import { RepostytoryList } from "./pages/repositoryList";
import { AuthLayout } from "./pages/authLayout";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route element={<AuthLayout />}>
            <Route path="/repos" element={<RepostytoryList />} />
            <Route path="/dashboard" element={<DashBoard />} />
            <Route path="/pulls" element={<PullRequests />} />
            <Route path="/wiki" element={<PullRequests />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
