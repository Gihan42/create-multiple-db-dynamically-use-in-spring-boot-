package com.multipleDB.createmultipleDB.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiTenantDataSourceRouter extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {

        return DatabaseContextHolder.getCurrentCompany();
    }
}
