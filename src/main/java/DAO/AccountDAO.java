package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    
    public Account registerAccount(Account acc){
        Connection con = ConnectionUtil.getConnection();
        String sql = "Insert Into account (username, password) Values (?,?)";
        Account account = null;

        try{
            PreparedStatement prState = con.prepareStatement(sql);
            prState.setString(1, acc.getUsername());
            prState.setString(2, acc.getPassword());
            /*
             * 
             * ResultSet result = prState.executeQuery();
            if(result.next()){
                account = new Account(result.getInt("account_id"),result.getString("username"), result.getString("password"));
            }
             */
            prState.executeUpdate();
            ResultSet resultKey = prState.getGeneratedKeys();
            if(resultKey.next()){
                int generatedId = (int) resultKey.getLong(1);
                account = new Account(generatedId, acc.getUsername(), acc.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        
        return account;
    }

    public boolean isAccount(int accId){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Select * From account Where account_id = ?";

        try{
            PreparedStatement prState = con.prepareStatement(sql);
            prState.setInt(1, accId);
            ResultSet result = prState.executeQuery();
            if(result.next())
                if(result.getInt("account_id") == accId)
                    return true;
        }catch(SQLException e){
            System.out.println(e);
        }

        return false;
    }

    public boolean accountLogIn(Account acc){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Select * From account Where username = ? and password = ?";

        try{
            PreparedStatement prState = con.prepareStatement(sql);
            prState.setString(1, acc.getUsername());
            prState.setString(2, acc.getPassword());
            ResultSet rs = prState.executeQuery();
            if(rs.next()){
                Account account = new Account(rs.getInt("account_id"),rs.getString("username"), rs.getString("password"));
                if(acc.username == account.getUsername() && acc.getPassword() == account.getPassword())
                    return true;
            }
        }catch(SQLException e){
            System.out.println(e);
        }

        return false;
    }
}
