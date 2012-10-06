package ua.com.springbyexample;

import ua.com.springbyexample.net.SyncService;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Helper class to manage sync process via simple API<br>
 * Facade pattern
 * 
 * @author akaverin
 * 
 */
public class SyncManager {

	private Context context;

	public SyncManager(Context context) {
		this.context = context;
	}

	public void startSync() {
		// TODO: add check for non existing server IP in Prefs!!
		// TODO: handle missing network case (add requets to queue, etc)
		// TODO: handle often syncs with delays and queue to prevent battery
		// drain

		Toast.makeText(context, "Starting synchronization with server...",
				Toast.LENGTH_SHORT).show();

		Intent intent = new Intent(context, SyncService.class);
		context.startService(intent);
	}

}
