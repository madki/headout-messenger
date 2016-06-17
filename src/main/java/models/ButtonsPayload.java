package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public class ButtonsPayload {
    @SerializedName("template_type")
    private final TemplateType templateType = TemplateType.button;
    public String text;
    public List<Button> buttons;
}
