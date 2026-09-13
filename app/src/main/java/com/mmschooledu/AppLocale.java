package com.mmschooledu;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/** Centralized, backward-compatible language preference and resource locale helper. */
public final class AppLocale {
    public static final String PREFS_NAME = "translate";
    public static final String PREF_LANGUAGE_ID = "id";
    public static final int MYANMAR_ID = 0;
    public static final int ENGLISH_ID = 1;

    private AppLocale() {
    }

    public static int getLanguageId(Context context) {
        if (context == null) {
            return ENGLISH_ID;
        }
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(PREF_LANGUAGE_ID, ENGLISH_ID);
    }

    public static void saveLanguage(Context context, int languageId) {
        if (context == null) {
            return;
        }
        int normalizedId = languageId == MYANMAR_ID ? MYANMAR_ID : ENGLISH_ID;
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(PREF_LANGUAGE_ID, normalizedId)
                .apply();
        applyTo(context, localeTagFor(normalizedId));
    }

    public static String localeTagFor(int languageId) {
        return languageId == MYANMAR_ID ? "my" : "en";
    }

    public static void applySaved(Context context) {
        if (context != null) {
            applyTo(context, localeTagFor(getLanguageId(context)));
        }
    }

    public static Context apply(Context context, String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    public static void applyTo(Context context, String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
