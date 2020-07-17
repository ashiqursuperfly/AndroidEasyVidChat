package com.ashiqurrahman.easyvidchat.rtc_util;
/*
 * Created by :
 * <a href="https://www.github.com/ashiqursuperfly">Ashiqur Rahman</a> on 7/17/20.
 */

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CallDurationUtil {
    public static String convertToFormattedString(long millis) {
        return String.format(Locale.getDefault(),"%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
        );
    }

}
