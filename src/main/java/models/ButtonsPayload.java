package models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by madki on 17/06/16.
 */
public class ButtonsPayload {
    @SerializedName("template_type")
    private final TemplateType templateType = TemplateType.button;
    public String text;
    public List<Button> buttons;

    public static ButtonsPayload create(String text, Button... buttons) {
        ButtonsPayload bp = new ButtonsPayload();
        bp.text = text;
        bp.buttons = Arrays.asList(buttons);
        return bp;
    }
}
