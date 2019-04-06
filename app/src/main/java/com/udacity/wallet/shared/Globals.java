package com.udacity.wallet.shared;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

public class Globals {

    private static Context applicationContext;


    private Globals() {

    }

    public static Context getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(Context applicationContext) {
        Globals.applicationContext = applicationContext;
    }

    @Nullable
    public FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    public Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }



    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        Timber.i("email isValid: "+isValid);
        return isValid;
    }

    public static long printDifference(Date startDate, Date endDate) {
        long elapsedDays = 0;
        long different = 0;
        //milliseconds
        if(null != startDate && null != endDate){
            different = endDate.getTime() - startDate.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            elapsedDays = different / daysInMilli;
            different = different % daysInMilli;
            if(elapsedDays >= 0 && elapsedDays <1){
                if(endDate.getDay() != startDate.getDay()){
                    elapsedDays = 1;
                }
            }
        }


        return elapsedDays;
    }

    public static String getMonth(int intMonth){

        switch (intMonth){
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default:return "";
        }

    }

    public static int totalTime(String hour, String minute){
        if("".equals(minute) && "".equals(hour)){
            return 0;
        }else  if("".equals(minute) && !"".equals(hour)){
            return Integer.parseInt(hour)*60;
        }else if("".equals(hour) && !"".equals(minute)){
            return Integer.parseInt(minute);
        }else{
            return Integer.parseInt(hour)*60 + Integer.parseInt(minute);
        }
    }


}
