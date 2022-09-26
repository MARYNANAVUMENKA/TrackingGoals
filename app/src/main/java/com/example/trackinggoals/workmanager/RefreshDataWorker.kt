package com.example.trackinggoals.workmanager


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.trackinggoals.R
import com.example.trackinggoals.presentation.main.MainActivity
import com.example.trackinggoals.domain.usecases.quote.LoadQuotesInBackgroundUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class RefreshDataWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters), KoinComponent {

    private val loadQuotesInBackgroundUseCase: LoadQuotesInBackgroundUseCase by inject()

    override suspend fun doWork(): Result {

        loadQuotesInBackgroundUseCase.invoke()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = NOTIFICATION_CHANNEL_NAME
            val descriptionText = NOTIFICATION_CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText

            notificationManager.createNotificationChannel(mChannel)

            val pIntent = PendingIntent.getActivity(
                context,
                CONTENT_REQUEST_CODE,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(CONTENT_TITLE)
                .setContentText(CONTENT_NAME)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_baseline_refresh_24)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .build()

            notificationManager.notify(1, notification)
        }
        return Result.success()
    }


    companion object {
        private const val CHANNEL_ID = "1"
        private const val CONTENT_TITLE = "Affirmation updated"
        private const val CONTENT_NAME = "See your affirmation for today"
        private const val NOTIFICATION_CHANNEL_NAME = "Work Service"
        private const val CONTENT_REQUEST_CODE = 2
        const val WORK_NAME = "RefreshDataWorker"
    }
}