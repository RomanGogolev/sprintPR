package com.javalab.marketing.dao;

import com.javalab.marketing.models.Order;
import com.javalab.marketing.services.ConnectionPool;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderManagerTest {

    @Test(expected = SQLException.class)
    public void testNotExisting() throws SQLException, ClassNotFoundException {
        OrderManager orderManager = new OrderManager();
        orderManager.getOrder(9999);
    }

    @Test
    public void testRemove() throws SQLException, ClassNotFoundException {
        Order order = new Order(9999,"test","test","test","test",1,1,"test");
        boolean flag=false;
        OrderManager orderManager = new OrderManager();
        orderManager.createOrder(order);
        List<Order> orders = orderManager.getByUserId(9999);
        if(!orders.isEmpty()){
            flag=true;
        }
        orderManager.deleteOrder(orders.get(0).getId());
        orders = orderManager.getByUserId(9999);
        Assert.assertTrue(flag&&orders.isEmpty());
    }

    @Test
    public void testCreateAndExisting() throws SQLException, ClassNotFoundException {
        Order order = new Order(9999,"test","test","test","test",1,1,"test");
        OrderManager orderManager = new OrderManager();
        orderManager.createOrder(order);
        ArrayList<Order> orders = (ArrayList<Order>) orderManager.getByUserId(9999);
        Assert.assertTrue(!orders.isEmpty());
        orderManager.deleteOrder(orders.get(0).getId());
    }

    @Test
    public void testGetByUserId() throws SQLException, ClassNotFoundException {
        Order order1 = new Order(9999,"test","test","test","test",1,1,"test");
        Order order2 = new Order(9999,"test","test","test","test",1,1,"test");
        Order order3 = new Order(9999,"test","test","test","test",1,1,"test");
        boolean flag=false;
        OrderManager orderManager = new OrderManager();
        orderManager.createOrder(order1);
        orderManager.createOrder(order2);
        orderManager.createOrder(order3);
        ArrayList<Order> orders = (ArrayList<Order>) orderManager.getByUserId(9999);
        orderManager.deleteOrder(orders.get(0).getId());
        orderManager.deleteOrder(orders.get(1).getId());
        orderManager.deleteOrder(orders.get(2).getId());
        Assert.assertEquals(3,orders.size());
    }

    @Test
    public void testGetAll() throws SQLException, ClassNotFoundException {
        OrderManager orderManager = new OrderManager();
        ArrayList<Order> orders = (ArrayList<Order>) orderManager.getAll();
        Connection con = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement(
                "select " +
                        "   COUNT(id) " +
                        "from " +
                        "   orders "
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Assert.assertEquals(orders.size(),resultSet.getInt("COUNT(id)"));
    }

    @Test
    public void testPayment() throws SQLException, ClassNotFoundException {
        OrderManager orderManager = new OrderManager();
        Order crOrder = new Order(9999,"test","test","test","test",1,1,"test");
        orderManager.createOrder(crOrder);
        ArrayList<Order> orders = (ArrayList<Order>) orderManager.getByUserId(9999);
        orderManager.payment(orders.get(0).getId());
        orders = (ArrayList<Order>) orderManager.getByUserId(9999);
        orderManager.deleteOrder(orders.get(0).getId());
        Assert.assertEquals("Выполняется",orders.get(0).getState());
    }

    @Test
    public void testAcceptOrder() throws SQLException, ClassNotFoundException {
        OrderManager orderManager = new OrderManager();
        Order curOrder;
        Order crOrder = new Order(9999,"test","test","test","test",1,1,"test");
        orderManager.createOrder(crOrder);
        ArrayList<Order> orders = (ArrayList<Order>) orderManager.getByUserId(9999);
        orderManager.acceptOrder(orders.get(0).getId());
        orders = (ArrayList<Order>) orderManager.getByUserId(9999);
        orderManager.deleteOrder(orders.get(0).getId());
        Assert.assertEquals("Требуется оплата",orders.get(0).getState());
    }

    @Test
    public void testCountOrder() throws SQLException, ClassNotFoundException {
        OrderManager orderManager = new OrderManager();
        Order crOrder = new Order(9999,"test","test","test","test",1,1,"test");
        orderManager.createOrder(crOrder);
        int count=orderManager.countOrder(9999);
        ArrayList<Order> orders = (ArrayList<Order>) orderManager.getByUserId(9999);
        orderManager.deleteOrder(orders.get(0).getId());
        Assert.assertEquals(1,count);
    }
}
