package pl.chiqvito.sowieso.db.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.Date;

import pl.chiqvito.sowieso.rest.dto.CategoryDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseDTO;
import pl.chiqvito.sowieso.utils.DateUtil;

public class ExpenseEntity implements Parcelable {

    public static final Parcelable.Creator<ExpenseEntity> CREATOR = new Parcelable.Creator<ExpenseEntity>() {
        public ExpenseEntity createFromParcel(Parcel in) {
            return new ExpenseEntity(in);
        }

        public ExpenseEntity[] newArray(int size) {
            return new ExpenseEntity[size];
        }
    };

    private Long id;
    private Long categoryId;
    private String name;
    private String operationDate;
    private String amount;
    private String info;
    private CategoryEntity category;

    public ExpenseEntity() {
    }

    private ExpenseEntity(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        id = in.readLong();
        categoryId = in.readLong();
        name = in.readString();
        operationDate = in.readString();
        amount = in.readString();
        info = in.readString();
        category = in.readParcelable(CategoryEntity.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id == null ? -1 : id);
        dest.writeLong(categoryId == null ? -1 : categoryId);
        dest.writeString(name == null ? "" : name);
        dest.writeString(operationDate == null ? "" : operationDate);
        dest.writeString(amount == null ? "" : amount);
        dest.writeString(info == null ? "" : info);
        dest.writeParcelable(category == null ? new CategoryEntity() : category, flags);
    }

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
        return DateUtil.date(getOperationDate());
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
