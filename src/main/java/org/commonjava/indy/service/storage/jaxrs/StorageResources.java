/**
 * Copyright (C) 2011-2022 Red Hat, Inc. (https://github.com/Commonjava/service-parent)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.commonjava.indy.service.storage.jaxrs;

import org.commonjava.indy.service.storage.controller.StorageController;
import org.commonjava.indy.service.storage.util.ResponseHelper;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.spi.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.io.InputStream;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status;
import static org.commonjava.storage.pathmapped.util.PathMapUtils.ROOT_DIR;

@Tag( name = "Storage api", description = "Resource for storage" )
@ApplicationScoped
@Path( "/api" )
public class StorageResources
{
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Inject
    StorageController controller;

    @Inject
    ResponseHelper responseHelper;

    @Operation( description = "Write a file" )
    @APIResponse( responseCode = "201", description = "The file was created" )
    @PUT
    @POST
    @Path( "/content/{filesystem}/{path: (.*)}" )
    public Response put(final @PathParam( "filesystem" ) String filesystem,
                           final @PathParam( "path" ) String path,
                           final @Context UriInfo uriInfo,
                           final @Context HttpRequest request,
                           final @Context SecurityContext securityContext )
    {
        logger.info( "Write [{}]{}", filesystem, path );
        Response response;
        try
        {
            controller.writeFile( filesystem, path, request.getInputStream() );
        }
        catch ( final Exception e )
        {
            response = responseHelper.formatResponse( e, "message" );
            return response;
        }
        return Response.ok().build();
    }

    @Operation( description = "Retrieve a file" )
    @APIResponse( responseCode = "200", description = "The file content" )
    @APIResponse( responseCode = "404", description = "The file doesn't exist" )
    @GET
    @Path( "/content/{filesystem}/{path: (.*)}" )
    public Response get( final @PathParam( "filesystem" ) String filesystem,
                         final @PathParam( "path" ) String path )
    {
        logger.info( "Get [{}]{}", filesystem, path );
        Response response;
        try
        {
            final InputStream is = controller.getFileAsStream( filesystem, path );
            if ( is == null )
            {
                response = Response.status( Status.NOT_FOUND ).build();
            }
            else
            {
                response = Response.ok( is ).build();
            }
        }
        catch ( final Exception e )
        {
            logger.error( e.getMessage(), e );
            response = responseHelper.formatResponse( e );
        }
        return response;
    }

    @Operation( description = "List a directory" )
    @APIResponse( responseCode = "200", description = "The dir list" )
    @APIResponse( responseCode = "404", description = "The dir doesn't exist" )
    @GET
    @Produces( APPLICATION_JSON )
    @Path( "/browse/{path: (.+)}" )
    public Response list( final @PathParam( "path" ) String rawPath )
    {
        logger.info( "List [{}]", rawPath );
        Response response;
        try
        {
            int limit = 2;
            String[] tokens = rawPath.split("/", limit );
            String filesystem = tokens[0];
            String path;
            if ( tokens.length == limit )
            {
                path = tokens[1];
            }
            else
            {
                path = ROOT_DIR;
            }
            final String ret = controller.list( filesystem, path );
            if ( ret == null )
            {
                response = Response.status( Status.NOT_FOUND ).build();
            }
            else
            {
                response = Response.ok( ret ).build();
            }
        }
        catch ( final Exception e )
        {
            logger.error( e.getMessage(), e );
            response = responseHelper.formatResponse( e );
        }
        return response;
    }

}