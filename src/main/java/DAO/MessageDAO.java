package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    public Message creatMessage(Message m){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Insert Into message (posted_by, message_text, time_posted_epoch) Values (?,?,?)";

        try{
            PreparedStatement prState = con.prepareStatement(sql);
            prState.setInt(1,m.getPosted_by());
            prState.setString(2, m.getMessage_text());
            prState.setLong(3, m.getTime_posted_epoch());
            ResultSet result = prState.executeQuery();
            if(result.next()){
                Message ms = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                return ms;
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        
        return null;
    }

    public boolean hasPoster(int postId){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Select * From message Where posted_by = ?";

        try{
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, postId);
            return preparedStatement.execute();
        }catch(SQLException e){
            System.out.println(e);
        }

        return false;
    }
}
