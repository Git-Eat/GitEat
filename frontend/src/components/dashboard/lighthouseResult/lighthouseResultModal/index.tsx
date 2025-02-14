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
  LinearProgress,
} from "@mui/material";
import React, { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import { useAddLighthouseResult } from "../../../../api/queries/useAddLighthouseResult";
import yarnLogo from "../../../../assets/images/yarn_logo.svg";
import npmLogo from "../../../../assets/images/npm_logo.svg";
import { usePollingResult } from "../../../../api/queries/usePollingResult";

interface LighthouseResultModalProps {
  isModalOpen: boolean;
  closeModal: () => void;
  refetch: () => void;
}

const style = {
  position: "absolute" as const,
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "60%",
  height: "500px",
  bgcolor: "background.paper",
  boxShadow: 24,
  p: 4,
  borderRadius: 2,
  display: "flex",
  flexDirection: "column",
  justifyContent: "space-between",
};

function LighthouseResultModal({
  isModalOpen,
  closeModal,
  refetch,
}: LighthouseResultModalProps) {
  const { repoId } = useParams<{ repoId: string }>();
  const gitUrlRef = useRef<HTMLInputElement>(null);
  const frontendPathRef = useRef<HTMLInputElement>(null);
  const branchRef = useRef<HTMLInputElement>(null);
  const [build, setBuild] = useState("");
  const {
    mutate: addLighthouseResult,
    isLoading,
    isError,
  } = useAddLighthouseResult();
  const { isUpdated, startPolling, stopPolling } = usePollingResult(
    repoId || "",
    20000
  );

  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarSeverity, setSnackbarSeverity] = useState<"info" | "error">(
    "info"
  );

  const handleSave = () => {
    const gitUrl = gitUrlRef.current?.value.trim();
    const frontendPath = frontendPathRef.current?.value.trim();
    const branch = branchRef.current?.value.trim();

    if (!repoId) {
      console.log("repoId가 없습니다.");
      return;
    }
    if (!gitUrl || !frontendPath || !branch) {
      setSnackbarMessage("모든 필드를 입력해주세요.");
      setSnackbarSeverity("error");
      setSnackbarOpen(true);
      return;
    }
    addLighthouseResult({ gitUrl, frontendPath, branch, repoId, build });
    startPolling();
  };

  useEffect(() => {
    if (isUpdated) {
      console.log("test 정보 업데이트 완료");
      setSnackbarMessage("성능 측정 완료");
      setSnackbarSeverity("info");
      setSnackbarOpen(true);
      stopPolling();
      refetch();
      closeModal();
    }
    if (isLoading) {
      setSnackbarMessage("저장 중...");
      setSnackbarSeverity("info");
      setSnackbarOpen(true);
    } else if (isError) {
      setSnackbarMessage("저장에 실패했습니다.");
      setSnackbarSeverity("error");
      setSnackbarOpen(true);
      stopPolling();
    }
  }, [isUpdated, isLoading, isError]);

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
        <Box
          sx={{
            ...style,
            pointerEvents: isLoading ? "none" : "auto",
            position: "relative",
          }}
        >
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
              <Box sx={{ position: "absolute", top: 0, left: 0, right: 0 }}>
                <LinearProgress />
              </Box>
            </Box>
          )}
          <Typography
            id="modal-modal-title"
            variant="h6"
            component="h2"
            sx={{ color: "#000" }}
          >
            프로젝트 통계 추가
          </Typography>
          <Typography variant="body2" sx={{ mb: 2, color: "#6b6b6b" }}>
            프로젝트 정보를 입력해주세요.
          </Typography>
          <RadioGroup
            row
            value={build}
            onChange={(e) => setBuild(e.target.value)}
            sx={{ mb: 2 }}
          >
            <FormControlLabel
              value="npm"
              control={<Radio sx={{ color: "#000" }} />}
              label={
                <Box display="flex" alignItems="center" gap={1}>
                  <img
                    src={npmLogo}
                    alt="npm Logo"
                    style={{ width: 36, height: 36 }}
                  />
                  <Typography sx={{ color: "#000" }}>npm</Typography>
                </Box>
              }
            />
            <FormControlLabel
              value="yarn"
              control={<Radio sx={{ color: "#000" }} />}
              label={
                <Box display="flex" alignItems="center" gap={1}>
                  <img
                    src={yarnLogo}
                    alt="yarn Logo"
                    style={{ width: 36, height: 36 }}
                  />
                  <Typography sx={{ color: "#000" }}>yarn</Typography>
                </Box>
              }
            />
          </RadioGroup>
          <TextField
            fullWidth
            margin="normal"
            label="프로젝트 URL을 입력해주세요"
            variant="outlined"
            inputRef={gitUrlRef}
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
          <TextField
            fullWidth
            margin="normal"
            label="Frontend path를 입력해주세요"
            variant="outlined"
            inputRef={frontendPathRef}
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
          <TextField
            fullWidth
            margin="normal"
            label="브랜치명을 입력해주세요"
            variant="outlined"
            inputRef={branchRef}
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

export default LighthouseResultModal;
