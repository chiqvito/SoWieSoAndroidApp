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
        if (parentId != null)
            return " - " + name;
        return name;
    }

}
