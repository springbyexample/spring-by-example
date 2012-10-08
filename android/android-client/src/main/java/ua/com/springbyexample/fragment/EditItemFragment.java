package ua.com.springbyexample.fragment;

import ua.com.springbyexample.R;
import ua.com.springbyexample.dao.DBConsts;
import ua.com.springbyexample.dao.model.Employee;
import ua.com.springbyexample.dao.provider.EmployeeContentProvider;
import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * {@link Fragment} responsible for {@link Employee} create/edit operations
 * 
 * @author akaverin
 * 
 */
public class EditItemFragment extends Fragment {

	EditText firstNameEdit;
	EditText secondNameEdit;
	EditText projectEdit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.edit_item_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

		View rootView = getView();
		firstNameEdit = (EditText) rootView.findViewById(R.id.firstNameEdit);
		secondNameEdit = (EditText) rootView.findViewById(R.id.secondNameEdit);
		projectEdit = (EditText) rootView.findViewById(R.id.projectEdit);

		// TODO: add Edit mode support...

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.edit_item_abmenu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuItemSave:
			onSaveItem();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void onSaveItem() {
		if (isInputInvalid()) {
			return;
		}

		ContentValues values = new ContentValues(4);
		values.put(EmployeeContentProvider.FIRST_NAME, firstNameEdit.getText().toString());
		values.put(EmployeeContentProvider.SECOND_NAME, secondNameEdit.getText().toString());
		values.put(EmployeeContentProvider.PROJECT, projectEdit.getText().toString());
		values.put(EmployeeContentProvider.SYNC_STATUS,
				DBConsts.SYNC_STATUS.CREATE.ordinal());

		getActivity().getContentResolver().insert(EmployeeContentProvider.CONTENT_URI,
				values);

		Toast.makeText(getActivity(), R.string.success_add_toast,
				Toast.LENGTH_LONG).show();
		getActivity().finish();
	}

	private boolean isInputInvalid() {
		if (validateField(firstNameEdit,
				getActivity().getString(R.string.invalid_fname_error))) {
			return true;
		}
		if (validateField(secondNameEdit,
				getActivity().getString(R.string.invalid_sname_error))) {
			return true;
		}
		return validateField(projectEdit,
				getActivity().getString(R.string.invalid_project_error));
	}

	private boolean validateField(EditText editText, String errorMessage) {
		if (editText.getText().length() == 0) {
			editText.setError(errorMessage);
			return true;
		}
		return false;
	}
}
