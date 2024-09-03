package app.lawnchair.allapps

import android.os.Handler
import android.os.Looper
import android.util.Pair
import com.android.launcher3.Launcher
import com.android.launcher3.model.data.ItemInfo

private val uiHandler by lazy { Handler(Looper.getMainLooper()) }

/** Convenience function to add all items to home screen that aren't there already. */
fun addAllAppsToHomeScreen(launcher: Launcher, delayed: Boolean) {
    // Hacky, but I'm going for 'easy to maintain', not 'technically correct'.
    uiHandler.postDelayed({
        if (launcher.isDestroyed || launcher.isFinishing) {
            // Abort
            return@postDelayed
        }

        val model = launcher.model
        val allAppsList = model.allAppsList.data
        val workspaceItems: List<Pair<ItemInfo, Any>> =
            allAppsList.filter { it.targetPackage != launcher.packageName }
                .map { Pair.create(it.makeWorkspaceItem(launcher), null) }
        model.addAndBindAddedWorkspaceItems(workspaceItems)
    }, if (delayed) 100 else 0)
}
