package com.javalab.marketing.dao;

import com.javalab.marketing.models.Support;
import com.javalab.marketing.services.ConnectionPool;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupportManagerTest {
    @Test
    public void testCreateSupport() throws SQLException, ClassNotFoundException {
        boolean flag=false;
        SupportManager supportManager = new SupportManager();
        supportManager.createSupport(9999,"test","test");
        List<Support> supports = supportManager.getAll();
        Assert.assertTrue(!supports.isEmpty());
        supportManager.removeSupport(supports.get(0).getId());
    }

    @Test
    public void testRemoveSupport() throws SQLException, ClassNotFoundException {
        boolean flag=false;
        SupportManager supportManager = new SupportManager();
        supportManager.createSupport(9999,"test","test");
        List<Support> supports = supportManager.getAll();
        if(!supports.isEmpty()){
            flag=true;
        }
        supportManager.removeSupport(supports.get(0).getId());
        supports=supportManager.getAllById(9999);
        Assert.assertTrue(flag && supports.isEmpty());
    }

    @Test
    public void testGetSupportsByUserId() throws SQLException, ClassNotFoundException {
        boolean flag=false;
        SupportManager supportManager = new SupportManager();
        supportManager.createSupport(9999,"test","test");
        List<Support> supports = supportManager.getAllById(9999);
        Assert.assertTrue(!supports.isEmpty()&&supports.size()==1);
        supportManager.removeSupport(supports.get(0).getId());
    }

    @Test
    public void testGetAll() throws SQLException, ClassNotFoundException {
        SupportManager supportManager = new SupportManager();
        ArrayList<Support> supports = (ArrayList<Support>) supportManager.getAll();
        Connection con = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(
                "select " +
                        "   COUNT(id) " +
                        "from " +
                        "   supports "
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Assert.assertEquals(supports.size(),resultSet.getInt("COUNT(id)"));
    }

    @Test
    public void testAnswerSupport() throws SQLException, ClassNotFoundException {
        SupportManager supportManager = new SupportManager();
        supportManager.createSupport(9999,"test","test");
        List<Support> supports = supportManager.getAllById(9999);
        supportManager.answerSupport(supports.get(0).getId(),"test");
        supports = supportManager.getAllById(9999);
        Assert.assertEquals("test", supports.get(0).getMessageadmin());
        supportManager.removeSupport(supports.get(0).getId());
    }

    @Test
    public void testGetSupportsBySupportId() throws SQLException, ClassNotFoundException {
        Support getSupport=null;
        SupportManager supportManager = new SupportManager();
        supportManager.createSupport(9999,"test","test");
        List<Support> supports = supportManager.getAllById(9999);
        getSupport=supportManager.getBySupportId(supports.get(0).getId());
        Assert.assertEquals(getSupport.getMessageuser(),supports.get(0).getMessageuser());
        supportManager.removeSupport(supports.get(0).getId());
    }
}
