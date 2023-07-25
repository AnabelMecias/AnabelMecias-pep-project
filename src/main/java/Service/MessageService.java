package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message addMessage(Message message) {
        if (isValidMessage(message)) {
            return messageDAO.createMessage(message);
        }
        return null;
    }

    public boolean isValidMessage(Message message) {
        return message.getMessage_text().isBlank() == false && message.getMessage_text().length() < 255
                && accountDAO.getAccountByID(message.getPosted_by()) != null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessagesByUserID(int account_id) {
        List<Message> messages = messageDAO.getAllMessagesByUserID(account_id);
        return messages;
    }

    public Message deleteMessage(int message_id) {
        Message existsMessage = messageDAO.deleteMessage(message_id);
        return existsMessage;
    }

    public Message getMessage(int message_id) {
        Message existsMessage = messageDAO.getMessage(message_id);
        if (existsMessage == null) {
            System.out.println("Message with ID " + message_id + " does not exist.");
        }
        return existsMessage;
    }

    public Message updateMessage(int message_id, Message message) {
        Message existsMessage = messageDAO.getMessage(message_id);
        if (existsMessage != null && isValidMessage(message)) {
            existsMessage.setMessage_text(message.getMessage_text());
            existsMessage.setPosted_by(message.getPosted_by());
            existsMessage.setTime_posted_epoch(message.getTime_posted_epoch());
            return existsMessage;
        } else {
            return null;
        }
    }
}
