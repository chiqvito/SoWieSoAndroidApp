package pl.chiqvito.sowieso.bus.events;

import pl.chiqvito.sowieso.rest.dto.CredentialDTO;

public class LoginEvent {
    private final CredentialDTO credential;

    public LoginEvent(CredentialDTO credential) {
        this.credential = credential;
    }

    public CredentialDTO getCredential() {
        return credential;
    }
}
