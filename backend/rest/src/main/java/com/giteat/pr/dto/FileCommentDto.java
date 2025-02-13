package com.giteat.pr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileCommentDto {
    private Position position;
    private String body;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Position {
        private String baseSha;
        private String startSha;
        private String headSha;
        private String oldPath;
        private String newPath;
        private String positionType;
        private Integer newLine;
        private Integer oldLine;
        private Integer newStartLine;
        private Integer newEndLine;
        private Integer oldStartLine;
        private Integer oldEndLine;
        private LineRange lineRange;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class LineRange {
            private LineCode start;
            private LineCode end;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class LineCode {
            private String lineCode;
        }
    }
}
