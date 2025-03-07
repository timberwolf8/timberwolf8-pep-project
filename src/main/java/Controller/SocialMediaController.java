package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
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

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postRegistrationHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAuthor(acc);
        if(addedAccount != null){
            context.json(mapper.writeValueAsString(addedAccount));
        }else{
            context.status(400);
        }
    }

    private void postLoginHandler(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(context.body(), Account.class);
        Account log = accountService.login(acc);
        if(log != null){
            context.json(mapper.writeValueAsString(log));
        }else{
            context.status(401);
        }
    }

    private void postMessagesHandler(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        context.json("sample text");
    }

    private void getMessagesHandler(Context context) {
        List<Account> accounts = accountService.getMessages();
        context.json(accounts);
    }

    private void getMessagesByIdHandler(Context context) {
        List<Account> accounts = accountService.getMessagesById();
        context.json(accounts);
    }

    private void deleteMessagesByIdHandler(Context context) {
        context.json(accounts);
    }

    private void patchMessagesByIdHandler(Context context) {
        context.json("sample text");
    }

    private void getAllUserMessagesHandler(Context context) {
        context.json("sample text");
    }


}