/*******************************************************************************
 * Copyright (c) 2013 Jens Kristian Villadsen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Jens Kristian Villadsen - Lead developer, owner and creator
 ******************************************************************************/
package org.dyndns.jkiddo.service.dmap;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

public interface ILibraryResource
{

	@Path("server-info")
	@GET
	public Response serverInfo(@QueryParam("hsgid") String hsgid) throws IOException, SQLException;

	@Path("login")
	@GET
	public Response login(@QueryParam("pairing-guid") String guid, @QueryParam("hasFP") int value, @QueryParam("hsgid") String hsgid) throws IOException;

	@Path("update")
	@GET
	public Response update(@QueryParam("session-id") long sessionId, @QueryParam("revision-number") long revisionNumber, @QueryParam("delta") long delta, @QueryParam("daap-no-disconnect") int daapNoDisconnect, @QueryParam("hsgid") String hsgid) throws IOException;

	@Path("databases")
	@GET
	public Response databases(@QueryParam("session-id") long sessionId, @QueryParam("revision-number") long revisionNumber, @QueryParam("delta") long delta, @QueryParam("hsgid") String hsgid) throws IOException, SQLException;

	@Path("databases/{databaseId}/items")
	@GET
	public Response items(@PathParam("databaseId") long databaseId, @QueryParam("session-id") long sessionId, @QueryParam("revision-number") long revisionNumber, @QueryParam("delta") long delta, @QueryParam("type") String type, @QueryParam("meta") String meta, @QueryParam("query") String query, @QueryParam("hsgid") String hsgid) throws IOException, SQLException;

	@Path("databases/{databaseId}/containers")
	@GET
	public Response containers(@PathParam("databaseId") long databaseId, @QueryParam("session-id") long sessionId, @QueryParam("revision-number") long revisionNumber, @QueryParam("delta") long delta, @QueryParam("meta") String meta, @QueryParam("hsgid") String hsgid) throws IOException, SQLException;

	@Path("databases/{databaseId}/containers/{containerId}/items")
	@GET
	public Response containerItems(@PathParam("containerId") long containerId, @PathParam("databaseId") long databaseId, @QueryParam("session-id") long sessionId, @QueryParam("revision-number") long revisionNumber, @QueryParam("delta") long delta, @QueryParam("meta") String meta, @QueryParam("type") String type, @QueryParam("group-type") String group_type, @QueryParam("sort") String sort, @QueryParam("include-sort-headers") String include_sort_headers, @QueryParam("query") String query, @QueryParam("index") String index, @QueryParam("hsgid") String hsgid) throws IOException, SQLException;

	@Path("content-codes")
	@GET
	public Response contentCodes() throws IOException;

	@Path("logout")
	@GET
	public Response logout(@QueryParam("session-id") long sessionId);
}
