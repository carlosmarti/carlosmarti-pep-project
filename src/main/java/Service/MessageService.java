package Service;

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
}
