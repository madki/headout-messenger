package models;

/**
 * Created by madki on 17/06/16.
 */
public enum TemplateType {
    generic("generic"),
    button("button");
    private final String value;

    TemplateType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
