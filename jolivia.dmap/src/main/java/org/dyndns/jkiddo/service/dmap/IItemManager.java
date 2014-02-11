package org.dyndns.jkiddo.service.dmap;

import java.util.Collection;

import org.dyndns.jkiddo.dmp.IDatabase;
import org.dyndns.jkiddo.dmp.chunks.VersionChunk;
import org.dyndns.jkiddo.dmp.chunks.media.AuthenticationMethod;

public interface IItemManager
{
	AuthenticationMethod.PasswordMethod getAuthenticationMethod();

	VersionChunk getMediaProtocolVersion();

	VersionChunk getAudioProtocolVersion();

	VersionChunk getPictureProtocolVersion();

	String getDMAPKey();

	long getSessionId(String remoteHost);

	void waitForUpdate();

	long getRevision(String remoteHost, long sessionId);

	Collection<IDatabase> getDatabases();

	IDatabase getDatabase(long databaseId);

	byte[] getItemAsByteArray(long databaseId, long itemId);

}
