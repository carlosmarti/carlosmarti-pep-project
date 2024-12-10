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

        Message msg = new Message();

        Connection con = ConnectionUtil.getConnection();
        String sql = "Select * From message Where message_id = ?";

        try {
            PreparedStatement prState = con.prepareStatement(sql);
            prState.setInt(1, msgId);
            ResultSet result = prState.executeQuery();
            if(result.next()){
                msg.setMessage_id(result.getInt("message_id"));
                msg.setPosted_by(result.getInt("posted_by"));
                msg.setMessage_text(result.getString("message_text"));
                msg.setTime_posted_epoch(result.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return msg;
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

    public boolean deleteMessage(int msgId){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Delete From message Where message_id = ?";

        try {
            PreparedStatement prState = con.prepareStatement(sql);
            prState.setInt(1, msgId);
            return prState.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public Message updateMessage(int msgId, String msg){

        Connection con = ConnectionUtil.getConnection();
        String sql = "Update message Set message_text = ? Where message_id = ?";
        Message updatedMsg = new Message();

        try {
            PreparedStatement prState = con.prepareStatement(sql);
            prState.setString(1, msg);
            prState.setInt(2, msgId);
            ResultSet result = prState.executeQuery();
            if(result.next()){
                updatedMsg.setMessage_id(result.getInt("message_id"));
                updatedMsg.setPosted_by(result.getInt("posted_id"));
                updatedMsg.setMessage_text(result.getString("message_text"));
                updatedMsg.setTime_posted_epoch(result.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return updatedMsg;
    }
}
