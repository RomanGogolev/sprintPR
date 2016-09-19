package com.javalab.marketing.services;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolTest {
    @Test
    public void testGetConnection() throws SQLException, ClassNotFoundException {
        Connection con = ConnectionPool.getConnection();
        Assert.assertEquals(false,con.isClosed());
    }

    @Test
    public void testConnection() throws SQLException, ClassNotFoundException {
        Connection con = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(
                "select " +
                        "   COUNT(id) " +
                        "from " +
                        "   users "
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Assert.assertNotNull(resultSet.getInt("COUNT(id)"));
    }
}
