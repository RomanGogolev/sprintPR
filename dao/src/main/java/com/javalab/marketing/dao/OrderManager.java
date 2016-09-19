package com.javalab.marketing.dao;


import com.javalab.marketing.models.Order;
import com.javalab.marketing.services.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private Connection connection;

    //Connection between webapp and database(MySql)
    public OrderManager() throws SQLException, ClassNotFoundException {
        this.connection = ConnectionPool.getConnection();
    }

    /*
    This method create order from user panel in account menu
     */
    public void createOrder(Order order) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert orders set userid=?,appname=?,hrefappstore=?,hrefplaymarket=?,service=?,count=?,price=?,state=?", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, order.getUserid());
        preparedStatement.setString(2,order.getAppname());
        preparedStatement.setString(3, order.getHrefappstore());
        preparedStatement.setString(4, order.getHrefplaymarket());
        preparedStatement.setString(5, order.getService());
        preparedStatement.setInt(6, order.getCount());
        preparedStatement.setDouble(7, order.getPrice());
        preparedStatement.setString(8, order.getState());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
    }

    /*
    This method increment earn count
     */
    public void upEarn(String appName) throws SQLException {
        List<Order> orders = new ArrayList<Order>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select " +
                        "   * " +
                        "from " +
                        "   orders " +
                        "where " +
                        "   appname = ? "
        );
        preparedStatement.setString(1, appName);

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Order order = new Order(resultSet.getInt("userid"),resultSet.getString("appname"),resultSet.getString("hrefappstore"),resultSet.getString("hrefplaymarket"),
                    resultSet.getString("service"),resultSet.getInt("count"),resultSet.getDouble("price"),resultSet.getString("state"));
            order.setId(resultSet.getInt("id"));
            order.setEarn(resultSet.getInt("earn"));
            orders.add(order);
        }

        for(Order o:orders){
            o.setEarn(o.getEarn()+1);
            if(o.getEarn()==o.getCount()){
                completeOrder(o.getId());
            }else{
                PreparedStatement preparedStatement2 = connection.prepareStatement(
                        "UPDATE orders SET earn=? WHERE id=? ;"
                );

                preparedStatement2.setInt(1, o.getEarn());
                preparedStatement2.setInt(2,o.getId());
                preparedStatement.executeUpdate();
            }
        }

    }

    /*
    This method create order with id
     */
    public void createOrderWithId(Order order) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert orders set id=?,userid=?,appname=?,hrefappstore=?,hrefplaymarket=?,service=?,count=?,price=?,state=?", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, order.getId());
        preparedStatement.setInt(2, order.getUserid());
        preparedStatement.setString(3,order.getAppname());
        preparedStatement.setString(4, order.getHrefappstore());
        preparedStatement.setString(5, order.getHrefplaymarket());
        preparedStatement.setString(6, order.getService());
        preparedStatement.setInt(7, order.getCount());
        preparedStatement.setDouble(8, order.getPrice());
        preparedStatement.setString(9, order.getState());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
    }

    /*
    This method show all orders by user in account menu
     */
    public List<Order> getByUserId(int userid) throws SQLException {
        List<Order> orders = new ArrayList<Order>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select " +
                        "   * " +
                        "from " +
                        "   orders " +
                        "where " +
                        "   userid = ? "
        );
        preparedStatement.setInt(1, userid);

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Order order = new Order(resultSet.getInt("userid"),resultSet.getString("appname"),resultSet.getString("hrefappstore"),resultSet.getString("hrefplaymarket"),
                    resultSet.getString("service"),resultSet.getInt("count"),resultSet.getDouble("price"),resultSet.getString("state"));
            order.setId(resultSet.getInt("id"));
            orders.add(order);
        }
        return orders;
    }

    /*
    This method show all orders in admin panel
     */
    public List<Order> getAll() throws SQLException {
        List<Order> orders = new ArrayList<Order>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select " +
                        "   * " +
                        "from " +
                        "   orders "
        );

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Order order = new Order(resultSet.getInt("userid"),resultSet.getString("appname"),resultSet.getString("hrefappstore"),resultSet.getString("hrefplaymarket"),
                    resultSet.getString("service"),resultSet.getInt("count"),resultSet.getInt("price"),resultSet.getString("state"));
            order.setId(resultSet.getInt("id"));
            orders.add(order);
        }
        return orders;
    }

    /*
    This method initial payment in account menu
     */
    public void payment(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE orders SET state=? WHERE id=? ;"
        );

        preparedStatement.setString(1,"Выполняется");
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();
    }

    /*
    This method update new price in db
     */
    public void updatePayment(int id,double summ) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE orders SET price=? WHERE id=? ;"
        );

        preparedStatement.setDouble(1,summ);
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();
    }

    /*
    This method for accept order in admin panel, and it transfer order from wait to need pay
     */
    public void acceptOrder(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE orders SET state=? WHERE id=? ;"
        );

        preparedStatement.setString(1,"Требуется оплата");
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();
    }

    /*
    This method delete order how it complete
     */
    public void deleteOrder(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE from orders WHERE id=? ;"
        );

        preparedStatement.setInt(1,id);

        preparedStatement.executeUpdate();
    }

    public void completeOrder(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE orders SET state=? WHERE id=? ;"
        );

        preparedStatement.setString(1,"Готово");
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();
    }

    /*
    This method show how many orders has user
     */
    public int countOrder(int userid) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select " +
                        " count(userid) " +
                        "from " +
                        "   orders " +
                        "where " +
                        "   userid = ? "
        );
        preparedStatement.setInt(1, userid);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        return resultSet.getInt("count(userid)");
    }

    /*
    This method get order by id in admin panel for accept order
     */
    public Order getOrder(int id) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select " +
                        " * " +
                        "from " +
                        "   orders " +
                        "where " +
                        "   id = ? "
        );
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        Order order = new Order(resultSet.getInt("userid"),resultSet.getString("appname"),resultSet.getString("hrefappstore"),resultSet.getString("hrefplaymarket"),
                resultSet.getString("service"),resultSet.getInt("count"),resultSet.getInt("price"),resultSet.getString("state"));
        order.setId(resultSet.getInt("id"));
        return order;
    }
}
