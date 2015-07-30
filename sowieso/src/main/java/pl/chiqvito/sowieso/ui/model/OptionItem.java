package pl.chiqvito.sowieso.ui.model;

public class OptionItem<T> {

    private T type;

    public OptionItem(T type) {
        this.type = type;
    }

    public T getType() {
        return type;
    }

    @Override
    public String toString() {
        if (getType() == null)
            return "All";
        return getType().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OptionItem)) return false;

        OptionItem that = (OptionItem) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
}
