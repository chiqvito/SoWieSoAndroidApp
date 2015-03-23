package pl.chiqvito.sowieso.rest;

import java.util.List;

import pl.chiqvito.sowieso.rest.dto.CategoryDTO;
import retrofit.Callback;
import retrofit.http.GET;

public interface ApiService {

    @GET("/dictionary/budget/categories")
    void categories(Callback<List<CategoryDTO>> callback);

}
