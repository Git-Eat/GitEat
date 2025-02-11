import React from "react";
import HoverPopover from "../hoverPopover";

const DUMMY = [
  {
    userId: 22158,
    name: "이하영",
    userName: "lhy23456",
    avatarUrl:
      "https://secure.gravatar.com/avatar/160d57d4c22cf54206e04621aa12d9be6798c9076eaccf4536a4b85fbaa644e0?s=80&d=identicon",
  },
  {
    userId: 22219,
    name: "이해루",
    userName: "gofn080776",
    avatarUrl:
      "https://secure.gravatar.com/avatar/64d76aebe92226f9ea325dc5d35a44327d62594998d76d6905a47b6a0f61ae92?s=80&d=identicon",
  },
];

export function Participants() {
  return (
    <section className="w-full flex-col justify-between px-10 py-5 bg-white rounded-lg">
      <div className="font-bold mb-2">{DUMMY.length} Participants</div>
      <ul className="text-xl font-bold text-green-600 px-5 flex gap-10 ">
        {DUMMY.map((data) => (
          <li key={data.userId} className="w-[27px] rounded-full">
            <HoverPopover name={data.name} userName={data.userName}>
              <img
                src={data.avatarUrl}
                alt={`${data.name} avatar`}
                className="rounded-full"
              />
            </HoverPopover>
          </li>
        ))}
      </ul>
    </section>
  );
}
