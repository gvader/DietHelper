package com.gvader.diethelper.ui.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.view.MenuItem;
import android.view.View;

import com.gvader.diethelper.MyApplication;
import com.gvader.diethelper.R;


public class SettingsActivity extends AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartScreenCallback {
    private static final String TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // load settings fragment
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragmentCompat {
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.pref_main);

            // feedback preference click listener
            Preference feedbackNotificationPref = findPreference(getString(R.string.pref_key_send_feedback));
            feedbackNotificationPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    sendFeedback(getActivity());
                    return true;
                }
            });
        }

        @Override
        public Fragment getCallbackFragment() {
            return this;
        }

        /**/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Fix for launching subscreen using PreferenceFragmentCompat:
     * https://stackoverflow.com/questions/34701740/preference-sub-screen-not-opening-when-using-support-v7-preference
     *
     * @param preferenceFragmentCompat Fragment on behalf which to launch subscreen.
     * @param preferenceScreen Preference screen to be launch.
     * @return status code
     */
    @Override
    public boolean onPreferenceStartScreen(
            PreferenceFragmentCompat preferenceFragmentCompat,
            PreferenceScreen preferenceScreen
    ) {
        preferenceFragmentCompat.setPreferenceScreen(preferenceScreen);
        return true;
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferencesSummaryToValueListener);

        sBindPreferencesSummaryToValueListener.onPreferenceChange(
                preference,
                PreferenceManager
                    .getDefaultSharedPreferences(preference.getContext())
                    .getString(preference.getKey(), "")
        );
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferencesSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();

            if (preference instanceof EditTextPreference) {
                String key = preference.getKey();
                if (key.equals(MyApplication.getInstance().getAppContext().getString(R.string.pref_key_meal_list_meal_number_per_day)) ||
                        key.equals(MyApplication.getInstance().getAppContext().getString(R.string.pref_key_water_widget_amount))) {
                    preference.setSummary(stringValue);
                }
            } else {
                preference.setSummary(stringValue);
            }

            return true;
        }
    };

    /**
     * Email client intent to send support mail
     * Appends the necessary device information to email body
     * useful when providing support
     */
    public static void sendFeedback(Context context) {
        String body = null;
        try {
            body = context
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
            body = "\n\n-----------------------------------\nPlease don't remove this" +
                    "information\n Device OS: Android \n Device os version: " +
                    Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " +
                    Build.BRAND + "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " +
                    Build.MANUFACTURER;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"lukasz.gwadera@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Query from android app");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.pref_choose_email_client)));
    }
}
