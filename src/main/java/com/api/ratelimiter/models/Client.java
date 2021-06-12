package com.api.ratelimiter.models;

import com.api.ratelimiter.interfaces.IClient;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter public class Client implements IClient {

	private final String clientId;
	private final String clientName;
	private final TokenBucket tokenBucket;
	private final Long timeWindow;
	private final TimeUnit timeUnit;
	private final ScheduledExecutorService cleanUpService;

	public Client(String clientId, String clientName, Long timeWindow, TimeUnit unit) {
		this.clientId = clientId;
		this.clientName = clientName;
		this.tokenBucket = new TokenBucket();
		this.timeUnit = unit;
		this.timeWindow = this.timeUnit.toMillis(timeWindow);
		this.cleanUpService = (ScheduledExecutorService) Executors.newSingleThreadScheduledExecutor()
				.scheduleAtFixedRate(this.cleanUp(), 1, this.timeUnit.toMillis(2L * this.timeWindow),
						TimeUnit.MILLISECONDS);
	}

	@Override public boolean validateRequest(Long timeStamp) {
		synchronized (tokenBucket) {
			List tokenList = this.tokenBucket.getTokenList();
			if (tokenList.size() < this.tokenBucket.getTokenLimit()) {
				tokenList.add(timeStamp);
				return true;
			}
			return false;
		}
	}

	@Override public Runnable cleanUp() {
		return () -> {
			List<Long> tokenList = this.tokenBucket.getTokenList();
			for (Long reqTime : tokenList) {
				if (System.currentTimeMillis() - reqTime >= this.timeWindow)
					tokenList.remove(0);
				else
					break;
			}
		};
	}

}
