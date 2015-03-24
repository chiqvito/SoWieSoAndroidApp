package pl.chiqvito.sowieso.rest;

import java.util.List;

import pl.chiqvito.sowieso.rest.dto.CategoryDTO;
import pl.chiqvito.sowieso.rest.dto.CredentialDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseDTO;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ApiService {

    @GET("/dictionary/budget/categories")
    void categories(Callback<List<CategoryDTO>> callback);

    @POST("/budget/expenses")
    void saveExpenses(@Body List<ExpenseDTO> expenses, Callback<Boolean> callback);

    @POST("/auth/login")
    void login(@Body CredentialDTO credential, Callback<String> callback);

}
