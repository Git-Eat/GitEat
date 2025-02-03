package com.giteat.webHook.gitLab.service;

import java.util.Map;

public interface GitLabWebHookService {

    void commitEvent(Map<String , Object> body);

    void mergeRequestEvent(Map<String , Object> body);

    void noteEvent(Map<String , Object> body);
}
