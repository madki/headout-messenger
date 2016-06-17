package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public class GenericPayload {
    @SerializedName("template_type")
    private final TemplateType templateType = TemplateType.generic;
    public List<StructuredElement> elements;
}
