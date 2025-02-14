package com.giteat.common.constants;

public final class WebHookConstants {

    //인스턴스화 방지 생성자
    private WebHookConstants() {

    }

    public static final String WEBHOOK_URL = "http://i12b108.p.ssafy.io:8082/api/rest/gitlab/event";

    public static final String MERGE_REQUEST_NAME = "giteat site MergeRequest WebHook";

    public static final String MERGE_REQUEST_DESCRIPTION = "giteat 사이트에서 생성된 mergeRequest용 WebHook입니다.";

    public static final String MERGE_REQUEST_CUSTOM_TEMPLATE = "{\n" +
            "  \"object_kind\": \"merge_request\", \n" +
            "  \"event_type\": \"merge_request\",\n" +
            "  \"user\": {\n" +
            "    \"id\": {{user.id}},\n" +
            "    \"username\": \"{{user.username}}\",\n" +
            "    \"name\": \"{{user.name}}\",\n" +
            "    \"email\": \"{{user.email}}\",\n" +
            "    \"avatar_url\": \"{{user.avatar_url}}\"\n" +
            "  },\n" +
            "  \"project\": {\n" +
            "    \"id\": {{project.id}},\n" +
            "    \"name\": \"{{project.name}}\",\n" +
            "    \"description\": \"{{project.description}}\",\n" +
            "    \"web_url\": \"{{project.web_url}}\",\n" +
            "    \"path_with_namespace\": \"{{project.path_with_namespace}}\",\n" +
            "    \"default_branch\": \"{{project.default_branch}}\"\n" +
            "  },\n" +
            "  \"repository\": {\n" +
            "    \"name\": \"{{repository.name}}\",\n" +
            "    \"url\": \"{{repository.url}}\",\n" +
            "    \"description\": \"{{repository.description}}\",\n" +
            "    \"homepage\": \"{{repository.homepage}}\"\n" +
            "  },\n" +
            "  \"object_attributes\": {\n" +
            "    \"id\": {{object_attributes.id}},\n" +
            "    \"iid\": {{object_attributes.iid}},\n" +
            "    \"title\": \"{{object_attributes.title}}\",\n" +
            "    \"description\": \"{{object_attributes.description}}\",\n" +
            "    \"state\": \"{{object_attributes.state}}\",\n" +
            "    \"created_at\": \"{{object_attributes.created_at}}\",\n" +
            "    \"updated_at\": \"{{object_attributes.updated_at}}\",\n" +
            "    \"target_branch\": \"{{object_attributes.target_branch}}\",\n" +
            "    \"source_branch\": \"{{object_attributes.source_branch}}\",\n" +
            "    \"merge_status\": \"{{object_attributes.merge_status}}\",\n" +
            "    \"last_commit\": {\n" +
            "      \"id\": \"{{object_attributes.last_commit.id}}\",\n" +
            "      \"message\": \"{{object_attributes.last_commit.message}}\",\n" +
            "      \"timestamp\": \"{{object_attributes.last_commit.timestamp}}\",\n" +
            "      \"url\": \"{{object_attributes.last_commit.url}}\",\n" +
            "      \"author\": {\n" +
            "        \"name\": \"{{object_attributes.last_commit.author.name}}\",\n" +
            "        \"email\": \"{{object_attributes.last_commit.author.email}}\"\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"changes\": {\n" +
            "    \"title\": {\n" +
            "      \"previous\": \"{{changes.title.previous}}\",\n" +
            "      \"current\": \"{{changes.title.current}}\"\n" +
            "    },\n" +
            "    \"description\": {\n" +
            "      \"previous\": \"{{changes.description.previous}}\",\n" +
            "      \"current\": \"{{changes.description.current}}\"\n" +
            "    },\n" +
            "    \"target_branch\": {\n" +
            "      \"previous\": \"{{changes.target_branch.previous}}\",\n" +
            "      \"current\": \"{{changes.target_branch.current}}\"\n" +
            "    },\n" +
            "    \"source_branch\": {\n" +
            "      \"previous\": \"{{changes.source_branch.previous}}\",\n" +
            "      \"current\": \"{{changes.source_branch.current}}\"\n" +
            "    }\n" +
            "  }\n" +
            "}";


    public static final String COMMENT_NAME = "giteat site Comment WebHook";

    public static final String COMMENT_DESCRIPTION = "giteat 사이트에서 생성된 Comment용 WebHook입니다.";

