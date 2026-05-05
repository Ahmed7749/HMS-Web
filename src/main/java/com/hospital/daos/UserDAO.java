package com.hospital.daos;

import com.hospital.pojos.User;
import com.hospital.utils.UserSupplier;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDAO extends GenericDAO{

    public int addUser(User user){
        String sql = "INSERT INTO users(username, password, role) VALUES (?,?,?)";

        return executeUpdateReturnId(sql, user.getUserName(), user.getPassword(), user.getRole().toString());
    }

    public Optional<User> loggedIn(String username, String password){
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        return executeQuerySingle(sql, UserSupplier::getUserViaResultSet, username, password);
    }


    public Optional<User> findByUsername(String username){
        String sql = "SELECT * FROM users WHERE username = ?";
        return executeQuerySingle(sql, UserSupplier::getUserViaResultSet, username);
    }

    public List<User> getUsers(){
        String sql = "SELECT * FROM users";

        return executeQueryList(sql, UserSupplier::getUserViaResultSet);
    }

    public boolean deleteUser(int userId){
        String sql = "DELETE FROM users WHERE id = ?";
        return executeUpdate(sql, userId);
    }

    public Optional<User> getUserById(int userId){
        String sql = "SELECT * FROM users WHERE id = ?";
        return executeQuerySingle(sql, UserSupplier::getUserViaResultSet, userId);
    }

    public List<User> getUsersByIds(Set<Integer> userIds){
        String mappedIds = getMappedIds(userIds);
        String sql = "SELECT * FROM users WHERE id IN (" + mappedIds +")";
        return executeQueryList(sql, UserSupplier::getUserViaResultSet, userIds.toArray());
    }

    //helper method
    private String getMappedIds(Set<Integer> userIds){
        return userIds.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));
    }
}
