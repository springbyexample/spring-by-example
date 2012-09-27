package ua.com.springbyexample.test;

import ua.com.springbyexample.activity.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest() {
		super(MainActivity.class);
	}

	public void testActivity() {
		MainActivity activity = getActivity();
		assertNotNull(activity);
	}
}
