package com.demo.collegeerp.utils;

import android.content.Context;

import com.demo.collegeerp.R;
import com.demo.collegeerp.models.MenuItems;

import java.util.ArrayList;

public class Helper {

    public static ArrayList<MenuItems> getServiceOptions(Context context, String type) {
        ArrayList<MenuItems> menuArrayList = new ArrayList<>();
        if (type.equals(Constants.ADMIN)) {
            menuArrayList.add(new MenuItems(Constants.USER_MANAGEMENT, context.getString(R.string.user_management), R.drawable.baseline_person_24, true));
        }

        if (type.equals(Constants.ADMIN)) {
            menuArrayList.add(new MenuItems(Constants.BUS_MANAGEMENT, context.getString(R.string.bus_management), R.drawable.baseline_feedback_24, true));
        } else if (type.equals(Constants.PARENT) || type.equals(Constants.STUDENT)) {
            menuArrayList.add(new MenuItems(Constants.BUS_TRACKING, context.getString(R.string.bus_tracking), R.drawable.baseline_feedback_24, true));
        } else if (type.equals(Constants.DRIVER)) {
            menuArrayList.add(new MenuItems(Constants.ROUTE_DETAILS, context.getString(R.string.route_details), R.drawable.baseline_route_24, true));
        }

        if (type.equals(Constants.ADMIN)) {
            menuArrayList.add(new MenuItems(Constants.ROUTE_PLANNING, context.getString(R.string.route_planning), R.drawable.baseline_route_24, true));
        }

        if (type.equals(Constants.ADMIN) || type.equals(Constants.STUDENT) || type.equals(Constants.DRIVER)) {
            menuArrayList.add(new MenuItems(Constants.EMERGENCY, context.getString(R.string.emergency_alters), R.drawable.baseline_person_24, true));
        }

        if (type.equals(Constants.ADMIN)) {
            menuArrayList.add(new MenuItems(Constants.ANALYTICS, context.getString(R.string.analytics), R.drawable.baseline_view_list_24, true));
        }

        if (type.equals(Constants.PARENT) || type.equals(Constants.DRIVER)) {
            menuArrayList.add(new MenuItems(Constants.ATT_MANAGEMENT, context.getString(R.string.att_tracking), R.drawable.baseline_calendar_month_24, true));
        } else if (type.equals(Constants.STUDENT)) {
            menuArrayList.add(new MenuItems(Constants.ATT_MANAGEMENT, context.getString(R.string.att_confirmation), R.drawable.baseline_calendar_month_24, true));
        }

        menuArrayList.add(new MenuItems(Constants.LOGOUT, context.getString(R.string.logout), R.drawable.baseline_logout_24, true));


        return menuArrayList;
    }

}
