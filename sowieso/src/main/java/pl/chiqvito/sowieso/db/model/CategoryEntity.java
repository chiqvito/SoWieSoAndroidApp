package pl.chiqvito.sowieso.db.model;

public class CategoryEntity {

    private Long id;
    private String name;
    private Long parentId;
    private Boolean isSelected;

    public CategoryEntity() {
        this.isSelected = false;
    }

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

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CategoryEntity.class.getSimpleName() + " [");
        sb.append("id=" + id);
        sb.append(", name=" + name);
        sb.append(", parentId=" + parentId);
        sb.append(", isSelected=" + isSelected);
        sb.append("]");
        return sb.toString();
    }

}
