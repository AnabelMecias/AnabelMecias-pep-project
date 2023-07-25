package Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    private MessageService messageService;
    private AccountService accountService;

    public SocialMediaController() {
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}/", this::updateMessageHandler);
        app.post("/messages", this::postMessageHandler);
        app.post("/register", this::registerUser);
        app.post("/login", this::login);
        return app;
    }

    /**
     * Handler to post a new message
     * 
     * @param context
     * @throws JsonProcessingException
     */
    public void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message updatedMessage = messageService.addMessage(message);
        if (updatedMessage != null) {
            context.status(200).json(mapper.writeValueAsString(updatedMessage));
        } else {
            context.status(400);
        }
    }

    /**
     * Handler to retrieve all messages
     * 
     * @param context
     */
    public void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.status(200).json(messages);
    }

    /**
     * Handler to retrieve a message by id
     * 
     * @param context
     */
    public void getMessageHandler(Context context) {
        int messageId = context.pathParamAsClass("message_id", Integer.class).get();
        Message message = messageService.getMessage(messageId);
        if (message != null) {
            context.status(200).json(message);
        } else {
            context.status(200).result("");
        }
    }

    /**
     * Handler to delete a message by id
     * 
     * @param context
     */
    public void deleteMessageHandler(Context context) {
        int messageId = context.pathParamAsClass("message_id", Integer.class).get();
        Message message = messageService.deleteMessage(messageId);
        if (message != null) {
            context.status(200).json(message);
        } else {
            context.status(200).result("");
        }
    }

    /**
     * Handler to update a message by id
     * 
     * @param context
     * @throws JsonProcessingException
     */
    public void updateMessageHandler(Context context) throws JsonProcessingException {
        int messageId = context.pathParamAsClass("message_id", Integer.class).get();
        Message messag = messageService.getMessage(messageId);
        if (messag != null) {
            ObjectMapper mapper = new ObjectMapper();
            String message_text = mapper.readTree(context.body()).get("message_text").asText();
            messag.setMessage_text(message_text);
            Message updatedMessage = messageService.updateMessage(messageId, messag);
            if (updatedMessage != null) {
                context.status(200).json(mapper.writeValueAsString(updatedMessage));
            } else {
                context.status(400);
            }
        } else {
            context.status(400);
        }
    }

    /**
     * Handler to retrieve a message by account id
     * 
     * @param context
     */
    public void getAllMessagesByUserIDHandler(Context context) {
        int account_id = context.pathParamAsClass("account_id", Integer.class).get();
        List<Message> messages = messageService.getAllMessagesByUserID(account_id);

        if (messages.isEmpty()) {
            context.status(200).json(new ArrayList<>()); 
        } else {
            context.status(200).json(messages); 
        }
    }

    public void registerUser (Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account registeredAccount = accountService.registerUser(account);
        if (registeredAccount != null) {
            context.status(200).json(mapper.writeValueAsString(registeredAccount));
        } else {
            context.status(400);
        }
    }

    public void login (Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loggedAccount = accountService.login(account);
        if (loggedAccount != null) {
            context.status(200).json(mapper.writeValueAsString(loggedAccount));
        } else {
            context.status(401);
        }
    }
    /**
     * This is an example handler for an example endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

}