package edu.aku.hassannaqvi.toic_hhlisting.Contracts;

import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 4/27/2016.
 */
public class UsersContract {
    private static final String TAG = "Users_CONTRACT";
    Long _ID;
    String ROW_USERNAME;
    String ROW_PASSWORD;
    String ROW_TEAM;

    public UsersContract() {
        // Default Constructor
    }

    public UsersContract(String username, String password) {
        this.ROW_PASSWORD = password;
        this.ROW_USERNAME = username;
    }

    public UsersContract sync(JSONObject jsonObject) throws JSONException {
        this.ROW_USERNAME = jsonObject.getString(singleUser.ROW_USERNAME);
        this.ROW_PASSWORD = jsonObject.getString(singleUser.ROW_PASSWORD);
        this.ROW_TEAM = jsonObject.getString(singleUser.ROW_TEAM);

        return this;
    }

    public Long getUserID() {
        return this._ID;
    }

    public void setId(int id) {
        this._ID = Long.valueOf(id);
    }

    public String getUserName() {
        return this.ROW_USERNAME;
    }

    public void setUserName(String username) {
        this.ROW_USERNAME = username;
    }

    public String getPassword() {
        return this.ROW_PASSWORD;
    }

    public void setPassword(String password) {
        this.ROW_PASSWORD = password;
    }

    public String getROW_TEAM() {
        return ROW_TEAM;
    }

    public void setROW_TEAM(String ROW_TEAM) {
        this.ROW_TEAM = ROW_TEAM;
    }

    public static abstract class singleUser implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String _ID = "id";
        public static final String ROW_USERNAME = "username";
        public static final String ROW_PASSWORD = "password";
        public static final String ROW_TEAM = "team";

        public static final String _URI = "users.php";
    }
}