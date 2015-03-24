package pl.chiqvito.sowieso.rest.client;

import android.content.Context;

import pl.chiqvito.sowieso.rest.ApiService;
import pl.chiqvito.sowieso.rest.dto.CredentialDTO;

public class LoginClient extends BaseApiClient<String> {

    private final CredentialDTO credential;

    public LoginClient(Context context, CredentialDTO credential) {
        super(context, null);
        this.credential = credential;
    }

    @Override
    protected void executeService(ApiService service) {
        service.login(credential, this);
    }

}
