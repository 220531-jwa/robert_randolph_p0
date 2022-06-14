package dev.randolph;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.patch;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

import dev.randolph.controller.AccountController;
import dev.randolph.controller.ClientController;
import io.javalin.Javalin;

public class Driver {
    
    public static void main(String[] args) {
        // Starting server
        Javalin app = Javalin.create();
        app.start();
        
        // Init
        ClientController cc = new ClientController();
        AccountController ac = new AccountController();
        
        // Endpoints
        app.routes(() -> {
            path("/clients", () -> {
                post(cc::createNewClient);
                get(cc::getAllClients);
                path("/{cid}", () -> {
                    get(cc::getClientById);
                    put(cc::updateClientById);
                    delete(cc::deleteClientById);
                    path("/accounts", () -> {
                        post(ac::createNewAccount);
                        get(ac::getAllClientAccounts);
                        path("/{aid}", () -> {
                            get(ac::getClientAccountById);
                            put(ac::updateClientAccountById);
                            delete(ac::deleteClientAccountById);
                            patch(ac::updateClientAccountBalance);
                            path("/transfer/{tid}", () -> {
                                patch(ac::transferClientAccountFunds);
                            });
                        });
                    });
                });
            });
        });
        
        // End of endpoints
    }
}
