package info.russtyuf;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//~--- JDK imports ------------------------------------------------------------

/**
 * McrClient is used to login to MyCokeRewards and to submit codes
 * @author Rusty
 */
public class McrClient {
  static String                  SERVER = "https://secure.mycokerewards.com/xmlrpc";
  private XmlRpcClient           client;
  private XmlRpcClientConfigImpl pConfig;

  /**
   * Constructs ...
   *
   */
  public McrClient() {
    pConfig = new XmlRpcClientConfigImpl();

    URL url = null;

    try {
      url = new URL(SERVER);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    pConfig.setServerURL(url);
    client = new XmlRpcClient();
    client.setConfig(pConfig);
  }

  /**
   *  Method to login to MyCokeRewards
   * @param pParams parameters needed to talk to cokeRewards, use createLoginParams to create correct params
   * @return result from myCokeRewards, formatted as a Map {SCREEN_NAME=name, LOGIN_RESULT=true, POINTS=pts, POINTS_BALANCE_RESULT=true}
   */
  public LoginResult login(Object[] pParams) {
    String method = "points.pointsBalance";

    return new LoginResult(sendToCoke(method, pParams));
  }

  /**
   *  Method to submit code to MyCoke rewards
   * @param pParams parameters needed to talk to cokeRewards, use createSubmitCodeParams to create correct params
   * @return result , Map {'LOGIN_RESULT': 'true', 'ENTER_CODE_RESULT': 'false', 'MESSAGES': ['Code already redeemed.'], 'SCREEN_NAME': 'name'}
   */
  public SubmitResult submitCode(Object[] pParams) {
    String    method = "points.enterCode";
    Map<?, ?> result = sendToCoke(method, pParams);

    return new SubmitResult(result);
  }

  /**
   * Method description
   *
   *
   * @param method, method to call on MCR (login or submit)
   * @param pParams, Parameters needed by MCR
   *
   * @return result, returned from MCR and converted to Map
   */
  @SuppressWarnings("unchecked")
  private Map<?, ?> sendToCoke(String method, Object[] pParams) {
    try {
      Object[]  res    = (Object[]) client.execute(method, pParams);
      Object    re     = res[0];
      Map<?, ?> result = (HashMap<?, ?>) re;

      return result;
    } catch (XmlRpcException e) {
      e.printStackTrace();

      return null;
    }
  }

  /**
   *  Used to create the parameter object for Login
   * @param email string for the email account @ CokeRewards
   * @param password string for password for account @ CokeRewards
   * @param ver string for version, "3.0" @ time of writing
   * @return parms, map of the supplied parameters in correct format/type
   */
  public Object[] createLoginParams(String email, String password, String ver) {
    Map<String, String> pParams = new HashMap<String, String>();

    pParams.put("emailAddress", email);
    pParams.put("password", password);
    pParams.put("VERSION", ver);

    return new Object[] { (Object) pParams, };
  }

  /**
   * Used to create the parameter object for SubmitCode
   *
   * @param email string for the email account @ CokeRewards
   * @param password string for password for account @ CokeRewards
   * @param ver string for version, "3.0" @ time of writing
   * @param code string of the code to be submitted
   * @return parms, map of the supplied parameters in correct format/type
   */
  public Object[] createSubmitCodeParams(String email, String password, String ver, String code) {
    Map<String, String> pParams = new HashMap<String, String>();

    pParams.put("emailAddress", email);
    pParams.put("password", password);
    pParams.put("capCode", code);
    pParams.put("VERSION", ver);

    return new Object[] { (Object) pParams, };
  }

  /**
   *
   * @param args,
   */
  public static void main(String[] args) {
    McrClient    mcr     = new McrClient();
    Object[]     pParams = mcr.createLoginParams("", "", "");
    LoginResult  l       = mcr.login(pParams);
    SubmitResult t       = mcr.submitCode(mcr.createSubmitCodeParams("", "", "3.0", ""));
    System.out.println(l.toString() + "\n" + t.toString());
  }
}

