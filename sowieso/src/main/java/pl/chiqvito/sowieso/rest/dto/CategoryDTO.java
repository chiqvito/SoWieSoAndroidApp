package pl.chiqvito.sowieso.rest.dto;

import com.google.gson.annotations.SerializedName;

import pl.chiqvito.sowieso.db.model.CategoryEntity;

public class CategoryDTO {

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("parentId")
    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public CategoryEntity toCategoryEntity() {
        CategoryEntity ce = new CategoryEntity();
        ce.setId(id);
        ce.setName(name);
        ce.setParentId(parentId);
        return ce;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + this.getClass().getSimpleName());
        sb.append(" id: " + id);
        sb.append(", name: " + name);
        sb.append(", parentId: " + parentId);
        sb.append("]");
        return sb.toString();
    }

}
