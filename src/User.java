
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private final HashMap<String, String> data;

    public User(HashMap<String, String> data) {
        this.data = data;
    }

    String checkExist() {
        String users = DataBase.getInstance().getController("ClientAccounts").readFile();
        String[] split = users.split("\n");
        for (String str : split) {
            String[] detail = str.split(";");
            if(detail.length>1) {
                if (detail[1].equals(data.get("username"))) {
                    return str;
                }
            }
        }
        return "invalid";
    }


    String signUp() {
        String invalidMsg = "";
        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = email.matcher(data.get("email"));
        if (!matcher.find()) {
            invalidMsg += "invalid email";
        }
        String user = checkExist();
        if (!user.equals("invalid")) {
            if (invalidMsg.equals(""))
                invalidMsg += "invalid username";
            else
                invalidMsg += ";invalid username";
        }

        if (!data.get("password").matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")) {
            if (invalidMsg.equals(""))
                invalidMsg += "invalid password";
            else
                invalidMsg += ";invalid password";
        }
        if (invalidMsg.equals("")) {
            DataBase.getInstance().getController("ClientAccounts").writeFile(data.get("email")
                    + ";" + data.get("username") + ";" + data.get("password") + ";null" + "\n");
            return "valid";
        }
        return invalidMsg;
    }

    String logIn() {
        String check = checkExist();
        if (check.equals("invalid")) {
            return "invalid username";
        } else if (!check.split(";")[2].equals(data.get("password"))) {
            return "invalid password";
        }
        return "valid";
    }


}
