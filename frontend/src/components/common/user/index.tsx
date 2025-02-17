import { useLoginStore } from "../../../store/loginStore";

export function User() {
  const { user } = useLoginStore();
  return (
    <div className="flex justify-center items-center gap-2 px-5 py-2 bg-stone-50 rounded-xl h-[40px]">
      <img
        src={user.avatar_url}
        alt="userProfile"
        className="w-[28px] rounded-full "
      />
      <span className="box border-l border-stone-500 ps-2">
        @{user.username}
      </span>
    </div>
  );
}
