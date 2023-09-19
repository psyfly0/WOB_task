/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.exceptions;

import java.sql.SQLException;

/**
 *
 * @author szaboa
 */
public class DataSyncException extends SQLException {
    private String tableName;
    private String operationName;
    private SQLException sqlException;

    public DataSyncException(String message, String tableName, String operationName, SQLException sqlException) {
        super(message);
        this.tableName = tableName;
        this.operationName = operationName;
        this.sqlException = sqlException;
    }

    public String getTableName() {
        return tableName;
    }

    public String getOperationName() {
        return operationName;
    }
    
}
