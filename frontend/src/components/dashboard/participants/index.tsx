import { useParams } from "react-router-dom";
import { useGetParticipants } from "../../../api/queries/useGetParticipants";
import HoverPopover from "../hoverPopover";

export function Participants() {
  const { repoId } = useParams();
  const { data } = useGetParticipants(repoId as string);
  return (
    <section className="w-full flex-col justify-between px-10 py-5 bg-white rounded-lg">
      <div className="font-bold mb-2">
        {data?.participants.length} Participants
      </div>
      <ul className="text-xl font-bold text-green-600 px-5 flex gap-10 ">
        {data?.participants.map((participant) => (
          <li key={participant.userId} className="w-[27px] rounded-full">
            <HoverPopover
              name={participant.name}
              userName={participant.userName}
            >
              <img
                src={participant.avatarUrl}
                alt={`${participant.name} avatar`}
                className="rounded-full"
              />
            </HoverPopover>
          </li>
        ))}
      </ul>
    </section>
  );
}
