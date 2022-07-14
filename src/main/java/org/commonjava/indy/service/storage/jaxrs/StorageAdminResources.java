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

import org.apache.commons.io.IOUtils;

import org.commonjava.indy.service.storage.controller.StorageAdminController;
import org.commonjava.indy.service.storage.util.ResponseHelper;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
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
import java.nio.charset.Charset;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.*;

@Tag( name = "Storage Administration", description = "Resource for managing storage" )
@Path( "/api/admin/filesystem" )
@ApplicationScoped
public class StorageAdminResources
{
/*
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Inject
    StorageAdminController adminController;

    @Inject
    ResponseHelper responseHelper;

    @Operation( description = "Create a new filesystem" )
    @APIResponse( responseCode = "201", description = "The filesystem was created" )
    @RequestBody( description = "The filesystem def JSON", name = "body" )
    @POST
    @Consumes( APPLICATION_JSON )
    @Path( "/{filesystem}" )
    public Response create(final @PathParam( "filesystem" ) String filesystem
            , final @Context UriInfo uriInfo,
                             final @Context HttpRequest request,
                             final @Context SecurityContext securityContext )
    {
        logger.info( "Create {}", filesystem );
        Response response;
        String json;
        try
        {
            json = IOUtils.toString( request.getInputStream(), Charset.defaultCharset() );
        }
        catch ( final Exception e )
        {
            response = responseHelper.formatResponse( e, "message" );
            return response;
        }
        return Response.ok().build();
    }

    @Operation( description = "Retrieve the info of a specific filesystem" )
    @APIResponse( responseCode = "200", description = "The filesystem info" )
    @APIResponse( responseCode = "404", description = "The filesystem doesn't exist" )
    @Path( "/{filesystem}" )
    @GET
    @Produces( APPLICATION_JSON )
    public Response get( final @PathParam( "filesystem" ) String filesystem )
    {
        logger.info( "Get {}", filesystem );
        Response response;
        try
        {
            final String fs = adminController.getFilesystem( filesystem );

            if ( fs == null )
            {
                response = Response.status( Status.NOT_FOUND ).build();
            }
            else
            {
                response = responseHelper.formatOkResponseWithJsonEntity( fs );
            }
        }
        catch ( final Exception e )
        {
            logger.error( e.getMessage(), e );
            response = responseHelper.formatResponse( e );
        }
        return response;
    }
*/

}