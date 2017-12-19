package br.com.andrei.security;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptService {

	private final int MAX_ATTEMPT = 3;

	private LoadingCache<String, Integer> attemptsCache;

	public LoginAttemptService() {
		super();
		attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}

				});
	}
	
	public void loginSucceded(String key) {
		attemptsCache.invalidate(key);
	}

	public void loginFailed(String key) {
		int attempts = 0;
		try {
			attempts = attemptsCache.get(key);
		} catch (Exception e) {
			attempts = 0;
		}
		
		attempts++;
		attemptsCache.put(key, attempts);
	}
	
	public boolean isBlocked(String Key) {
		
		try {
			return attemptsCache.get(Key)>=MAX_ATTEMPT;
		} catch (ExecutionException e) {
			return false;
		}
	}
}
