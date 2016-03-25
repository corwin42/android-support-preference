package net.xpece.android.support.preference.sample;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.xpece.android.support.preference.PreferenceScreenNavigationStrategy;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p></p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatActivity implements
    PreferenceFragmentCompat.OnPreferenceStartScreenCallback,
    FragmentManager.OnBackStackChangedListener,
    PreferenceScreenNavigationStrategy.ReplaceFragment.Callbacks {

    Toolbar mToolbar;

    private SettingsFragment mSettingsFragment;

    private PreferenceScreenNavigationStrategy.ReplaceFragment mReplaceFragmentStrategy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mReplaceFragmentStrategy = new PreferenceScreenNavigationStrategy.ReplaceFragment(this, R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out);

        if (savedInstanceState == null) {
            mSettingsFragment = SettingsFragment.newInstance(null);
            getSupportFragmentManager().beginTransaction().add(R.id.content, mSettingsFragment, "Settings").commit();
        } else {
            mSettingsFragment = (SettingsFragment) getSupportFragmentManager().findFragmentByTag("Settings");
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreferenceStartScreen(final PreferenceFragmentCompat preferenceFragmentCompat, final PreferenceScreen preferenceScreen) {
        mReplaceFragmentStrategy.onPreferenceStartScreen(getSupportFragmentManager(), preferenceFragmentCompat, preferenceScreen);
        return true;
//        return false; // Turn off to try ReplaceRoot strategy.
    }

    @Override
    public void onBackStackChanged() {
        mSettingsFragment = (SettingsFragment) getSupportFragmentManager().findFragmentByTag("Settings");
    }

    @Override
    public PreferenceFragmentCompat onBuildPreferenceFragment(final String rootKey) {
        return SettingsFragment.newInstance(rootKey);
    }

    @Override
    public void onBackPressed() {
        // Use with ReplaceRoot strategy.
//        if (mSettingsFragment.onBackPressed()) return;

        super.onBackPressed();
    }
}
