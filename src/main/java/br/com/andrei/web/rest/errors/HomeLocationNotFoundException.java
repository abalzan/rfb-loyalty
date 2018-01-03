package br.com.andrei.web.rest.errors;

public class HomeLocationNotFoundException extends BadRequestAlertException {

    public HomeLocationNotFoundException() {
        super("Home Location Not found", "userManagement", "homelocationnotfound");
    }
}
