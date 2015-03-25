package pl.chiqvito.sowieso.rest;

import java.util.List;

import pl.chiqvito.sowieso.rest.dto.CategoryDTO;
import pl.chiqvito.sowieso.rest.dto.CredentialDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthCategoryDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthDTO;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface ApiService {

    @GET("/dictionary/budget/categories")
    void categories(Callback<List<CategoryDTO>> callback);

    @GET("/budget/report/year")
    void reportYear(Callback<List<ExpenseReportYearDTO>> callback);

    @GET("/budget/report/year/month")
    void reportYearMonth(@Query("year") Integer year, Callback<List<ExpenseReportYearMonthDTO>> callback);

    @GET("/budget/report/year/month/category")
    void reportYearMonthCategory(@Query("year") Integer year, @Query("month") Integer month, Callback<List<ExpenseReportYearMonthCategoryDTO>> callback);

    @POST("/budget/expenses")
    void saveExpenses(@Body List<ExpenseDTO> expenses, Callback<Boolean> callback);

    @POST("/auth/login")
    void login(@Body CredentialDTO credential, Callback<String> callback);

}
