import { Participant } from "../participant";

export function ParticipantList() {
  return (
    <div className="bg-white my-5 p-5 rounded-xl">
      <h1 className="text-[18px] font-semibold pb-3">리뷰에 참여한 사람들</h1>
      <Participant />
    </div>
  );
}
