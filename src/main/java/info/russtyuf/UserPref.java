package info.russtyuf;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.Serializable;
import java.util.prefs.Preferences;

/**
 * Store and retrieve user preferences
 */
public class UserPref implements Serializable {
  private final String version = "3.0";
  private String       email;
  private String       password;
  private File         codeFile;
  private Preferences  prefs;

  /**
   * Constructs ...
   *
   */
  public UserPref() {
    prefs = Preferences.userNodeForPackage(UserPref.class);
  }

  /**
   * Getter
   * @return version, used for MCR
   */
  public String getVersion() {
    return version;
  }

  /**
   * Getter
   * @return password, user's password for MCR
   */
  public String getPassword() {
    return prefs.get("password", null);
  }

  /**
   * Setter
   * @param password, update user password
   */
  public void setPassword(String password) {
    prefs.put("password", password);
  }

  /**
   * Getter
   *
   * @return email, email address used with MCR
   */
  public String getEmail() {
    return prefs.get("email", null);
  }

  /**
   * Setter
   * @param email, updates user email address
   */
  public void setEmail(String email) {
    prefs.put("email", email);
  }

  public File getCodeFile() {
    String fileString = prefs.get("codeFile", "codes.mycr");
    return new File(fileString);
  }

  public void setCodeFile(File codeFile) {
    String fileString = codeFile.getPath();
    prefs.put("codeFile", fileString);
  }
}
