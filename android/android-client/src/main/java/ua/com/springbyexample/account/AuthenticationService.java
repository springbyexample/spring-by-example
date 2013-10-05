package ua.com.springbyexample.account;

import android.accounts.AbstractAccountAuthenticator;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Service to provide Authenticator for sync
 * Created with IntelliJ IDEA.
 * User: akaverin
 * Date: 10/5/13
 * Time: 6:45 PM
 */
public class AuthenticationService extends Service {

    private AbstractAccountAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new Authenticator(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }

}
