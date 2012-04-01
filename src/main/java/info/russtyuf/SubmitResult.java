package info.russtyuf;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class to represent the result of submitting a code to MCR
 */
public class SubmitResult {
    public final String screenName;
    public final boolean enterCodeResult;
    public final boolean loginResult;
    public final int pointsEarned;
    public final ArrayList<String> messages = new ArrayList<String>();

    SubmitResult(Map<?, ?> result) {
        loginResult = Boolean.valueOf(result.get("LOGIN_RESULT").toString());
        enterCodeResult = loginResult && Boolean.valueOf(result.get("ENTER_CODE_RESULT").toString());
        if(enterCodeResult) {
            screenName = result.get("SCREEN_NAME").toString();
            pointsEarned = Integer.parseInt(result.get("POINTS_EARNED").toString());
            messages.add("No Messages");
        } else {
            Object[] temp = (Object[]) result.get("MESSAGES");
            for (Object a:temp) {
                messages.add(a.toString());
            }
            screenName = null;
            pointsEarned = 0;
        }
    }

  public String resultMsg(String code) {
    return ("Code: " + code + " Points Earned: " + pointsEarned + "\n");
  }

    @Override
    public String toString() {
        if (enterCodeResult) {
            return ("ScreenName: " + screenName + ", Login Result: " + loginResult + ", Points Earned: " + pointsEarned +
                    ", Enter Code Result: " + enterCodeResult);
        } else {
            return ("Code submission failed " + messages.toString());
        }
    }
}
