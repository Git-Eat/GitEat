import { Navigate, Outlet } from "react-router-dom";
import { Header } from "../../components/common/header";
import { useLoginStore } from "../../store/loginStore";

export function AuthLayout() {
  const { isLogin } = useLoginStore();
  return isLogin ? (
    <div>
      <Header />
      <div className="flex justify-end">
        <main className="w-[calc(100vw-130px)]">
          <Outlet />
        </main>
      </div>
    </div>
  ) : (
    <Navigate to="/" replace />
  );
}
