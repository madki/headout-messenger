package models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public class GenericPayload {
    @SerializedName("template_type")
    private final TemplateType templateType = TemplateType.generic;
    public List<StructuredElement> elements;

    public static GenericPayload create(StructuredElement... elements) {
        GenericPayload gp = new GenericPayload();
        gp.elements = Arrays.asList(elements);
        return gp;
    }

    public static GenericPayload create(List<StructuredElement> elements) {
        GenericPayload gp = new GenericPayload();
        gp.elements = elements;
        return gp;
    }
}
