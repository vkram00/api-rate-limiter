package com.api.ratelimiter.interfaces;

import com.api.ratelimiter.models.TokenBucket;

public interface IClient {

	public boolean validateRequest(Long timestamp);

	public  Runnable cleanUp();
}
