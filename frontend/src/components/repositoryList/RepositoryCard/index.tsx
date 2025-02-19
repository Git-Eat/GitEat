import {
  ClickAwayListener,
  MenuItem,
  MenuList,
  Paper,
  Popper,
  Zoom,
} from "@mui/material";
import kebab from "../../../assets/images/kebab.svg";
import { useBooleanState } from "../../../hooks/useBooleanState";
import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useDeleteRepository } from "../../../api/queries/useDeleteRepository";
import { useRepoStore } from "../../../store/repoStore";
interface RepositoryCardProps {
  title: string;
  description: string;
  access: string;
  ownerName: string;
  repoId: number;
  openModal: () => void;
}

function Private() {
  return (
    <span className="px-5 border-yellow-500 border rounded-full text-xs text-yellow-500 flex items-center h-[24px]">
      private
    </span>
  );
}

function Public() {
  return (
    <span className="px-5 border-emerald-500 border rounded-full text-xs text-emerald-500 flex items-center h-[24px]">
      public
    </span>
  );
}

export function RepositoryCard({
  title,
  description,
  access,
  ownerName,
  openModal,
  repoId,
}: RepositoryCardProps) {
  const [isToggle, onToggle, offToggle] = useBooleanState(false);
  const { mutate } = useDeleteRepository(repoId);
  const { setRepo } = useRepoStore();
  const navigation = useNavigate();
  const anchorRef = React.useRef<HTMLButtonElement>(null);
  const onHandler = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    e.stopPropagation();
    onToggle();
  };
  return (
    <Link to={`/repos/${repoId}/${ownerName}/${title}`}>
      <div className=" bg-white rounded-xl p-7 flex justify-between hover:bg-gray-200 cursor-pointer items-top border">
        <div>
          <div className="flex gap-[10px] items-center mb-[10px]">
            <span className="text-xl font-semibold">
              {ownerName} / {title}
            </span>
            {access === "public" ? <Public /> : <Private />}
          </div>
          <span>{description}</span>
        </div>
        <button
          onClick={(e) => onHandler(e)}
          ref={anchorRef}
          className="w-[15px] h-[15px] hover:cursor-pointer flex justify-center items-center"
        >
          <img className="w-[18px] h-[18px]" src={kebab} alt="menu" />
        </button>

        <Popper
          open={isToggle}
          role={undefined}
          placement="bottom-start"
          anchorEl={anchorRef.current}
          transition
          disablePortal
        >
          {({ TransitionProps }) => (
            <Zoom
              {...TransitionProps}
              style={{
                transformOrigin: "left top",
              }}
            >
              <Paper>
                <ClickAwayListener
                  onClickAway={(e) => {
                    e.stopPropagation();
                    e.preventDefault();
                    offToggle();
                  }}
                >
                  <MenuList
                    autoFocusItem={isToggle}
                    id="composition-menu"
                    aria-labelledby="composition-button"
                  >
                    <MenuItem
                      onClick={(e) => {
                        e.stopPropagation();
                        e.preventDefault();
                        mutate();
                        offToggle();
                      }}
                    >
                      레포지토리 등록 해제
                    </MenuItem>

                    <MenuItem
                      onClick={(e) => {
                        e.stopPropagation();
                        e.preventDefault();
                        setRepo(repoId);
                        openModal();
                        offToggle();
                      }}
                    >
                      알림 등록
                    </MenuItem>
                    <MenuItem
                      onClick={(e) => {
                        e.stopPropagation();
                        e.preventDefault();
                        navigation(`${repoId}/dashboard/${ownerName}/${title}`);
                      }}
                    >
                      대시보드
                    </MenuItem>
                    <MenuItem
                      onClick={(e) => {
                        e.stopPropagation();
                        e.preventDefault();
                        navigation(
                          `${repoId}/performace/${ownerName}/${title}`
                        );
                      }}
                    >
                      성능측정
                    </MenuItem>
                  </MenuList>
                </ClickAwayListener>
              </Paper>
            </Zoom>
          )}
        </Popper>
      </div>
    </Link>
  );
}
