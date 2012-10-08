package ua.com.springbyexample;

import android.app.Application;

/**
 * Our {@link Application} specialization. Behaves as Android native singleton
 * and serves as main point for application generic services
 * 
 * @author akaverin
 * 
 */
public class SpringApplication extends Application {

	SyncManager syncManager;

	//TODO: configure AndroLog
	@Override
	public void onCreate() {
		super.onCreate();
		syncManager = new SyncManager(this);
	}

	@Override
	public void onLowMemory() {
		// TODO: reset any caches in memory here...
	}

	public SyncManager getSyncManager() {
		return syncManager;
	}

}
