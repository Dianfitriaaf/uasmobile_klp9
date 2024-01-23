package com.bladerlaiga.catanime

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bladerlaiga.catanime.repository.AppRepository
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*

class AlarmReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    runBlocking {
      val repository = AppRepository(context)
      val animeReminder = repository.getAnimeReminder()
      val id = intent.getLongExtra("id", 0L)
      if (id == 0L) {
        return@runBlocking
      }
      val data = animeReminder.getByIdAnimeDetailWithAnimeDetail(id) ?: return@runBlocking
      val channel = NotificationChannel(
        AnimeReminderNotification.CHANNEL_ID,
        AnimeReminderNotification.CHANNEL_NANME,
        AnimeReminderNotification.CHANNEL_IMPORTANCE
      ).apply { description = AnimeReminderNotification.CHANNEL_DESC }
      val notificationManager: NotificationManager = context.getSystemService(
        Context.NOTIFICATION_SERVICE
      ) as NotificationManager
      val builder = NotificationCompat.Builder(context, AnimeReminderNotification.CHANNEL_ID)
        .setSmallIcon(R.drawable.proto_v2_s16)
        .setContentTitle(data.animeDetail.title)
        .setContentText("Your Anime on Update")
        .setDefaults(Notification.DEFAULT_ALL/*Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE*/)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      notificationManager.createNotificationChannel(channel)
      Glide.with(context)
        .asBitmap()
        .load(data.animeDetail.image_url)
        .placeholder(R.drawable.proto_v2)
        .error(R.drawable.proto_v2)
        .into(object : CustomTarget<Bitmap>() {
          override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            builder.setLargeIcon(resource)
            with(NotificationManagerCompat.from(context)) {
              notify(0, builder.build())
            }
          }

          override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

  }
}

object AnimeReminderNotification {
  val CHANNEL_ID = "00010011"
  val CHANNEL_NANME = "anime reminder"
  val CHANNEL_DESC = "reminder for ongoing anime"
  val CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT
}
