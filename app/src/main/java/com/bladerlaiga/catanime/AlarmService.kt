package com.bladerlaiga.catanime

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmService : Service() {
  private var startMode: Int = 0             // indicates how to behave if the service is killed
  private var binder: IBinder? = null        // interface for clients that bind
  private var allowRebind: Boolean = false   // indicates whether onRebind should be used

  val CHANNEL_ID = "0"

  override fun onCreate() {
    // The service is being created
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    if (intent != null) {
      val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.proto_v2)
        .setContentTitle(intent.getStringExtra("title"))
        .setContentText("Your Anime on Update")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

      with(NotificationManagerCompat.from(this)) {
        notify(intent.getIntExtra("id", 0), builder.build())
      }
    }
    return startMode
  }

  override fun onBind(intent: Intent): IBinder? {
    // A client is binding to the service with bindService()
    return binder
  }

  override fun onUnbind(intent: Intent): Boolean {
    // All clients have unbound with unbindService()
    return allowRebind
  }

  override fun onRebind(intent: Intent) {
    // A client is binding to the service with bindService(),
    // after onUnbind() has already been called
  }

  override fun onDestroy() {
    // The service is no longer used and is being destroyed
  }
}