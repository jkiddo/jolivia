package org.dyndns.jkiddo.dmp.chunks.unknown;

import org.dyndns.jkiddo.dmp.chunks.UByteChunk;

import org.dyndns.jkiddo.dmp.IDmapProtocolDefinition.DmapProtocolDefinition;
import org.dyndns.jkiddo.dmp.DMAPAnnotation;

@DMAPAnnotation(type=DmapProtocolDefinition.____, explicitValue = 0x3F3F3F3F)
public class ReqFplay extends UByteChunk
{
	public ReqFplay()
	{
		this(0);
	}

	public ReqFplay(int i)
	{
		super("????", "com.apple.itunes.req-fplay", i);
	}
}
