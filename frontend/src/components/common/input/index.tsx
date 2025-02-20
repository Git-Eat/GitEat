import React from "react";

interface CustomInputProps {
  value?: string;
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
  type?: string;
  className?: string;
  style?: React.CSSProperties;
  placeholder?: string;
}

export function CustomInput(props: CustomInputProps) {
  // 기본값 할당
  const {
    value = "",
    onChange,
    type = "text",
    className = "",
    style,
    placeholder = "",
  } = props;

  return (
    <div>
      <input
        value={value}
        onChange={onChange}
        type={type}
        className={className}
        style={style}
        placeholder={placeholder}
      />
    </div>
  );
}
