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
    private val hashProvider: HashProvider,
) : ApplicationListProvider {

    override suspend fun fetchApplicationInfo(): List<AppItemModel> = coroutineScope {
        val flag = PackageManager.GET_META_DATA
        val semaphore = Semaphore(permits = 8)

        packageManager.getInstalledApplications(flag)
            .filter { packageManager.getLaunchIntentForPackage(it.packageName) != null }
            .map { app ->
                async {
                    semaphore.withPermit {
                        try {
                            val packageInfo = packageManager.getPackageInfo(app.packageName, flag)
                            val apkHash = hashProvider.calculateHash(app.sourceDir)
                            AppItemModel(
                                title = app.loadLabel(packageManager).toString(),
                                version = with(packageInfo) { "$versionName ($versionCode)" },
                                packageName = app.packageName,
                                apkHash = apkHash,
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
