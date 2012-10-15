package ua.com.springbyexample.activity;

import ua.com.springbyexample.R;
import android.os.Bundle;

public class EditActivity extends BaseActivity {
	public static final String EXTRA_EDIT_ID = "EXTRA_EDIT_ID";

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_activity);
	}
}
