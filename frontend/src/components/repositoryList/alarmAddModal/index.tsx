import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
  Snackbar,
  Alert,
  // CircularProgress,
  LinearProgress, // 원한다면 LinearProgress도 사용 가능합니다.
} from "@mui/material";
import React, { useEffect, useRef, useState } from "react";
import {
  useAddAlarm,
  useDeletelarm,
  useGetAlarm,
} from "../../../api/queries/alarmQueries";
import { useLoginStore } from "../../../store/loginStore";
import { useRepoStore } from "../../../store/repoStore";

const style = {
  position: "absolute" as const,
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "60%",
  height: "250px",
  bgcolor: "background.paper",
  boxShadow: 24,
  p: 4,
  borderRadius: 2,
  display: "flex",
  flexDirection: "column",
  justifyContent: "space-between",
};

export function AlarmAddModal({
  isModalOpen,
  closeModal,
}: {
  isModalOpen: boolean;
  closeModal: () => void;
}) {
  const { user } = useLoginStore();
  const { repoId: selectedId } = useRepoStore();
  const mmurl = useRef<HTMLInputElement>(null);
  const { mutate: addAlarm, isLoading, isError } = useAddAlarm(Number(user.id));
  const { data, refetch } = useGetAlarm(selectedId, Number(user.id));
  const { mutate: deleteAlarm } = useDeletelarm(Number(user.id));
  // Snackbar 관련 상태
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarSeverity, setSnackbarSeverity] = useState<"info" | "error">(
    "info"
  );

  const handleSave = () => {
    if (mmurl.current) {
      addAlarm({ repoId: selectedId, url: mmurl.current.value });
    }
  };

  const handleDelete = () => {
    deleteAlarm({ repoId: selectedId });
  };

  // 로딩 및 에러 상태에 따른 Snackbar 메시지 처리
  useEffect(() => {
    if (isLoading) {
      setSnackbarMessage("저장 중...");
      setSnackbarSeverity("info");
      setSnackbarOpen(true);
    } else if (isError) {
      setSnackbarMessage("저장에 실패했습니다.");
      setSnackbarSeverity("error");
      setSnackbarOpen(true);
    } else {
      setSnackbarOpen(false);
    }
  }, [isLoading, isError]);

  useEffect(() => {
    if (isModalOpen) {
      refetch();
    }
  }, [isModalOpen]);

  const handleSnackbarClose = (
    _event?: React.SyntheticEvent | Event,
    reason?: string
  ) => {
    if (reason === "clickaway") return;
    setSnackbarOpen(false);
  };

  return (
    <>
      <Modal
        open={isModalOpen}
        onClose={closeModal}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        {/* 모달 Box에 pointerEvents를 적용하여 로딩 혹은 에러 시 내부 클릭을 막습니다. */}
        <Box
          sx={{
            ...style,
            pointerEvents: isLoading ? "none" : "auto",
            position: "relative",
          }}
        >
          {/* isLoading일 때 로딩 인디케이터 표시 */}
          {isLoading && (
            <Box
              sx={{
                position: "absolute",
                top: 0,
                left: 0,
                right: 0,
                bottom: 0,
                bgcolor: "rgba(255, 255, 255, 0.7)",
                zIndex: 2,
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              {/* <CircularProgress /> */}
              {/* 혹은 아래와 같이 LinearProgress를 사용해도 됩니다. */}
              <Box sx={{ position: "absolute", top: 0, left: 0, right: 0 }}>
                <LinearProgress />
              </Box>
            </Box>
          )}

          <div>
            {/* 모달 제목 */}
            <Typography
              id="modal-modal-title"
              variant="h6"
              component="h2"
              sx={{ color: "#000" }}
            >
              알림채널 추가하기
            </Typography>
            <TextField
              fullWidth
              margin="normal"
              label={"MatterMost 웹훅 주소를 입력해주세요"}
              variant="outlined"
              inputRef={mmurl}
              value={data?.notiToken}
              focused={data?.notiToken ? true : false}
              sx={{
                "& .MuiOutlinedInput-root": {
                  "& fieldset": {
                    borderColor: "#000",
                  },
                  "&:hover fieldset": {
                    borderColor: "#000",
                  },
                  "&.Mui-focused fieldset": {
                    borderColor: "#000",
                  },
                },
              }}
            />
          </div>

          {/* 버튼 그룹 */}
          <Box sx={{ display: "flex", justifyContent: "flex-end", mt: 3 }}>
            <Button
              onClick={closeModal}
              sx={{ mr: 2, color: "#000", borderColor: "#000" }}
              variant="outlined"
            >
              취소
            </Button>
            <Button
              variant="contained"
              sx={{
                backgroundColor: "#000",
                mr: 2,
                color: "#fff",
                "&:hover": { backgroundColor: "#333" },
              }}
              onClick={handleDelete}
              disabled={isLoading}
            >
              등록해제
            </Button>
            <Button
              variant="contained"
              sx={{
                backgroundColor: "#000",
                color: "#fff",
                "&:hover": { backgroundColor: "#333" },
              }}
              onClick={handleSave}
              disabled={isLoading}
            >
              등록
            </Button>
          </Box>
        </Box>
      </Modal>

      {/* Snackbar를 이용한 Toast 메시지 */}
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={3000}
        onClose={handleSnackbarClose}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert
          onClose={handleSnackbarClose}
          severity={snackbarSeverity}
          sx={{ width: "100%" }}
        >
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </>
  );
}
