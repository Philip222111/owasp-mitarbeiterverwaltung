package ch.bbw.owasp.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {
    private static final int MAX_ATTEMPTS = 5;
    private final Map<String, Integer> attempts = new ConcurrentHashMap<>();

    public void loginFailed(String username) {
        if (username == null || username.isBlank()) return;
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
    }

    public void loginSucceeded(String username) {
        attempts.remove(username);
    }

    public boolean isBlocked(String username) {
        return attempts.getOrDefault(username, 0) >= MAX_ATTEMPTS;
    }
}
