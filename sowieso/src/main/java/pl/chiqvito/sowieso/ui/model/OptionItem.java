package pl.chiqvito.sowieso.ui.model;

public class OptionItem<T extends Enum> {

    private T type;

    public OptionItem(T type) {
        this.type = type;
    }

    public T getType() {
        return type;
    }

}
