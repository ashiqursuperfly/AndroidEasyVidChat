package com.ashiqurrahman.easyvidchat.ui;

/*
 * Created by :
 * <a href="https://www.github.com/ashiqursuperfly">Ashiqur Rahman</a> on 5/27/20.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.Window;
import android.widget.Button;

import com.ashiqurrahman.easyvidchat.data.VidChatConfig;

public class UiUtil {

    /**@param task -Task performed on Button Dialog Click**/
    public static void showAlertDialog(final Activity activity,final String title,final String msg,final String btnText,DialogInterface.OnClickListener task)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        String html = "<font color='HEX'>TEXT</font>";

        String htmlTitle = html.replace("HEX",VidChatConfig.AlertDialogUI.INSTANCE.getTitleTextColorHexSixDigitString()).replace("TEXT",title);
        alertDialog.setTitle(Html.fromHtml(htmlTitle));
        String htmlMsg = html.replace("HEX",VidChatConfig.AlertDialogUI.INSTANCE.getTextMsgColorHexSixDigitString()).replace("TEXT",msg);
        alertDialog.setMessage(Html.fromHtml(htmlMsg));
        alertDialog.setIcon(VidChatConfig.AlertDialogUI.INSTANCE.getIconDrawableRes());
        alertDialog.setCancelable(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, btnText, task);
        alertDialog.setOnShowListener(arg0 -> {

            GradientDrawable shape =  new GradientDrawable();
            shape.setCornerRadius(8f);
            shape.setColor(activity.getResources().getColor(VidChatConfig.AlertDialogUI.INSTANCE.getBtnBgColorRes()));

            Button btn =alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            btn.setTextColor(activity.getResources().getColor(VidChatConfig.AlertDialogUI.INSTANCE.getBtnTextColorRes()));
            btn.setBackground(shape);


            GradientDrawable shape2 =  new GradientDrawable();
            shape2.setCornerRadius(16f);

            shape2.setColor(activity.getResources().getColor(VidChatConfig.AlertDialogUI.INSTANCE.getAlertDialogBgColorRes()));
            Window window = alertDialog.getWindow();

            if (window != null) {
                window.setBackgroundDrawable(shape2);
            }
            //if(window != null)window.setBackgroundDrawableResource(VidChatConfig.AlertDialogUI.INSTANCE.getAlertDialogBgColorRes());

        });
        alertDialog.show();


    }
}
