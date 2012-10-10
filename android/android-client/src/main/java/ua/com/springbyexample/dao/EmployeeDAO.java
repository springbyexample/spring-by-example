package ua.com.springbyexample.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.com.springbyexample.dao.model.Employee;
import ua.com.springbyexample.dao.provider.EmployeeContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * DAO layer to provider {@link Employee} POJO to {@link EmployeeProvider}
 * bridge. Useful when we work with POJOs obtained from REST API layer
 * 
 * @author akaverin
 * 
 */
public class EmployeeDAO {

	public static void save(Context context, List<Employee> employees) {

		ContentValues[] bulkValues = new ContentValues[employees.size()];
		for (int i = 0; i < employees.size(); ++i) {
			Employee employee = employees.get(i);
			bulkValues[i] = employeeToValues(employee);
		}

		context.getContentResolver().bulkInsert(
				EmployeeContentProvider.CONTENT_URI, bulkValues);
	}

	private static ContentValues employeeToValues(Employee employee) {
		ContentValues values = new ContentValues();
		values.put(EmployeeContentProvider.ID, employee.getId());
		values.put(EmployeeContentProvider.FIRST_NAME, employee.getFirstName());
		values.put(EmployeeContentProvider.SECOND_NAME, employee.getLastName());
		values.put(EmployeeContentProvider.PROJECT, employee.getProject());
		return values;
	}

	public static List<Employee> load(Cursor cursor) {
		if (cursor.getCount() == 0) {
			return Collections.emptyList();
		}
		ArrayList<Employee> employees = new ArrayList<Employee>(
				cursor.getCount());

		cursor.moveToFirst();
		do {
			Employee employee = new Employee();
			employee.setFirstName(getStringField(cursor,
					EmployeeContentProvider.FIRST_NAME));
			employee.setLastName(getStringField(cursor,
					EmployeeContentProvider.SECOND_NAME));
			employee.setProject(getStringField(cursor,
					EmployeeContentProvider.PROJECT));
			long serverId = cursor.getLong(cursor
					.getColumnIndex(EmployeeContentProvider.ID));
			if (serverId > 0) {
				employee.setId(serverId);
			}

			employees.add(employee);

		} while (cursor.moveToNext());

		//return Collections.unmodifiableList(employees);
		return employees;
	}

	private static String getStringField(Cursor cursor, String columnName) {
		return cursor.getString(cursor.getColumnIndex(columnName));
	}

}
