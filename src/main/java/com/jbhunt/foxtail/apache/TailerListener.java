/*
 * This file is here to support the tweaked Tailer.java file
 */
package com.jbhunt.foxtail.apache;

public interface TailerListener
{
	void init(Tailer tailer);

	void fileNotFound();

	void fileRotated();

	void handle(String line);

	void handle(Exception ex);
}