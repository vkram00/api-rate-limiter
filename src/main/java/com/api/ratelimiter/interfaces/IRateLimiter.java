package com.api.ratelimiter.interfaces;

public interface IRateLimiter {

	public boolean isAllowed(String clientId);

}
