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

        try{
            PreparedStatement prState = con.prepareStatement(sql);
            prState.setString(1, acc.getUsername());
            prState.setString(2, acc.getPassword());
            prState.executeUpdate();
        }catch(SQLException e){
            System.out.println(e);
        }
        
        return acc;
    }

    public boolean isAccount(int accId){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Select * From account Where account_id = ?";

        try{
            PreparedStatement prState = con.prepareStatement(sql);
            prState.setInt(1, accId);
            return prState.execute();
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
