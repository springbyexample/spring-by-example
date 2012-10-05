package ua.com.springbyexample.fragment;

import ua.com.springbyexample.R;
import ua.com.springbyexample.activity.EditItemActivity;
import ua.com.springbyexample.activity.SettingsActivity;
import ua.com.springbyexample.dao.DBConsts;
import ua.com.springbyexample.dao.EmployeeProvider;
import ua.com.springbyexample.dao.model.Employee;
import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class ItemsListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final NameBinder VIEW_BINDER = new NameBinder();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

		String[] from = new String[] { DBConsts.FIELD_FNAME,
				DBConsts.FIELD_SNAME, DBConsts.FIELD_PROJECT

		};
		int[] to = new int[] { 0, android.R.id.text1, android.R.id.text2 };

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_2, null, from, to, 0);
		adapter.setViewBinder(VIEW_BINDER);
		setListAdapter(adapter);

		getLoaderManager().initLoader(0, null, this);
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

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(getActivity(),
				EmployeeProvider.CONTENT_URI, null, null, null, null);
		return loader;
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		((SimpleCursorAdapter) getListAdapter()).swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
	}

	/**
	 * Helper class to force {@link SimpleCursorAdapter} show merged
	 * {@link Employee} name as FirstName + SecondName single value
	 * 
	 * @author akaverin
	 * 
	 */
	private static final class NameBinder implements ViewBinder {
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if (android.R.id.text1 == view.getId()) {
				String result = cursor.getString(columnIndex - 1) + " "
						+ cursor.getString(columnIndex);
				((TextView) view).setText(result);
				return true;
			}
			return false;
		}
	}

}
