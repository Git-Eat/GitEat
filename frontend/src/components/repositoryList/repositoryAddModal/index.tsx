import gitLabLogo from "../../../assets/images/gitlab_logo.svg";
import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
  RadioGroup,
  FormControlLabel,
  Radio,
  Snackbar,
  Alert,
  // CircularProgress,
  LinearProgress, // 원한다면 LinearProgress도 사용 가능합니다.
} from "@mui/material";
import React, { useEffect, useRef, useState } from "react";
import { useAddRepository } from "../../../api/queries/useAddRepository";

const style = {
  position: "absolute" as const,
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "60%",
  height: "400px",
  bgcolor: "background.paper",
  boxShadow: 24,
  p: 4,
  borderRadius: 2,
  display: "flex",
  flexDirection: "column",
  justifyContent: "space-between",
};

function RepositoryAddModal({
  isModalOpen,
  closeModal,
}: {
  isModalOpen: boolean;
  closeModal: () => void;
}) {
  const [selectedOption, setSelectedOption] = useState("gitlab");
  const repoId = useRef<HTMLInputElement>(null);
  const { mutate: addRepository, isLoading, isError } = useAddRepository();

  // Snackbar 관련 상태
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarSeverity, setSnackbarSeverity] = useState<"info" | "error">(
    "info"
  );
  const [inputEror, setError] = useState(false);

  const handleSave = () => {
    if (repoId.current?.value.trim()) {
      addRepository(Number(repoId.current.value));
    } else {
      setError(true);
    }
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
      closeModal();
    }
  }, [isLoading, isError]);

  const handleSnackbarClose = (
    _event?: React.SyntheticEvent | Event,
    reason?: string
  ) => {
    if (reason === "clickaway") return;
    setSnackbarOpen(false);
  };

  return (
    <div className="Pretendard-Regular">
      <Modal
        open={isModalOpen}
        onClose={closeModal}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        {/* 모달 Box에 pointerEvents를 적용하여 로딩 혹은 에러 시 내부 클릭을 막습니다. */}
        <Box
          className="font-pretendard"
          sx={{
            ...style,
            pointerEvents: isLoading ? "none" : "auto",
            position: "relative",
            fontFamily: "Pretendard-Regular",
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
              프로젝트 추가하기
            </Typography>
            <Typography variant="body2" sx={{ mb: 2, color: "#6b6b6b" }}>
              프로젝트 ID를 입력해주세요.
            </Typography>

            {/* 플랫폼 선택 */}
            <RadioGroup
              row
              value={selectedOption}
              onChange={(e) => setSelectedOption(e.target.value)}
              sx={{ mb: 2 }}
            >
              <FormControlLabel
                value="gitlab"
                control={<Radio sx={{ color: "#000" }} />}
                label={
                  <Box display="flex" alignItems="center" gap={1}>
                    <img
                      src={gitLabLogo}
                      alt="GitLab Logo"
                      style={{ width: 36, height: 36 }}
                    />
                    <Typography sx={{ color: "#000" }}>GitLab</Typography>
                  </Box>
                }
              />
            </RadioGroup>

            {/* 입력 필드 */}
            <TextField
              fullWidth
              error={inputEror}
              margin="normal"
              label={
                selectedOption === "github"
                  ? "프로젝트 URL을 입력해주세요."
                  : "프로젝트 ID를 입력해주세요."
              }
              variant="outlined"
              inputRef={repoId}
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
                color: "#fff",
                "&:hover": { backgroundColor: "#333" },
              }}
              onClick={handleSave}
              disabled={isLoading}
            >
              확인
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
    </div>
  );
}

export default RepositoryAddModal;
