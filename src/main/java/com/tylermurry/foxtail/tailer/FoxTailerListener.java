package com.tylermurry.foxtail.tailer;

import com.tylermurry.foxtail.apache.TailerListenerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class FoxTailerListener extends TailerListenerAdapter
{
	private String groupName;
	private String sourceName;

	public FoxTailerListener(String groupName, String sourceName)
	{
		this.groupName = groupName;
		this.sourceName = sourceName;
	}

	@Override
	public void handle(String line)
	{
		MDC.put("groupName", groupName);
		log.info(sourceName + " -> " + line);
		MDC.remove("groupName");
	}

	@Override
	public void fileNotFound()
	{
		log.warn("Could not find the file for (groupName: " + groupName + ", sourceName: " + sourceName + ")");
	}

	@Override
	public void handle(Exception ex)
	{
		log.error(ex.getMessage(), ex);
	}
}
