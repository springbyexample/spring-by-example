package ua.com.springbyexample.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Class with common logic for all {@link Activity} implementations
 *
 * @author akaverin
 */
abstract class BaseActivity extends Activity {

    /**
     * reference to manipulate the {@link ActionBar}
     */
    ActionBar actionBar = null;

    /**
     * @see android.app.Activity#onCreate(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get reference to the action bar
        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // TODO: re-use to notify last UPDATE time stamp...
        // actionBar.setSubtitle(getString(R.string.actionBarSubtitle));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // when app icon in action bar is clicked, show home/main activity
                Intent intent = new Intent(this,
                        ua.com.springbyexample.activity.MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
