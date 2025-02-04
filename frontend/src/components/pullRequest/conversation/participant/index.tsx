import defaultProfile from "../../../../assets/images/user_profile_2.svg";

export function Participant() {
  return (
    <div className="flex items-center gap-2 py-2">
      <img src={defaultProfile} alt="user profile" className="max-w-6" />
      <p className="text-[12px] font-semibold">Lilyoung</p>
      <img
        src="/src/assets/images/suggest.svg"
        alt="suggest"
        className="max-w-10"
      />
    </div>
  );
}
