import android.app.Application
import android.content.Context

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MainApplication.appContext = applicationContext
    }

    companion object {

        lateinit  var appContext: Context

    }
}