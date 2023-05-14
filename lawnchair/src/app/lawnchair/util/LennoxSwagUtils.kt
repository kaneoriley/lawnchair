package app.lawnchair.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import app.lawnchair.preferences.PreferenceManager
import app.lawnchair.preferences2.PreferenceManager2
import com.android.launcher3.LauncherAppState
import com.android.launcher3.LauncherSettings.Favorites
import com.android.launcher3.model.data.ItemInfo

fun Context.startOrRequestInstall(packageName: String, intent: Intent) {
    if (packageManager.resolveActivity(intent, 0) != null) {
        startActivity(intent)
        return
    }

    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}

fun getPrefs(): PreferenceManager {
    return PreferenceManager.getInstance(LauncherAppState.INSTANCE.noCreate.context)
}

fun getPrefs2(): PreferenceManager2 {
    return PreferenceManager2.getInstance(LauncherAppState.INSTANCE.noCreate.context)
}

/** Detect if an item is a duplicate for the purposes of all apps mode cleanup. */
fun ItemInfo.isAllAppsModeDuplicate(other: ItemInfo) = itemType == Favorites.ITEM_TYPE_APPLICATION && other.itemType == Favorites.ITEM_TYPE_APPLICATION &&
    targetComponent?.toString() == other.targetComponent?.toString() && user == other.user
