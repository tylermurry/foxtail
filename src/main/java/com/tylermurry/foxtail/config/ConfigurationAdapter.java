package com.tylermurry.foxtail.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.file.Files.readAllLines;
import static java.nio.file.Paths.get;

@Slf4j
public class ConfigurationAdapter
{
	private static final String CONFIG_DELIMITER = "\\|";
	private static final String COMMENT_PREFIX   = "#";

	public static Map<String, FoxTailConfiguration> getConfiguration(String filePath) throws IOException
	{
		Map<String, FoxTailConfiguration> configGroups = new ConcurrentHashMap<>();

		readAllLines(get(filePath)).stream().filter(configLine -> !configLine.trim().startsWith(COMMENT_PREFIX) && !configLine.trim().isEmpty()).forEach(configLine ->
		{
			String[] configParts = configLine.split(CONFIG_DELIMITER);

			String groupName = configParts[0].trim();
			String sourceName = configParts[1].trim();
			String sourceFileLocation = configParts[2].trim();

			FoxTailConfiguration configuration = findOrCreateConfiguration(configGroups, groupName);
			configuration.getSourceFiles().put(sourceName, sourceFileLocation);

			configGroups.put(groupName, configuration);
		});

		return configGroups;
	}

	private static FoxTailConfiguration findOrCreateConfiguration(Map<String, FoxTailConfiguration> configGroups, String groupName)
	{
		FoxTailConfiguration config = configGroups.get(groupName);

		if (config == null)
		{
			config = FoxTailConfiguration.builder().groupName(groupName).sourceFiles(new HashMap<>()).build();
		}

		return config;
	}
}
