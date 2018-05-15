package com.gvader.diethelper.ui.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.gvader.diethelper.MyApplication;
import com.gvader.diethelper.R;


public class SettingsActivity extends AppCompatPreferenceActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
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

            // Version
            Preference appVersionPref = findPreference(getString(R.string.pref_key_app_version));
            appVersionPref.setSummary(MyApplication.getInstance().getAppVersion());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
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
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"lukasz.gwadera@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Query from android app");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.pref_choose_email_client)));
    }
}
