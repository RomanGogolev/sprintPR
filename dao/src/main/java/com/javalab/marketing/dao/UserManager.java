package com.javalab.marketing.dao;

import com.javalab.marketing.models.User;
import com.javalab.marketing.services.ConnectionPool;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserManager {

private Connection connection;

    //Connection between webapp and database(MySql)
    public UserManager()throws SQLException, ClassNotFoundException {
        this.connection = ConnectionPool.getConnection();
    }

    /*
    This method create user by username for authenticate
     */
    public User getUser(String username) throws SQLException{

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select " +
                        "   * " +
                        "from " +
                        "   users " +
                        "where " +
                        "   username = ? "
        );
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        User user = new User(resultSet.getString("email"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("roles"));

        user.setId(resultSet.getInt("id"));

        return user;
    }

    /*
    This method show all users and their roles in admin panel
     */
    public List<User> getAll() throws SQLException{

        List <User> users = new ArrayList<User>();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select " +
                        "   * " +
                        "from " +
                        "   users "
        );

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {

            User user = new User(resultSet.getString("email"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("roles"));

            user.setId(resultSet.getInt("id"));

            users.add(user);
        }

        return users;
    }


    /*
    This method returns all authorities, which have user
     */
    public String getAuthorities(User user){
        String letter="";
        Collection<? extends GrantedAuthority> authorities=user.getAuthorities();
        for(GrantedAuthority grantedAuthority : authorities){
            letter+=grantedAuthority.getAuthority()+";";
        }
        return letter;
    }

    /*
    This method create user with data from registration page
     */
    public User create(String email,  String username, String password,String roles) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("insert users set email=?,username=?,password=?,roles=?", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2,username);
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, roles);
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();

        return new User(email,username,password,roles);
    }

    /*
    This method create user with id
     */
    public User createWithId(User user) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("insert users set id=?,email=?,username=?,password=?,roles=?", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, user.getId());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3,user.getUsername());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, "ROLE_USER");
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();

        return user;
    }

    /*
    This method check user with same name;
     */
    public boolean busyUsername(String username) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select " +
                        "   * " +
                        "from " +
                        "   users " +
                        "where " +
                        "   username = ? "
        );
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        if(!resultSet.first()){
            return true;
        }else return false;
    }

    /*
    This method delete user by username
     */
    public void deleteUser(String username) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE from users WHERE username=? ;"
        );

        preparedStatement.setString(1,username);

        preparedStatement.executeUpdate();
    }
}
