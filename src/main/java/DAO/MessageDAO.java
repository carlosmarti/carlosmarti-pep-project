package DAO;

import static org.mockito.Mockito.calls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    public Message creatMessage(Message m){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Insert Into message (posted_by, message_text, time_posted_epoch) Values (?,?,?)";

        try{
            PreparedStatement prState = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prState.setInt(1,m.getPosted_by());
            prState.setString(2, m.getMessage_text());
            prState.setLong(3, m.getTime_posted_epoch());
            
            prState.executeUpdate();
            ResultSet pkeyResultSet = prState.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, m.getPosted_by(), m.getMessage_text(), m.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        
        return null;
    }

    public List<Message> getAllMessages(){

        List<Message> messages = new ArrayList<Message>();
        Connection con = ConnectionUtil.getConnection();
        String sql = "Select * From message";

        try{
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(sql);
            while(result.next()){
                Message msg = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                messages.add(msg);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        
        return messages;
    }

    public Message getMessage(int msgId){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Select * From message Where message_id = ?";

        try {
            PreparedStatement prState = con.prepareStatement(sql);
            prState.setInt(1, msgId);
            ResultSet result = prState.executeQuery();
            if(result.next()){
                    
                return new Message(result.getInt("message_id"),
                 result.getInt("posted_by"),
                 result.getString("message_text"),
                 result.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
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
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            System.out.println(e);
        }

        return false;
    }

    public Message deleteMessage(Message msg){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Delete From message Where message_id = ?";

        try {
            PreparedStatement prState = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prState.setInt(1, msg.getMessage_id());

            if(prState.executeUpdate() > 0){
                return msg;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return null;
    }
     

    /*
     * public Message deleteMessage(int msgId){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Delete From message Where message_id = ?";

        try {
            PreparedStatement prState = con.prepareStatement(sql);
            prState.setInt(1, msgId);

            prState.executeUpdate();
            ResultSet pkeyResultSet = prState.getResultSet();
            if(pkeyResultSet.next()){
                int generated_message_id = pkeyResultSet.getInt(1);
                return new Message(pkeyResultSet.getInt("message_id"),
                 pkeyResultSet.getInt("posted_by"),
                 pkeyResultSet.getString("message_text"),
                 pkeyResultSet.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
     */
    
    

    public Message updateMessage(int msgId, String msg, Message oldMsg){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Update message Set message_text = ? Where message_id = ?";

        try {
            PreparedStatement prState = con.prepareStatement(sql);
            prState.setString(1, msg);
            prState.setInt(2, msgId);

            if(prState.executeUpdate() > 0){
                oldMsg.setMessage_text(msg);
                return oldMsg;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Message>getMessagesByPoster(int postId){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Select * From message Where posted_by = ?";

        try{
            List<Message> messages = new ArrayList<Message>();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message newMsg = new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                messages.add(newMsg);
            }
            return messages;
        }catch(SQLException e){
            System.out.println(e);
        }

        return new ArrayList<Message>(); 
    }
}
