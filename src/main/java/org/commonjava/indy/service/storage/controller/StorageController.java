package org.commonjava.indy.service.storage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.commonjava.indy.service.storage.config.CassandraConfig;
import org.commonjava.indy.service.storage.config.StorageServiceConfig;
import org.commonjava.storage.pathmapped.config.DefaultPathMappedStorageConfig;
import org.commonjava.storage.pathmapped.config.PathMappedStorageConfig;
import org.commonjava.storage.pathmapped.core.FileBasedPhysicalStore;
import org.commonjava.storage.pathmapped.core.PathMappedFileManager;
import org.commonjava.storage.pathmapped.pathdb.datastax.CassandraPathDB;
import org.commonjava.storage.pathmapped.spi.PathDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.commonjava.storage.pathmapped.pathdb.datastax.util.CassandraPathDBUtils.*;

@ApplicationScoped
public class StorageController {
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    private PathMappedFileManager fileManager;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    StorageServiceConfig storageServiceConfig;

    @Inject
    CassandraConfig cassandraConfig;

    @PostConstruct
    void init()
    {
        PathMappedStorageConfig config = getPathMappedStorageConfig();
        PathDB pathDB = new CassandraPathDB( config );
        File baseDir = storageServiceConfig.baseDir();
        if ( !baseDir.isDirectory() )
        {
            baseDir.mkdirs();
        }
        fileManager = new PathMappedFileManager( config, pathDB, new FileBasedPhysicalStore( baseDir ) );
    }

    private PathMappedStorageConfig getPathMappedStorageConfig() {
        Map<String, Object> props = getCassandraProps();
        return new DefaultPathMappedStorageConfig( props );
    }

    protected Map<String, Object> getCassandraProps()
    {
        Map<String, Object> props = new HashMap<>();
        props.put( PROP_CASSANDRA_HOST, cassandraConfig.host() );
        props.put( PROP_CASSANDRA_PORT, cassandraConfig.port() );
        props.put( PROP_CASSANDRA_USER, cassandraConfig.user() );
        props.put( PROP_CASSANDRA_PASS, cassandraConfig.pass() );
        props.put( PROP_CASSANDRA_KEYSPACE, cassandraConfig.keyspace() );
        return props;
    }

    public void writeFile(String filesystem, String path, InputStream inputStream) throws IOException {
        logger.debug("Write file, filesystem: {}, path: {}", filesystem, path);
        try (OutputStream outputStream = fileManager.openOutputStream(filesystem, path) )
        {
            IOUtils.copy(inputStream, outputStream);
        }
    }

    public InputStream getFileAsStream(String filesystem, String path) throws IOException {
        return fileManager.openInputStream(filesystem, path);
    }

    public String list(String filesystem, String path) throws Exception {
        return objectMapper.writeValueAsString( fileManager.list(filesystem, path) );
    }
}
