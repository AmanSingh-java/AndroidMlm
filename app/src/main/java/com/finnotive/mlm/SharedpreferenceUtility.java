package com.finnotive.mlm;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedpreferenceUtility {

    public static SharedPreferences mPref;
    private static SharedpreferenceUtility mRef;
    private Editor mEditor;
/*public static String CUSTOMER_ID = "customerId";
public static String CUSTOMER_TYPE = "type";*/

    /**
     * Singleton method return the instance
     **/
    public static SharedpreferenceUtility getInstance(Context context) {
        if (mRef == null) {
            mRef = new SharedpreferenceUtility();
            mPref = context.getApplicationContext().getSharedPreferences(
                    "MyPref", 0);
            return mRef;
        }
        return mRef;
    }

    /**
     * Put long value into sharedpreference
     **/
    public void putLong(String key, long value) {
        try {
            mEditor = mPref.edit();
            mEditor.putLong(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get long value from sharedpreference
     **/


    public void setAdsDate(String AdsDate) {
        mEditor.putString("AdsDate", AdsDate);
        mEditor.commit();
    }
    public String getAdsDate() {
        return mPref.getString("AdsDate", "");
    }
    public long getLong(String key) {
        try {
            long lvalue;
            lvalue = mPref.getLong(key, 0);
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public void putFloat(String key, Float value) {
        try {
            mEditor = mPref.edit();
            mEditor.putFloat(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get long value from sharedpreference
     **/
    public Float getFloat(String key) {
        try {
            Float lvalue;
            lvalue = mPref.getFloat(key, 0.0f);
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return 0f;
        }
    }

    /**
     * Put int value into sharedpreference
     **/
    public void putInt(String key, int value) {
        try {
            mEditor = mPref.edit();
            mEditor.putInt(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get int value from sharedpreference
     **/
    public int getInt(String key) {
        try {
            int lvalue;
            lvalue = mPref.getInt(key, 0);
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Put String value into sharedpreference
     **/
    public void putString(String key, String value) {
        try {
            mEditor = mPref.edit();
            mEditor.putString(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get String value from sharedpreference
     **/
    public String getString(String key) {
        try {
            String lvalue;
            lvalue = mPref.getString(key, "");
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Put String value into sharedpreference
     **/
    public void putBoolean(String key, Boolean value) {
        try {
            mEditor = mPref.edit();
            mEditor.putBoolean(key, value);
            mEditor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get String value from sharedpreference
     **/
    public Boolean getBoolean(String key) {
        try {
            Boolean lvalue;
            lvalue = mPref.getBoolean(key, false);
            return lvalue;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}