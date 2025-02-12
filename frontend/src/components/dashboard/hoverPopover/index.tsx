import React, {
  useState,
  useRef,
  useEffect,
  MouseEvent as ReactMouseEvent,
} from "react";
import Popover from "@mui/material/Popover";
import Typography from "@mui/material/Typography";

interface HoverPopoverProps {
  name: string;
  userName: string;
  children: React.ReactNode;
}

const HoverPopover: React.FC<HoverPopoverProps> = ({
  name,
  userName,
  children,
}) => {
  const [anchorEl, setAnchorEl] = useState<HTMLDivElement | null>(null);
  const containerRef = useRef<HTMLDivElement | null>(null);

  const handleMouseEnter = (event: ReactMouseEvent<HTMLDivElement>): void => {
    setAnchorEl(event.currentTarget);
  };

  // 문서 전체에서 마우스 움직임을 감지하여, container 영역 밖으로 나가면 Popover를 닫습니다.
  useEffect(() => {
    const handleDocumentMouseMove = (event: globalThis.MouseEvent) => {
      if (containerRef.current) {
        const rect = containerRef.current.getBoundingClientRect();
        if (
          event.clientX < rect.left ||
          event.clientX > rect.right ||
          event.clientY < rect.top ||
          event.clientY > rect.bottom
        ) {
          setAnchorEl(null);
        }
      }
    };

    document.addEventListener("mousemove", handleDocumentMouseMove);
    return () => {
      document.removeEventListener("mousemove", handleDocumentMouseMove);
    };
  }, []);

  return (
    <div
      ref={containerRef}
      onMouseEnter={handleMouseEnter}
      style={{ display: "inline-block" }}
    >
      {children}
      <Popover
        open={Boolean(anchorEl)}
        anchorEl={anchorEl}
        onClose={() => setAnchorEl(null)}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "center",
        }}
        transformOrigin={{
          vertical: "top",
          horizontal: "center",
        }}
        disableRestoreFocus
      >
        <Typography sx={{ p: 1 }}>
          {name} ({userName})
        </Typography>
      </Popover>
    </div>
  );
};

export default HoverPopover;
