package ua.com.springbyexample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.ContentResolver;
import android.os.Bundle;
import android.util.Log;
import ua.com.springbyexample.account.Authenticator;
import ua.com.springbyexample.dao.provider.EmployeeContentProvider;
import ua.com.springbyexample.util.Tags;


/**
 * Our {@link Application} specialization. Behaves as Android native singleton
 * and serves as main point for application generic services
 *
 * @author akaverin
 */
public class SpringApplication extends Application {

    private static final String TAG = Tags.getTag(SpringApplication.class);
    private Account appAccount;

    //TODO: configure AndroLog
    @Override
    public void onCreate() {
        super.onCreate();
        setupAppAccount();
    }

    @Override
    public void onLowMemory() {
        // TODO: reset any caches in memory here...
    }

    public void requestForcedSync() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(appAccount, EmployeeContentProvider.AUTHORITY, bundle);
    }

    private void setupAppAccount() {
        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(Authenticator
                .ACCOUNT_TYPE);
        if (accounts.length > 0) {
            appAccount = accounts[0];
            return;
        }
        Log.i(TAG, "UASpring account not present, creating...");

        appAccount = new Account(Authenticator.ACCOUNT_NAME, Authenticator.ACCOUNT_TYPE);
        //usually it will contain real user info, like login/pass
        accountManager.addAccountExplicitly(appAccount, null, null);

        //Enable AutoSync, important for auto-upload
        ContentResolver.setSyncAutomatically(appAccount, EmployeeContentProvider.AUTHORITY, true);
    }

}


