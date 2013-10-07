package ua.com.springbyexample.dao.provider;

import android.database.Cursor;
import android.test.ProviderTestCase2;
import android.test.suitebuilder.annotation.Suppress;
import ua.com.springbyexample.dao.EmployeeDAO;
import ua.com.springbyexample.dao.model.Employee;

import java.util.ArrayList;
import java.util.List;

import static ua.com.springbyexample.dao.provider.EmployeeContentProvider.CONTENT_URI_EMPLOYEE;

public class EmployeeContentProviderTest extends
        ProviderTestCase2<EmployeeContentProvider> {

    public EmployeeContentProviderTest() {
        super(EmployeeContentProvider.class, EmployeeContentProvider.AUTHORITY);
    }

    @Suppress
    public void testBulkInsert() {
        List<Employee> employees = new ArrayList<Employee>(2);
        for (int i = 0; i < employees.size(); ++i) {
            Employee employee = new Employee();
            employee.setFirstName("Name " + i);
            employee.setLastName("LastName " + i);
            employee.setProject("Project " + i);
        }
        EmployeeDAO.save(getContext().getContentResolver(), employees);

        getProvider();
        Cursor cursor = getMockContentResolver().query(
                CONTENT_URI_EMPLOYEE, null, null, null, null);
        assertEquals(employees.size(), cursor.getCount());
    }

}
