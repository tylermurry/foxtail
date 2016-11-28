package com.tylermurry.foxtail.config;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class FoxTailConfiguration
{
	private String              groupName;
	private Map<String, String> sourceFiles;
	private long                pollDelay;
	private boolean             tailFromEnd;
	private boolean             reloadFile;

	public static class FoxTailConfigurationBuilder
	{
		private long    pollDelay   = 1000;
		private boolean tailFromEnd = true;
		private boolean reloadFile  = true;
	}
}