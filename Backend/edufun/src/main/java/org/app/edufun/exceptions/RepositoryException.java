package org.app.edufun.exceptions;

import java.util.Optional;
import java.util.UUID;

public class RepositoryException extends RuntimeException{
    public RepositoryException(String action, Exception e) {
        super(String.format("RepositoryException: %s performed with error - %s", action, e));
    }
    /*public static RepositoryException collector(long siteId, String number, String meter, String utilityTypeName){
        return new RepositoryException("Collector", String.format("%s_%s_%s_%s", siteId, nullableIdValue(number), meter, utilityTypeName));
    }*/
}
