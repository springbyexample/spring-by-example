package ua.com.springbyexample.fragment;

import ua.com.springbyexample.R;
import ua.com.springbyexample.dao.DBConsts;
import ua.com.springbyexample.dao.EmployeeProvider;
import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
		
		//TODO: add Edit mode support...
		
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

		ContentValues values = new ContentValues(3);
		values.put(DBConsts.FIELD_FNAME, firstNameEdit.getText().toString());
		values.put(DBConsts.FIELD_SNAME, secondNameEdit.getText().toString());
		values.put(DBConsts.FIELD_PROJECT, projectEdit.getText().toString());

		getActivity().getContentResolver().insert(EmployeeProvider.CONTENT_URI,
				values);

		Toast.makeText(getActivity(),
				"Item successfully added and will be uploaded in a moments.",
				Toast.LENGTH_LONG).show();
		getActivity().finish();
	}

	private boolean isInputInvalid() {
		if (validateField(firstNameEdit, "Please, provide first name")) {
			return true;
		}
		if (validateField(secondNameEdit, "Please, provide second name")) {
			return true;
		}
		return validateField(projectEdit, "Please, provide project name");
	}

	private boolean validateField(EditText editText, String errorMessage) {
		if (editText.getText().length() == 0) {
			editText.setError(errorMessage);
			return true;
		}
		return false;
	}

	public void afterTextChanged(Editable s) {
		if (s.length() == 0) {

		}
	}
}
