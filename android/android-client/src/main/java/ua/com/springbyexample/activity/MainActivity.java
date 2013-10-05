package ua.com.springbyexample.activity;

import android.app.Activity;
import android.os.Bundle;
import ua.com.springbyexample.R;

/**
 * Application default {@link Activity}
 *
 * @author akaverin
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // TODO: fix or leave APP only title, as it has different text which
        // noticeably changed during app initialization
        getActionBar().setTitle(R.string.actionBarTitle);
    }

}