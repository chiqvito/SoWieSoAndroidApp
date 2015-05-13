package pl.chiqvito.sowieso.rest;

import java.util.List;

import pl.chiqvito.sowieso.rest.dto.CategoryDTO;
import pl.chiqvito.sowieso.rest.dto.CredentialDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthCategoryDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthDTO;
import pl.chiqvito.sowieso.rest.dto.InventoryCarConsumptionDTO;
import pl.chiqvito.sowieso.rest.dto.InventoryCarDTO;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
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

    @GET("/inventory/car/page/{page_nbr}")
    void cars(@Path("page_nbr") Integer page, Callback<List<InventoryCarDTO>> callback);

    @GET("/inventory/car/consumption/page/{page_nbr}")
    void carConsumptions(@Path("page_nbr") Integer page, Callback<List<InventoryCarConsumptionDTO>> callback);

    @POST("/inventory/car/consumption")
    void createCarConsumption(@Body InventoryCarConsumptionDTO data, Callback<Boolean> callback);

    @PUT("/inventory/car/consumption")
    void updateCarConsumption(@Body InventoryCarConsumptionDTO data, Callback<Boolean> callback);

    @DELETE("/inventory/car/consumption/{id}")
    void removeCarConsumption(@Path("id") Long id, Callback<Boolean> callback);
}
