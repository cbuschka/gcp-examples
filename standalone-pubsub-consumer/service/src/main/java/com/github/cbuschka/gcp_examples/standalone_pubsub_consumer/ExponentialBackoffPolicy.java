package com.github.cbuschka.gcp_examples.standalone_pubsub_consumer;

public class ExponentialBackoffPolicy
{
	private long lastPollMillis;
	private final int minPollDelayMillis;
	private final int maxPollDelayMillis;
	private int currentPollDelayMillis;

	public ExponentialBackoffPolicy(int minPollDelayMillis, int maxPollDelayMillis)
	{
		this.minPollDelayMillis = minPollDelayMillis;
		this.maxPollDelayMillis = maxPollDelayMillis;
		this.currentPollDelayMillis = minPollDelayMillis;
		this.lastPollMillis = now();
	}

	public int getSleepMillis()
	{
		return Math.max((int) (this.lastPollMillis + this.currentPollDelayMillis - now()), 0);
	}

	public void success()
	{
		this.lastPollMillis = now();
		this.currentPollDelayMillis = 1;
	}

	public void empty()
	{
		this.lastPollMillis = now();
		incCurrentPollDelay();
	}

	public void failed()
	{
		this.lastPollMillis = now();
		incCurrentPollDelay();
	}

	private long now()
	{
		return System.currentTimeMillis();
	}

	private void incCurrentPollDelay()
	{
		this.currentPollDelayMillis = Math.min(maxPollDelayMillis, this.currentPollDelayMillis * 2);
	}
}
