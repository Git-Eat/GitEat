package com.giteat.notification.constants;

public final class NotiConstatns {

    private NotiConstatns(){

    }


    public static final String PR_MESSAGE_TEXT = "### Merge Request 등록되었습니다.\n" +
            "\n" +
            "- **등록자:** John Doe  \n" +
            "- **등록시간:** 2025-02-15 14:30:00  \n" +
            "- **제목:** *Fix authentication bug*  \n" +
            "- **URL:** [GitLab 이동하기](https://gitlab.com/example/repo/-/merge_requests/123)\n";

    public static final String PR_MESSAGE_TOP = "### Merge Request 등록되었습니다.  ";

    public static final String COMMENT_MESSAGE_TOP ="### 새로운 댓글이 등록되었습니다.  ";

    public static final String REPLY_MESSAGE_TOP = "### 새로운 대댓글이 등록되었습니다.  ";


    public static final String MESSAGE_TOP = "등록되었습니다.";

    public static final String MESSAGE_WRITTER = "- **등록자:** ";

    public static final String MESSAGE_TITLE = "**MR 제목:** ";

    public static final String MESSAGE_URL = "- **URL:** [GitLab 이동하기]";


}
