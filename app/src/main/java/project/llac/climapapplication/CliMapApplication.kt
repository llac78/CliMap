package project.llac.climapapplication

import android.app.Application
import project.llac.climapapplication.helper.HelperDB

class CliMapApplication : Application() {

    companion object {
        lateinit var instance: CliMapApplication
    }

    var helperDB: HelperDB? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)
    }
}