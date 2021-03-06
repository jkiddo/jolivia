package org.dyndns.jkiddo.dmp.chunks.media;

import org.dyndns.jkiddo.dmp.chunks.DateChunk;

import org.dyndns.jkiddo.dmp.IDmapProtocolDefinition.DmapChunkDefinition;
import org.dyndns.jkiddo.dmp.DMAPAnnotation;

@DMAPAnnotation(type=DmapChunkDefinition.mstc)
public class UTCTime extends DateChunk
{
	public UTCTime()
	{
		this(0);
	}

	public UTCTime(long i)
	{
		super("mstc", "dmap.utctime", i);
	}
}
