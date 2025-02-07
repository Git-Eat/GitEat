import gitLabLogo from "../../../assets/images/gitlab_logo.svg";
import gitHubLogo from "../../../assets/images/github_logo.svg";
import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
  RadioGroup,
  FormControlLabel,
  Radio,
} from "@mui/material";
import { useState } from "react";

const style = {
  position: "absolute",
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
  const [selectedOption, setSelectedOption] = useState("github");
  const [inputValue, setInputValue] = useState("");

  const handleSave = () => {
    console.log("저장된 정보:", {
      platform: selectedOption,
      value: inputValue,
    });

    // API 호출 로직 추가 가능
    closeModal();
  };

  return (
    <Modal
      open={isModalOpen}
      onClose={closeModal}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
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
            GitHub는 프로젝트 URL을, GitLab은 프로젝트 ID를 입력해주세요.
          </Typography>

          {/* 플랫폼 선택 */}
          <RadioGroup
            row
            value={selectedOption}
            onChange={(e) => setSelectedOption(e.target.value)}
            sx={{ mb: 2 }}
          >
            <FormControlLabel
              value="github"
              control={<Radio sx={{ color: "#000" }} />}
              label={
                <Box display="flex" alignItems="center" gap={1}>
                  <img
                    src={gitHubLogo}
                    alt="GitHub Logo"
                    style={{ width: 36, height: 36 }}
                  />
                  <Typography sx={{ color: "#000" }}>GitHub</Typography>
                </Box>
              }
            />
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
            margin="normal"
            label={
              selectedOption === "github"
                ? "프로젝트 URL을 입력해주세요."
                : "프로젝트 ID를 입력해주세요."
            }
            variant="outlined"
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
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
          >
            확인
          </Button>
        </Box>
      </Box>
    </Modal>
  );
}

export default RepositoryAddModal;
