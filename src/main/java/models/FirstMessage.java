package models;

import com.google.gson.annotations.SerializedName;
import utils.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by madki on 18/06/16.
 */
public class FirstMessage {
    @SerializedName("setting_type")
    private final String settingType = "call_to_actions";
    @SerializedName("thread_state")
    private final String threadState = "new_thread";
    @SerializedName("call_to_actions")
    private List<CallToAction> callToActions;

    private FirstMessage() {}

    public static FirstMessage create(Message... messages) {
        FirstMessage fm = new FirstMessage();
        fm.callToActions = Arrays.asList(messages)
                .stream()
                .map(CallToAction::create)
                .collect(Collectors.toList());
        return fm;
    }

    private static class CallToAction {
        private final Message message;

        CallToAction(Message message) {
            this.message = message;
        }

        public static CallToAction create(Message message) {
            return new CallToAction(message);
        }

        @Override
        public String toString() {
            return "CallToAction{" +
                    "message=" + message +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FirstMessage{" +
                "settingType='" + settingType + '\'' +
                ", threadState='" + threadState + '\'' +
                ", callToActions=" + Strings.toString(callToActions) +
                '}';
    }
}
