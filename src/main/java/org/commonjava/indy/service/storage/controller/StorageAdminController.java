package org.commonjava.indy.service.storage.controller;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StorageAdminController {
    public String getFilesystem(String filesystem) {
        return "hello";
    }
}
