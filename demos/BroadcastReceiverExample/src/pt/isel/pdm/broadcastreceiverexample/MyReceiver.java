package pt.isel.pdm.broadcastreceiverexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.RemoteViews;

public class MyReceiver extends BroadcastReceiver{

	private static final String TAG = "MY_RECEIVER";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.d(TAG, "received intent with action = "+intent.getAction());
		
		if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
			Log.d(TAG, "Power connected");
		}
		else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
			Log.d(TAG, "Power disconnected");
		}
		else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
			Log.d(TAG, "Wifi changed");
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			Log.d(TAG, "Network info  = "+ni);
			Log.d(TAG, "Wifi connected  = "+ni.isConnected());
			Log.d(TAG, "Wifi state  = "+ni.getDetailedState());			
		}
		else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
			Log.d(TAG, "Network changed");
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			Log.d(TAG, "Network info  = "+ni);
			Log.d(TAG, "Wifi connected  = "+ni.isConnected());
			Log.d(TAG, "Wifi state  = "+ni.getDetailedState());
			if(ni.isConnected()){				
				Log.d(TAG, "sending notification "+ni.getDetailedState().name());
				
				Notification.Builder builder = new Notification.Builder(context)
					.setContentTitle("title: Network is connected")
					.setContentText("text: network is connected")
					.setAutoCancel(false)
					.setSmallIcon(R.drawable.ic_launcher)
					.setProgress(100, 50, false)
					.setOngoing(true)
					.setContent(
							new RemoteViews("pt.isel.pdm.broadcastreceiverexample",
									R.layout.activity_main));
				
				intent = new Intent(context, MainActivity.class);
				intent.putExtra("msg", "network is connected");
				
				PendingIntent pintent = 
						PendingIntent.getActivity(context, 1, intent, 0);							
				
				builder.setContentIntent(pintent);				
				
				NotificationManager notificationManager = 
						  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

				notificationManager.notify(0, builder.build()); 
			}
		}
		else{
			Log.d(TAG, "Unknown intent");
		}
	}
}
