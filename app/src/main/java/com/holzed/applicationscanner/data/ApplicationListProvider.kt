package com.holzed.applicationscanner.data

import android.content.pm.PackageManager
import com.holzed.applicationscanner.data.model.AppItemModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import javax.inject.Inject

interface ApplicationListProvider {
    suspend fun fetchApplicationInfo(): List<AppItemModel>
}

@Suppress("TooGenericExceptionCaught", "SwallowedException")
class ApplicationListProviderImpl @Inject constructor(
    private val packageManager: PackageManager,
) : ApplicationListProvider {

    override suspend fun fetchApplicationInfo(): List<AppItemModel> = coroutineScope {
        val flag = PackageManager.GET_META_DATA
        val semaphore = Semaphore(permits = 8)

        val apps = packageManager.getInstalledApplications(flag)
            .filter { packageManager.getLaunchIntentForPackage(it.packageName) != null }

        apps.map { app ->
            async {
                semaphore.withPermit {
                    try {
                        val packageInfo = packageManager.getPackageInfo(app.packageName, flag)
                        AppItemModel(
                            title = app.loadLabel(packageManager).toString(),
                            version = with(packageInfo) { "$versionName ($versionCode)" },
                            packageName = app.packageName,
                            sourceDir = app.sourceDir,
                        )
                    } catch (_: Exception) {
                        null
                    }
                }
            }
        }
            .awaitAll()
            .filterNotNull()
    }
}
