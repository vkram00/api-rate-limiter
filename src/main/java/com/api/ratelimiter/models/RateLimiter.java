package com.api.ratelimiter.models;

import com.api.ratelimiter.interfaces.IClient;
import com.api.ratelimiter.interfaces.IRateLimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RateLimiter implements IRateLimiter {

	private final ConcurrentHashMap<String, IClient> clients;
	private final ExecutorService limitCheckExec;

	public RateLimiter() {
		clients = new ConcurrentHashMap<>();
		limitCheckExec = Executors.newSingleThreadExecutor();
	}

	@Override public boolean isAllowed(String clientId) {
		return clients.get(clientId).validateRequest(System.currentTimeMillis());
	}

}
