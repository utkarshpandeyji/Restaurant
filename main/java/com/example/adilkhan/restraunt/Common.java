package com.example.adilkhan.restraunt;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.adilkhan.restraunt.Model.UserLogin;

public class Common {

    public static UserLogin currentUser;
    public static final String USER_KEY = "User";
    public static final String PWD_JEY = "Password";

    public static String convertcodetostatus(String status) {

        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On My Way";
        else
            return "Shipped";
    }

    public static final String DELETE = "Delete";

    public static boolean isConnectedtoInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
            {
                for (int i=0; i<info.length;i++)
                {
                    if(info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }



}
