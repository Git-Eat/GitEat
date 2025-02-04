import { Outlet } from "react-router-dom";
import { Header } from "../../components/common/header";

export function AuthLayout() {
  return (
    <div>
      <Header />
      <div className="flex justify-end">
        <main className="w-[calc(100vw-130px)]">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
