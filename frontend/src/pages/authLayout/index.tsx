import { Outlet } from "react-router-dom";
import { Header } from "../../components/common/header";

export function AuthLayout() {
  return (
    <div className="flex">
      <Header />
      <main>
        <Outlet />
      </main>
    </div>
  );
}
