package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService(new AccountDAO());
        this.messageService = new MessageService(new MessageDAO());
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegistrationHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagesByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessagesByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessagesByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllUserMessagesHandler);

        return app;
    }

    private void postRegistrationHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(acc);
        if(addedAccount != null){
            context.json(mapper.writeValueAsString(addedAccount));
        }else{
            context.status(400);
        }
    }

    private void postLoginHandler(Context context) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(context.body(), Account.class);
        Account log = accountService.login(acc);
        if(log != null){
            context.json(mapper.writeValueAsString(log));
        }else{
            context.status(401);
        }
    }

    private void postMessagesHandler(Context context) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message mess = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(mess);
        if(addedMessage != null){
            context.json(mapper.writeValueAsString(addedMessage));
        }else{
            context.status(400);
        }
    }

    private void getMessagesHandler(Context context) {
        List<Message> messages = messageService.getMessages();
        context.json(messages);
    }

    private void getMessagesByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message mess = messageService.getMessagesById(messageId);
        if(mess != null){
            context.json(mess);
        }else{
            context.status(200);
        }
    }

    private void deleteMessagesByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message mess = messageService.getMessagesById(messageId);
        if(mess != null && messageService.deleteMessage(messageId)){
            context.json(mess);
        }else{
            context.status(200);
        }
    }

    private void patchMessagesByIdHandler(Context context) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message exists = messageService.getMessagesById(messageId);
        if(exists == null){
            context.status(400);
            return;
        }

        Message update = mapper.readValue(context.body(), Message.class);
        Message mess = messageService.updateMessage(messageId, update.getMessage_text());

        if (mess != null) {
            context.json(mess);
        } else {
            context.status(400);
        }
    }

    private void getAllUserMessagesHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUserId(accountId);
        context.json(mapper.writeValueAsString(messages));
    }


}