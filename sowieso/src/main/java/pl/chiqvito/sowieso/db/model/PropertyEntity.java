package pl.chiqvito.sowieso.db.model;

public class PropertyEntity {

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PropertyEntity.class.getSimpleName() + " [");
        sb.append("name=" + name);
        sb.append(", value=" + value);
        sb.append("]");
        return sb.toString();
    }

}
