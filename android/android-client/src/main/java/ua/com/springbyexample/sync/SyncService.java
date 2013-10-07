package ua.com.springbyexample.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Service to manage SyncAdapter lifetime
 * Created with IntelliJ IDEA.
 * User: akaverin
 * Date: 10/5/13
 * Time: 7:11 PM
 */
public class SyncService extends Service {

    private SyncAdapter syncAdapter;

    /**
     * @see android.app.Service#onCreate()
     */
    @Override
    public void onCreate() {
        synchronized (SyncAdapter.class) {
            syncAdapter = setupSyncAdapter();
        }
    }

    /**
     * @see android.app.Service#onBind(android.content.Intent)
     */
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }

    private SyncAdapter setupSyncAdapter() {
        return new SyncAdapter(getApplicationContext(), true);
    }
}
