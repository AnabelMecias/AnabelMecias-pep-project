package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        return messageDAO.createMessage(message);
    } 
    
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessagesByUserID(int account_id) {
        List<Message> messages = messageDAO.getAllMessagesByUserID(account_id);
        if (messages.isEmpty()) {
            System.out.println("Account with ID " + account_id + " does not exist.");
        }
        return messages;
    }

    public Message deleteMessage(int message_id) {
        Message existsMessage = messageDAO.deleteMessage(message_id);
        if (existsMessage == null) {
            System.out.println("Message with ID " + message_id + " does not exist.");
        }
        return existsMessage;
    }

    public Message getMessage(int message_id){
        Message existsMessage = messageDAO.getMessage(message_id);
        if (existsMessage == null) {
            System.out.println("Message with ID " + message_id + " does not exist.");
        }
        return existsMessage;
    }

    public Message updateMessage(int message_id, Message message) {
        Message existsMessage = messageDAO.getMessage(message_id);
        if (existsMessage != null) {
            existsMessage.setMessage_text(message.getMessage_text());
            existsMessage.setPosted_by(message.getPosted_by());
            existsMessage.setTime_posted_epoch(message.getTime_posted_epoch());
            return existsMessage;
        } else {
            System.out.println("Message with ID " + message_id + " does not exist.");
            return null;
        }
    }
}
