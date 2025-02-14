package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    
    private MessageDAO msgDAO;

    public MessageService(){
        msgDAO = new MessageDAO();
    }

    public Message createMessage(Message msg){

        if(msg.getMessage_text().length() < 255 && !msg.getMessage_text().isBlank()){
            if(msgDAO.hasPoster(msg.getPosted_by())){
                Message result = msgDAO.creatMessage(msg);
                return result;
            }
        }
        return null;
    }

    public Message getMessage(String msgId){

        int numMsgId = Integer.parseInt(msgId);

        return msgDAO.getMessage(numMsgId);
    }

    public List<Message> getAllMessages(){
        return msgDAO.getAllMessages();
    }

    /*
     * public Message deleteMessage(String msgId){

        int numMsgId = Integer.parseInt(msgId);
        Message result = msgDAO.deleteMessage(msgDAO.getMessage(numMsgId));
        
        if(result != null)
            return result;

        return null;
    }
     */

     public Message deleteMessage(String messageId){

        int messageIdNum = Integer.parseInt(messageId);
        Message msgExist = msgDAO.getMessage(messageIdNum);

        if(msgExist != null){
            return msgDAO.deleteMessage(msgExist);
        } 

        return null;
     }
    

    public Message updateMessage(String msgId, String messageText){

        int numMsgId = Integer.parseInt(msgId);
        Message updatMessage = null;
        Message oldMessage = msgDAO.getMessage(numMsgId); 
        if( oldMessage != null){
            if(!messageText.isBlank() && messageText.length() < 255)
                updatMessage = msgDAO.updateMessage(numMsgId, messageText, oldMessage);
        }

        return updatMessage;
    }

    public List<Message> getAllMessagesFromAccount(String postId){

        int numpostId = Integer.parseInt(postId);
        return msgDAO.getMessagesByPoster(numpostId);
    }
}
