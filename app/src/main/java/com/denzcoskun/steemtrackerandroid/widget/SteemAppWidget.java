package com.denzcoskun.steemtrackerandroid.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.denzcoskun.steemtrackerandroid.R;
import com.denzcoskun.steemtrackerandroid.helpers.DataHelper;
import com.denzcoskun.steemtrackerandroid.models.UserModel;

/**
 * Implementation of App Widget functionality.
 */
public class SteemAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        UserModel userModel;
        DataHelper dataHelper = new DataHelper(context);


        String steemText = context.getString(R.string.appwidget_steem_text);
        String sbdText = context.getString(R.string.appwidget_sbd_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.steem_app_widget);
        views.setTextViewText(R.id.widget_steem_type, steemText);
        views.setTextViewText(R.id.widget_sbd_type, sbdText);
        if(dataHelper.getModel() != null){
            userModel = dataHelper.getModel();
            views.setTextViewText(R.id.widget_profile_name, "@"+userModel.name);
            views.setTextViewText(R.id.widget_steem_value, userModel.balance);
            views.setTextViewText(R.id.widget_sbd_value, userModel.sbdBalance);
        }else {
            views.setTextViewText(R.id.widget_profile_name, context.getString(R.string.user_not_found));
            views.setTextViewText(R.id.widget_steem_value, context.getString(R.string.steem_not_found));
            views.setTextViewText(R.id.widget_sbd_value, context.getString(R.string.sbd_not_found));
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

