package app.lawnchair.gestures.handlers

import android.content.Context
import app.lawnchair.LawnchairLauncher

class OpenAppSearchGestureHandler(context: Context) : OpenAppDrawerGestureHandler(context) {

    override suspend fun onTrigger(launcher: LawnchairLauncher) {
        launcher.appsView.searchUiManager.editText?.showKeyboard()
        super.onTrigger(launcher)
    }
}
