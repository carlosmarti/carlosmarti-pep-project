package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    private AccountService accService = new AccountService();
    private MessageService msgService = new MessageService();
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("register", this::createAccountHandler);
        app.post("login", this::logInAccountHandler);
        app.post("messages", this::createMessageHandler);


        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void createAccountHandler(Context context){
        ObjectMapper objMapper = new ObjectMapper();

        try{
            
            Account acc = objMapper.readValue(context.body(), Account.class);
            Account returnedAcc = accService.createAccount(acc);
            if(returnedAcc != null)
                context.json(objMapper.writeValueAsString(returnedAcc)).status(200);
            else
                context.status(400);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void logInAccountHandler(Context context){
        ObjectMapper objMapper = new ObjectMapper();

        try{
            Account acc = objMapper.readValue(context.body(), Account.class);
            boolean result = accService.loginAccount(acc);
            if(result){
                context.json(objMapper.writeValueAsString(acc)).status(200);
            }
            else{
                context.status(401);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void createMessageHandler(Context context){

        ObjectMapper objMapper = new ObjectMapper();

        try{
            Message msg = objMapper.readValue(context.body(), Message.class);
            Message result = msgService.createMessage(msg);
            if(result != null){
                context.json(objMapper.writeValueAsString(result)).status(200);
            }
            else{
                context.status(400);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }



}