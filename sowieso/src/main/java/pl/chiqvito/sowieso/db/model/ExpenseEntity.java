package pl.chiqvito.sowieso.db.model;

import android.text.TextUtils;
import android.util.Log;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pl.chiqvito.sowieso.rest.dto.CategoryDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseDTO;

public class ExpenseEntity {

    private Long id;
    private Long categoryId;
    private String name;
    private String operationDate;
    private String amount;
    private String info;
    private CategoryEntity category;

    public ExpenseDTO toExpenseDTO() {
        ExpenseDTO exp = new ExpenseDTO();
        exp.setAmount(new BigDecimal(amount));
        CategoryDTO cat = new CategoryDTO();
        cat.setId(categoryId);
        exp.setCategory(cat);
        exp.setId(id);
        exp.setInfo(info);
        exp.setName("android-" + name);
        exp.setOperationDate(getOperationDateDate());
        return exp;
    }

    public boolean isFilled() {
        if (id == null || id.longValue() == -1) {
            return false;
        }
        if (categoryId == null || categoryId.longValue() == -1) {
            return false;
        }
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        if (TextUtils.isEmpty(operationDate)) {
            return false;
        }
        if (TextUtils.isEmpty(amount)) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!TextUtils.isEmpty(name)) {
            this.name = name;
        }
    }

    public Date getOperationDateDate() {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
            return sf.parse(operationDate);
        } catch (ParseException e) {
            Log.e(ExpenseEntity.class.getName(), e.getMessage(), e);
        }
        return null;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        if (!TextUtils.isEmpty(operationDate)) {
            this.operationDate = operationDate;
        }
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        if (!TextUtils.isEmpty(amount)) {
            this.amount = amount;
        }
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        if (!TextUtils.isEmpty(info)) {
            this.info = info;
        }
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ExpenseEntity.class.getSimpleName() + " [");
        sb.append("id=" + id);
        sb.append(", categoryId=" + categoryId);
        sb.append(", name=" + name);
        sb.append(", operationDate=" + operationDate);
        sb.append(", amount=" + amount);
        sb.append(", info=" + info);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpenseEntity)) return false;

        ExpenseEntity that = (ExpenseEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
