package com.jbhunt.foxtail.tailer;

import com.jbhunt.foxtail.apache.Tailer;
import com.jbhunt.foxtail.config.FoxTailConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TailerUtil
{
	public static List<Tailer> createTailers(Map<String, FoxTailConfiguration> configGroups)
	{
		List<Tailer> foxTails = new ArrayList<>();

		for (String configGroup : configGroups.keySet())
		{
			FoxTailConfiguration config = configGroups.get(configGroup);

			foxTails.addAll(config.getSourceFiles().keySet().stream().map(sourceName -> createTailer(config, sourceName)).collect(Collectors.toList()));
		}

		return foxTails;
	}

	public static Tailer createTailer(FoxTailConfiguration config, String sourceName)
	{
		String sourceFile = config.getSourceFiles().get(sourceName);
		String groupName = config.getGroupName();
		long pollDelay = config.getPollDelay();
		boolean tailFromEnd = config.isTailFromEnd();
		boolean reloadFile = config.isReloadFile();

		return new Tailer(new File(sourceFile), new FoxTailerListener(groupName, sourceName), pollDelay, tailFromEnd, reloadFile);
	}


	public static void initializerTailers(List<Tailer> tailers)
	{
		tailers.forEach(t -> new Thread(t).start());
	}
}
