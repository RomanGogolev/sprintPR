package com.javalab.marketing.dao;

import com.javalab.marketing.models.Support;
import com.javalab.marketing.services.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupportManager {

    //Connection between webapp and database(MySql)
    private Connection connection;

    public SupportManager() throws SQLException, ClassNotFoundException {
        this.connection = ConnectionPool.getConnection();
    }

    /*
    This method create support(question to admin) in account menu
     */
    public void createSupport(int iduser,String theme,String messageuser) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into supports(iduser,theme,messageuser) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, iduser);
        preparedStatement.setString(2,theme);
        preparedStatement.setString(3,messageuser);
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
    }

    public void createSupportWithId(Support support) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into supports(id,iduser,theme,messageuser) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, support.getId());
        preparedStatement.setInt(2, support.getIduser());
        preparedStatement.setString(3,support.getTheme());
        preparedStatement.setString(4, support.getMessageuser());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
    }

    /*
    This method show all supports, which user sent admin
     */
    public List<Support> getAllById(int iduser) throws SQLException {
        List<Support> supports = new ArrayList<Support>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select " +
                        "   * " +
                        "from " +
                        "   supports " +
                        "where " +
                        "   iduser = ? "
        );
        preparedStatement.setInt(1, iduser);

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Support support = new Support(resultSet.getInt("iduser"),resultSet.getString("theme"),resultSet.getString("messageuser"),resultSet.getString("messageadmin"),
                    resultSet.getInt("read"));
            support.setId(resultSet.getInt("id"));
            supports.add(support);
        }
        return supports;
    }

    /*
    This method show one support in account menu or admin panel
     */
    public Support getBySupportId(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select " +
                        "   * " +
                        "from " +
                        "   supports " +
                        "where " +
                        "   id = ? "
        );
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            Support support = new Support(resultSet.getInt("iduser"), resultSet.getString("theme"), resultSet.getString("messageuser"), resultSet.getString("messageadmin"),
                    resultSet.getInt("read"));
            support.setId(resultSet.getInt("id"));
            return support;
        }else {
            return null;
        }
    }

    /*
    This method show all support in admin panel, which write users
     */
    public List<Support> getAll() throws SQLException {
        List<Support> supports = new ArrayList<Support>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select " +
                        "   * " +
                        "from " +
                        "   supports "
        );
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Support support = new Support(resultSet.getInt("iduser"),resultSet.getString("theme"),resultSet.getString("messageuser"),resultSet.getString("messageadmin"),
                    resultSet.getInt("read"));
            support.setId(resultSet.getInt("id"));
            supports.add(support);
        }
        return supports;
    }

    /*
    This method help answer to support
     */
    public void answerSupport(int id, String messageadmin) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE supports SET messageadmin=? WHERE id=?"
        );

        preparedStatement.setString(1,messageadmin);
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();
    }

    public void removeSupport(int id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM supports WHERE id=?"
        );
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
    }
}
