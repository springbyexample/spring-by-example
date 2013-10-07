package ua.com.springbyexample.fragment;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;
import ua.com.springbyexample.R;
import ua.com.springbyexample.activity.EditActivity;
import ua.com.springbyexample.dao.DBConsts;
import ua.com.springbyexample.dao.EmployeeDAO;
import ua.com.springbyexample.dao.model.Employee;
import ua.com.springbyexample.dao.provider.EmployeeContentProvider;

import static ua.com.springbyexample.dao.DBConsts.Columns.*;
import static ua.com.springbyexample.dao.provider.EmployeeContentProvider.CONTENT_URI_EMPLOYEE;

/**
 * {@link Fragment} responsible for {@link Employee} create/edit operations
 *
 * @author akaverin
 */
public class EditFragment extends Fragment {

    private EditText firstNameEdit;
    private EditText secondNameEdit;
    private EditText projectEdit;
    private ModeStrategy currentMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        View rootView = getView();
        firstNameEdit = (EditText) rootView.findViewById(R.id.firstNameEdit);
        secondNameEdit = (EditText) rootView.findViewById(R.id.secondNameEdit);
        projectEdit = (EditText) rootView.findViewById(R.id.projectEdit);

        chooseModeStrategy();
        currentMode.onActivityCreated();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_abmenu, menu);
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
        values.put(FIRST_NAME, firstNameEdit.getText()
                .toString());
        values.put(SECOND_NAME, secondNameEdit
                .getText().toString());
        values.put(PROJECT, projectEdit.getText()
                .toString());

        currentMode.onFinishedEdit(values);

        Toast.makeText(getActivity(), R.string.success_save_toast,
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

    private void chooseModeStrategy() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.containsKey(EditActivity.EXTRA_EDIT_ID)) {
            currentMode = new EditModeStrategy();
        } else {
            currentMode = new CreateModeStrategy();
        }
    }

    /**
     * Handy interface for internal behavior<br>
     * Strategy pattern
     *
     * @author akaverin
     */
    private interface ModeStrategy {
        void onActivityCreated();

        void onFinishedEdit(ContentValues values);
    }

    private final class EditModeStrategy implements ModeStrategy {

        public void onActivityCreated() {
            getActivity().getActionBar().setTitle("Edit Employee");

            // Load existing entity data
            Bundle extras = getActivity().getIntent().getExtras();
            Cursor cursor = getActivity().getContentResolver().query(
                    CONTENT_URI_EMPLOYEE, null, DBConsts._ID + "=?",
                    new String[]{getEditId(extras)}, null);
            Employee editEntity = EmployeeDAO.load(cursor).get(0);

            firstNameEdit.setText(editEntity.getFirstName());
            secondNameEdit.setText(editEntity.getLastName());
            projectEdit.setText(editEntity.getProject());
        }

        public void onFinishedEdit(ContentValues values) {
            ContentResolver contentResolver = getActivity()
                    .getContentResolver();

            // TODO: check for unchanged data

            // TODO: check existing status?
            values.put(SYNC_STATUS,
                    DBConsts.SYNC_STATUS.UPDATE.name());
            contentResolver.update(EmployeeContentProvider.CONTENT_URI_EMPLOYEE, values,
                    _ID + " = ?",
                    new String[]{getEditId(getActivity().getIntent()
                            .getExtras())});
        }

        private String getEditId(Bundle extras) {
            return Long.toString(extras.getLong(EditActivity.EXTRA_EDIT_ID));
        }
    }

    private final class CreateModeStrategy implements ModeStrategy {

        public void onActivityCreated() {
            getActivity().getActionBar().setTitle("Create new Employee");
        }

        public void onFinishedEdit(ContentValues values) {
            ContentResolver contentResolver = getActivity()
                    .getContentResolver();
            values.put(SYNC_STATUS, DBConsts.SYNC_STATUS.CREATE.name());
            contentResolver.insert(CONTENT_URI_EMPLOYEE, values);
        }

    }

}
