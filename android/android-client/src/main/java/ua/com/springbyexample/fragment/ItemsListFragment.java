package ua.com.springbyexample.fragment;

import ua.com.springbyexample.R;
import ua.com.springbyexample.activity.EditItemActivity;
import ua.com.springbyexample.activity.SettingsActivity;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ItemsListFragment extends ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.abmenu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menuItemAdd:
			startActivity(EditItemActivity.class);
			return true;

		case R.id.menuItemRefresh:
			Toast.makeText(getActivity(), getString(R.string.menuItemRefresh),
					Toast.LENGTH_SHORT).show();
			return true;

		case R.id.menuItemSettings:
			startActivity(SettingsActivity.class);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void startActivity(Class<? extends Activity> activityToStart) {
		getActivity().startActivity(new Intent(getActivity(), activityToStart));
	}

}
