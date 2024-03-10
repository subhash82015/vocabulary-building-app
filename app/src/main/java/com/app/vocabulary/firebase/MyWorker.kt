package com.cubeJeKxUser.firebase

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker
    (@NonNull appContext: Context?, @NonNull workerParams: WorkerParameters?) :
    Worker(appContext!!, workerParams!!) {
    @NonNull
    override fun doWork(): Result {
        Log.d("TAG", "Performing long running task in scheduled job")
        // TODO(developer): add long running task here.
        return Result.success()
    }
}