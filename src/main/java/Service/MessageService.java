package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty() || message.getMessage_text().length() > 255) {
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    public List<Message> getMessages() {
        return messageDAO.getMessages();
    }

    public Message getMessagesById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public boolean deleteMessage(int messageId) {
        return messageDAO.deleteMessage(messageId);
    }

    public Message updateMessage(int messageId, String newMessageText) {
        if (newMessageText == null || newMessageText.trim().isEmpty() || newMessageText.length() > 255) {
            return null;
        }
        boolean updated = messageDAO.updateMessage(messageId, newMessageText);
        return updated ? messageDAO.getMessageById(messageId) : null;
    }

    public List<Message> getMessagesByUserId(int accountId) {
        return messageDAO.getMessagesByUserId(accountId);
    }
}
