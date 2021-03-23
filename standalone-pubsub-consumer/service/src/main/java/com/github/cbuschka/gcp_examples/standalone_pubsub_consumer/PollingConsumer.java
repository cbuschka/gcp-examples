package com.github.cbuschka.gcp_examples.standalone_pubsub_consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.support.AcknowledgeablePubsubMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PollingConsumer
{
	@Value("${PollingConsumer.subscription:spsc-subscription}")
	private String subscription;
	@Autowired
	private PubSubTemplate pubSubTemplate;

	private final ExponentialBackoffPolicy backoffPolicy = new ExponentialBackoffPolicy(100, 10 * 1000);

	@Scheduled(fixedDelay = 100)
	public void poll()
	{
		do
		{
			int sleepMillis = this.backoffPolicy.getSleepMillis();

			if (sleepMillis < 10)
			{
				try
				{
					List<AcknowledgeablePubsubMessage> messages = fetch();
					if (messages.isEmpty())
					{
						log.info("Nothing received.");
						this.backoffPolicy.empty();
					}
					else
					{
						processAndAcknowledge(messages);

						log.info("{} received and processed.", messages.size());
						this.backoffPolicy.success();
					}
				}
				catch (Exception ex)
				{
					log.error("Fetching failed.", ex);
					this.backoffPolicy.failed();
				}
			}
			else
			{
				log.info("Going to sleep for {} milli(s).", sleepMillis);
				return;
			}
		}
		while (true);
	}

	private void processAndAcknowledge(List<AcknowledgeablePubsubMessage> messages)
	{
		for (AcknowledgeablePubsubMessage message : messages)
		{
			log.info("Message is {}.", message.getPubsubMessage().getData().toStringUtf8());
		}

		this.pubSubTemplate.ack(messages);
	}

	private List<AcknowledgeablePubsubMessage> fetch()
	{
		return this.pubSubTemplate.pull(subscription, 10, true);
	}
}
