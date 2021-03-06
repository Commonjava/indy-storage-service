package org.commonjava.indy.service.storage.config;

import io.quarkus.runtime.Startup;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@Startup
@ConfigMapping( prefix = "cassandra" )
@ApplicationScoped
public interface CassandraConfig
{
    @WithName( "host" )
    String host();

    @WithName( "port" )
    int port();

    @WithName( "user" )
    String user();

    @WithName( "pass" )
    String pass();

    @WithName( "keyspace" )
    String keyspace();
}
