package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private AccountService accService = new AccountService();
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("register", this::createAccountHandler);
        app.post("login", this::logInAccount);
        

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

    private void logInAccount(Context context){
        ObjectMapper objMapper = new ObjectMapper();

        try{
            Account acc = objMapper.readValue(context.body(), Account.class);
            boolean result = accService.loginAccount(acc);
            if(result){
                context.json(objMapper.writeValueAsString(acc)).status(200);
            }
            else{
                context.status(400);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }


}