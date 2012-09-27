package ua.com.springbyexample.activity;

import ua.com.springbyexample.R;
import android.os.Bundle;

public class EditItemActivity extends BaseActivity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_item_activity);
	}
}
