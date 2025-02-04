package com.giteat.webHook.gitLab.service;

import java.util.Map;

public interface GitLabWebHookService {

    void mergeRequestEvent(Map<String , Object> body);

    void noteEvent(Map<String , Object> body);
}
