package com.tylermurry.foxtail;

import com.tylermurry.foxtail.apache.Tailer;
import com.tylermurry.foxtail.config.FoxTailConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import static com.tylermurry.foxtail.config.ConfigurationAdapter.getConfiguration;
import static com.tylermurry.foxtail.tailer.TailerUtil.createTailers;
import static com.tylermurry.foxtail.tailer.TailerUtil.initializerTailers;

@Slf4j
public class Main
{
	private static final String DEFAULT_CONFIG_LOCATION = "foxtail.config";

	public static void main(String[] args)
	{
		try
		{
			log.info("Starting Foxtail");

			log.info("Getting configuration");
			Map<String, FoxTailConfiguration> configuration = getConfiguration(DEFAULT_CONFIG_LOCATION);

			log.info("Creating tailers based on the provided configuration");
			List<Tailer> tails = createTailers(configuration);

			log.info("Initializing tailers");
			initializerTailers(tails);

			log.info("*** Foxtail started and active ***");

			// Put the main thread to sleep, indefinitely
			while (true)
			{
				Thread.sleep(10000000);
			}
		}
		catch (Exception e)
		{
			log.error("There was an error in the main thread", e);
			Thread.currentThread().interrupt();
		}
	}
}
