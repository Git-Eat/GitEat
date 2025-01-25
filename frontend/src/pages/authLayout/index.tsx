import { Outlet } from "react-router-dom";
import { Header } from "../../components/common/header";

export function AuthLayout() {
  return (
    <>
      <Header />
      <main>
        <Outlet />
      </main>
    </>
  );
}
