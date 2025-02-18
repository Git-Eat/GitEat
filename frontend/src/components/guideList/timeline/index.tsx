import circle_line from "../../../assets/images/circle_line.svg";
import circle_full from "../../../assets/images/circle_full.svg";

interface Step {
  id: number;
  title: string;
  description: string;
  color: boolean;
}

interface TimelineProps {
  steps: Step[];
}

export function Timeline({ steps }: TimelineProps) {
  return (
    <>
      {steps?.map((step, index) => (
        <div
          key={step.id}
          className="relative flex items-center pb-10 last:pb-0"
        >
          {index !== steps.length - 1 && (
            <span className="absolute left-6 top-5 h-full w-1 bg-guide-main my-auto"></span>
          )}

          <div className="absolute top-5 -translate-y-1/2">
            <img src={step.color ? circle_full : circle_line} alt="circle" />
            <span
              className={`absolute inset-0 flex items-center justify-center text-md font-bold ${step.color ? "text-white" : "text-guide-main"}`}
            >
              {step.id}
            </span>
          </div>

          <div className="pl-20">
            <h4 className="text-lg font-bold text-guide-main">{step.title}</h4>
            <p className="text-guide-text">{step.description}</p>
          </div>
        </div>
      ))}
    </>
  );
}
