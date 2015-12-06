package ru.gushi.android.sunshine.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Helper class for settings handling.
 */
public class Settings {
    /**
     * Get string setting from default shared preferences.
     *
     * @param keyStringId          id of a string containing the preference key
     * @param defaultValueStringId id of a string containing default value
     * @return Returns saved preference
     */
    public static String getStringSetting(int keyStringId, int defaultValueStringId,
                                          Context context) {
        final SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(context.getString(keyStringId),
                                     context.getString(defaultValueStringId));
    }
}