    public static final String COMMENT_CUSTOM_TEMPLATE = "{\n" +
            "  \"object_kind\": \"note\",\n" +
            "  \"event_type\": \"note\",\n" +
            "  \"user\": {\n" +
            "    \"id\": \"{{user.id}}\",\n" +
            "    \"name\": \"{{user.name}}\",\n" +
            "    \"username\": \"{{user.username}}\",\n" +
            "    \"avatar_url\": \"{{user.avatar_url}}\",\n" +
            "    \"email\": \"{{user.email}}\"\n" +
            "  },\n" +
            "  \"project_id\": \"{{project_id}}\",\n" +
            "  \"project\": {\n" +
            "    \"id\": \"{{project.id}}\",\n" +
            "    \"name\": \"{{project.name}}\",\n" +
            "    \"description\": \"{{project.description}}\",\n" +
            "    \"web_url\": \"{{project.web_url}}\",\n" +
            "    \"avatar_url\": \"{{project.avatar_url}}\",\n" +
            "    \"git_ssh_url\": \"{{project.git_ssh_url}}\",\n" +
            "    \"git_http_url\": \"{{project.git_http_url}}\",\n" +
            "    \"namespace\": \"{{project.namespace}}\",\n" +
            "    \"visibility_level\": \"{{project.visibility_level}}\",\n" +
            "    \"path_with_namespace\": \"{{project.path_with_namespace}}\",\n" +
            "    \"default_branch\": \"{{project.default_branch}}\",\n" +
            "    \"homepage\": \"{{project.homepage}}\",\n" +
            "    \"url\": \"{{project.url}}\",\n" +
            "    \"ssh_url\": \"{{project.ssh_url}}\",\n" +
            "    \"http_url\": \"{{project.http_url}}\"\n" +
            "  },\n" +
            "  \"repository\": {\n" +
            "    \"name\": \"{{repository.name}}\",\n" +
            "    \"url\": \"{{repository.url}}\",\n" +
            "    \"description\": \"{{repository.description}}\",\n" +
            "    \"homepage\": \"{{repository.homepage}}\"\n" +
            "  },\n" +
            "  \"object_attributes\": {\n" +
            "    \"id\": \"{{object_attributes.id}}\",\n" +
            "    \"iid\": \"{{object_attributes.iid}}\",\n" +
            "    \"note\": \"{{object_attributes.note}}\",\n" +
            "    \"noteable_type\": \"{{object_attributes.noteable_type}}\",\n" +
            "    \"author_id\": \"{{object_attributes.author_id}}\",\n" +
            "    \"created_at\": \"{{object_attributes.created_at}}\",\n" +
            "    \"updated_at\": \"{{object_attributes.updated_at}}\",\n" +
            "    \"project_id\": \"{{object_attributes.project_id}}\",\n" +
            "    \"attachment\": \"{{object_attributes.attachment}}\",\n" +
            "    \"line_code\": \"{{object_attributes.line_code}}\",\n" +
            "    \"commit_id\": \"{{object_attributes.commit_id}}\",\n" +
            "    \"noteable_id\": \"{{object_attributes.noteable_id}}\",\n" +
            "    \"system\": \"{{object_attributes.system}}\",\n" +
            "    \"st_diff\": \"{{object_attributes.st_diff}}\",\n" +
            "    \"action\": \"{{object_attributes.action}}\",\n" +
            "    \"url\": \"{{object_attributes.url}}\"\n" +
            "  },\n" +
            "  \"merge_request\": {\n" +
            "    \"id\": \"{{merge_request.id}}\",\n" +
            "    \"target_branch\": \"{{merge_request.target_branch}}\",\n" +
            "    \"source_branch\": \"{{merge_request.source_branch}}\",\n" +
            "    \"source_project_id\": \"{{merge_request.source_project_id}}\",\n" +
            "    \"author_id\": \"{{merge_request.author_id}}\",\n" +
            "    \"assignee_id\": \"{{merge_request.assignee_id}}\",\n" +
            "    \"title\": \"{{merge_request.title}}\",\n" +
            "    \"created_at\": \"{{merge_request.created_at}}\",\n" +
            "    \"updated_at\": \"{{merge_request.updated_at}}\",\n" +
            "    \"milestone_id\": \"{{merge_request.milestone_id}}\",\n" +
            "    \"state\": \"{{merge_request.state}}\",\n" +
            "    \"merge_status\": \"{{merge_request.merge_status}}\",\n" +
            "    \"target_project_id\": \"{{merge_request.target_project_id}}\",\n" +
            "    \"iid\": \"{{merge_request.iid}}\",\n" +
            "    \"description\": \"{{merge_request.description}}\",\n" +
            "    \"position\": \"{{merge_request.position}}\",\n" +
            "    \"labels\": \"{{merge_request.labels}}\",\n" +
            "    \"source\": {\n" +
            "      \"name\": \"{{merge_request.source.name}}\",\n" +
            "      \"description\": \"{{merge_request.source.description}}\",\n" +
            "      \"web_url\": \"{{merge_request.source.web_url}}\",\n" +
            "      \"avatar_url\": \"{{merge_request.source.avatar_url}}\",\n" +
            "      \"git_ssh_url\": \"{{merge_request.source.git_ssh_url}}\",\n" +
            "      \"git_http_url\": \"{{merge_request.source.git_http_url}}\",\n" +
            "      \"namespace\": \"{{merge_request.source.namespace}}\",\n" +
            "      \"visibility_level\": \"{{merge_request.source.visibility_level}}\",\n" +
            "      \"path_with_namespace\": \"{{merge_request.source.path_with_namespace}}\",\n" +
            "      \"default_branch\": \"{{merge_request.source.default_branch}}\",\n" +
            "      \"homepage\": \"{{merge_request.source.homepage}}\",\n" +
            "      \"url\": \"{{merge_request.source.url}}\",\n" +
            "      \"ssh_url\": \"{{merge_request.source.ssh_url}}\",\n" +
            "      \"http_url\": \"{{merge_request.source.http_url}}\"\n" +
            "    },\n" +
            "    \"detailed_merge_status\": \"{{merge_request.detailed_merge_status}}\"\n" +
            "  }\n" +
            "}\n";
}
