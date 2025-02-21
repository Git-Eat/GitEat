import React from "react";

interface CustomButtonProps {
  label?: string;
  onClick?: (event: React.MouseEvent<HTMLButtonElement>) => void;
  type?: "button" | "submit" | "reset";
  className?: string;
  style?: React.CSSProperties;
}

export function CustomButton(props: CustomButtonProps) {
  // 기본값을 직접 할당하는 방식으로 처리합니다.
  const {
    label = "Click Me",
    onClick,
    type = "button",
    className = "",
    style,
  } = props;

  return (
    <div>
      <button type={type} onClick={onClick} className={className} style={style}>
        {label}
      </button>
    </div>
  );
}
