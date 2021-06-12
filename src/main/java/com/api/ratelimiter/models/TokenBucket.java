package com.api.ratelimiter.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class TokenBucket {

	private final int tokenLimit = 100;
	private final CopyOnWriteArrayList<Long> tokenList;

	public TokenBucket(){
		tokenList = new CopyOnWriteArrayList<>();
	}

}
