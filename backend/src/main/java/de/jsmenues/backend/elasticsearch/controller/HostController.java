package de.jsmenues.backend.elasticsearch.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jsmenues.backend.elasticsearch.dao.HostsDao;
import de.jsmenues.backend.zabbixservice.ZabbixService;

@Path("/elasticsearch")
public class HostController {

    private static Logger LOGGER = LoggerFactory.getLogger(HostController.class);

    /**
     * insert all hosts from zabbix to elasticsearch
     *
     * @return status new hosts are inserted ,updated or not
     */
    @PermitAll
    @POST
    @Path("/insertallhosts")
    public Response InsertAllHosts() throws IOException {

        ZabbixService zabbixService = new ZabbixService();
        List<Map<String, Object>> result = zabbixService.getAllHosts();
        String status = "";
        status = HostsDao.insertAllHosts(result);
        return Response.ok(status).build();
    }

    /**
     * Get all hosts from elasticsearch
     *
     * @return list of hosts
     */
    @PermitAll
    @GET
    @Path("/getallhosts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHosts() throws IOException {

        List<Map<String, Object>> result = HostsDao.getAllHosts();
        return Response.ok(result).build();
    }

    /**
     * Delete host by id from elasticsearch
     *
     * @param hostid
     * @return response about delete request
     */
    @PermitAll
    @DELETE
    @Path("/deletehostById")
    public Response deleteHostByID(@QueryParam("hostid") String hostId) throws IOException {

        String result1 = HostsDao.deleteHostById(hostId);
        String result2 = HostsDao.deleteHostInfoById(hostId);
        return Response.ok(result1 + "\n" + result2).build();
    }

}