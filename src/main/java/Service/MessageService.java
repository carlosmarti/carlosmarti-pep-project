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

        if(msg.getMessage_text().length() > 255 && !msg.getMessage_text().isBlank()){
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

    public String deleteMessage(String msgId){

        int numMsgId = Integer.parseInt(msgId);
        boolean result = false;
        Message hasMessage = msgDAO.getMessage(numMsgId);

        if(hasMessage != null)
            result = msgDAO.deleteMessage(numMsgId);

        if(result)
            return "now-deleted";

        return "";
    }

    public Message updateMessage(String msgId, String messageText){

        int numMsgId = Integer.parseInt(msgId);
        Message updatMessage = new Message();

        if(msgDAO.getMessage(numMsgId) != null){
            if(!messageText.isBlank() && messageText.length() > 255)
                updatMessage = msgDAO.updateMessage(numMsgId, messageText);
        }

        return updatMessage;
    }

    public List<Message> getAllMessagesFromAccount(String postId){

        int numpostId = Integer.parseInt(postId);
        return msgDAO.getMessagesByPoster(numpostId);
    }
}
