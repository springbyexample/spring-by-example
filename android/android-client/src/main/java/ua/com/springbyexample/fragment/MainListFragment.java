package ua.com.springbyexample.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;
import de.akquinet.android.androlog.Log;
import ua.com.springbyexample.R;
import ua.com.springbyexample.SpringApplication;
import ua.com.springbyexample.activity.EditActivity;
import ua.com.springbyexample.activity.SettingsActivity;
import ua.com.springbyexample.dao.DBConsts;
import ua.com.springbyexample.dao.model.Employee;
import ua.com.springbyexample.dao.provider.EmployeeContentProvider;

import static ua.com.springbyexample.dao.DBConsts.Columns.SYNC_STATUS;
import static ua.com.springbyexample.dao.provider.EmployeeContentProvider.CONTENT_URI_EMPLOYEE;

/**
 * Employees list fragment. Allows to perform some CRUD and provides menu for
 * main {@link ActionBar}
 *
 * @author akaverin
 */
public class MainListFragment extends ListFragment implements
        LoaderCallbacks<Cursor>, OnItemClickListener {

    private static final NameBinder VIEW_BINDER = new NameBinder();
    private final MultiChoiseHandler CHOISE_HANDLER = new MultiChoiseHandler();
    private SpringApplication application;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        application = (SpringApplication) activity.getApplication();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        setupListView();
        setupAdapter();
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
                startActivity(EditActivity.class);
                return true;

            case R.id.menuItemRefresh:
                onRefresh();
                return true;

            case R.id.menuItemSettings:
                startActivity(SettingsActivity.class);
                return true;

            case R.id.menuItemCache:
                getActivity().getContentResolver().delete(
                        EmployeeContentProvider.CONTENT_URI_EMPLOYEE, null, null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO: consider reuse startActivity...
        Intent intent = new Intent(getActivity(), EditActivity.class);
        intent.putExtra(EditActivity.EXTRA_EDIT_ID, id);
        getActivity().startActivity(intent);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i("onCreateLoader");
        CursorLoader loader = new CursorLoader(getActivity(),
                CONTENT_URI_EMPLOYEE, null, SYNC_STATUS + " != ?",
                new String[]{DBConsts.SYNC_STATUS.REMOVE.name()}, null);
        return loader;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.i("onLoadFinished");
        ((SimpleCursorAdapter) getListAdapter()).swapCursor(cursor);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        ((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
    }

    private void onRefresh() {
        application.getSyncManager().startSync();
    }

    private void startActivity(Class<? extends Activity> activityToStart) {
        getActivity().startActivity(new Intent(getActivity(), activityToStart));
    }

    private void setupAdapter() {
        String[] from = new String[]{DBConsts.Columns.SECOND_NAME,
                DBConsts.Columns.PROJECT};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_activated_2, null, from, to,
                0);
        adapter.setViewBinder(VIEW_BINDER);
        setListAdapter(adapter);
    }

    private void setupListView() {
        ListView listView = getListView();
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(CHOISE_HANDLER);
    }

    /**
     * Helper class to force {@link SimpleCursorAdapter} show merged
     * {@link Employee} name as FirstName + SecondName single value
     *
     * @author akaverin
     */
    private static final class NameBinder implements ViewBinder {
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            if (android.R.id.text1 == view.getId()) {
                String result = cursor.getString(cursor
                        .getColumnIndex(DBConsts.Columns.FIRST_NAME))
                        + " " + cursor.getString(columnIndex);
                ((TextView) view).setText(result);
                return true;
            }
            return false;
        }
    }

    /**
     * Helper class to handle List Edit mode
     *
     * @author akaverin
     */
    private final class MultiChoiseHandler implements MultiChoiceModeListener {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.action_abmenu, menu);
            mode.setTitle(R.string.actionModeTitle);

            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menuItemDelete:
                    markCheckedItemsForDeletion();
                    mode.finish();
                    return true;

                case R.id.menuItemSelect:
                    setCheckedAll();
                    return true;

                case R.id.menuItemToggle:
                    toggleCheckedItems();
                    return true;
            }
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }

        public void onItemCheckedStateChanged(ActionMode mode, int position,
                                              long id, boolean checked) {
            final int checkedCount = getListView().getCheckedItemCount();
            switch (checkedCount) {
                case 0:
                    mode.setSubtitle(null);
                    break;
                case 1:
                    mode.setSubtitle(R.string.actionModeSubtitle1);
                    break;
                default:
                    mode.setSubtitle("" + checkedCount
                            + getString(R.string.actionModeSubtitle2));
                    break;
            }
        }

        private void toggleCheckedItems() {
            ListView listView = getListView();
            for (int i = 0; i < listView.getCount(); ++i) {
                listView.setItemChecked(i, !listView.isItemChecked(i));
            }
        }

        private void setCheckedAll() {
            ListView listView = getListView();
            for (int i = 0; i < listView.getCount(); ++i) {
                listView.setItemChecked(i, true);
            }
        }

        private void markCheckedItemsForDeletion() {
            ContentValues newValues = new ContentValues();
            newValues.put(DBConsts.Columns.SYNC_STATUS,
                    DBConsts.SYNC_STATUS.REMOVE.name());

            long[] ids = getListView().getCheckedItemIds();
            String[] strIds = new String[ids.length + 1];
            strIds[0] = DBConsts.SYNC_STATUS.CREATE.name();
            for (int i = 0; i < ids.length; ++i) {
                strIds[i + 1] = Long.toString(ids[i]);
            }
            String whereById = DBConsts._ID + " in ("
                    + buildPlaceholders(ids.length) + ")";

            //Delete from cached entities unsent to server
            int deleteCnt = getActivity().getContentResolver().delete(CONTENT_URI_EMPLOYEE,
                    SYNC_STATUS + "=? AND " + whereById, strIds);
            //nothing to update
            if (deleteCnt == ids.length) {
                return;
            }
            getActivity().getContentResolver().update(
                    EmployeeContentProvider.CONTENT_URI_EMPLOYEE,
                    newValues, SYNC_STATUS + "!=? AND " + whereById, strIds);
        }

        private String buildPlaceholders(int length) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; ++i) {
                builder.append("?").append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            return builder.toString();
        }

    }

}
