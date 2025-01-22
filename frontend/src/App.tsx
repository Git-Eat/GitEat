import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import { Login } from "./pages/login";
import { DashBoard } from "./pages/dashboard";
import { PullRequests } from "./pages/pullRequestList";
import { RepostytoryList } from "./pages/repositoryList";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/dashboard" element={<DashBoard />} />
          <Route path="/pulls" element={<PullRequests />} />
          <Route path="/repos" element={<RepostytoryList />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
