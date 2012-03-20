package info.russtyuf;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Map;

/**
 * Class to return a result from MyCokeRewards login
 */
public class LoginResult {
  public final ArrayList<String> messages = new ArrayList<String>();
  public final boolean           loginResult;
  public final boolean           pointBalanceResult;
  public final int               points;
  public final String            screenName;

  LoginResult(Map<?, ?> result) {
    loginResult = Boolean.valueOf(result.get("LOGIN_RESULT").toString());

    if (loginResult) {
      screenName         = result.get("SCREEN_NAME").toString();
      pointBalanceResult = Boolean.valueOf(result.get("POINTS_BALANCE_RESULT").toString());
      points             = Integer.parseInt(result.get("POINTS").toString());
      messages.add("No Messages");
    } else {
      Object[] temp = (Object[]) result.get("MESSAGES");

      for (Object a : temp) {
        messages.add(a.toString());
      }

      screenName         = null;
      pointBalanceResult = false;
      points             = 0;
    }
  }

  /**
   *
   * @return String representing the object
   */
  @Override
  public String toString() {
    if (loginResult) {
      return ("ScreenName: " + screenName + ", Login Result: " + loginResult + ", Points: " + points
              + ", PointBalanceResult: " + pointBalanceResult);
    } else {
      return ("Login failed " + messages.toString());
    }
  }
}
