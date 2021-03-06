package test;

import org.dyndns.jkiddo.dmp.model.Database;
import org.dyndns.jkiddo.service.daap.client.RequestHelper;
import org.dyndns.jkiddo.service.daap.client.Session;

public class TestSession extends Session
{

	public TestSession(final String host, final int port, final String pairingGuid) throws Exception
	{
		super(host, port, pairingGuid);
	}
	
	public TestSession(final String host, final int port) throws Exception
	{
		super(host, port, "0000000000000001");
	}
	
	public TestSession() throws Exception
	{
		super("localhost", 3689, "0000000000000001");
	}
	
	public TestSession(final String host, final int port, final String username, final String password) throws Exception
	{
		super(host, port, username, password);
	}
	
	public Database getTheDatabase()
	{
		return this.database;
	}

	@SuppressWarnings("unchecked")
	public <T> T fire(final String request) throws Exception
	{
		return (T) RequestHelper.requestParsed(String.format("%s" + request, getRequestBase()));
	}
	@SuppressWarnings("unchecked")
	public <T> T fire2() throws Exception
	{
		return (T) RequestHelper.requestParsed(String.format("%s/databases/%d/groups?meta=dmap.itemname,dmap.itemid,dmap.persistentid,daap.songartist,daap.groupalbumcount,daap.songartistid&type=music&group-type=artists&sort=album&include-sort-headers=1&query=('daap.songartist!:'+('com.apple.itunes.extended-media-kind:1','com.apple.itunes.extended-media-kind:32','com.apple.itunes.xid'))&session-id=%s", getRequestBase(), getDatabase().getItemId(), getSessionId()), false);
	}
}
