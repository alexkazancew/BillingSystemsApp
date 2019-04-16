import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.alexkazancew.billingsystemsapp.BillingApp
import com.alexkazancew.billingsystemsapp.di.DaggerAppComponent
import com.alexkazancew.billingsystemsapp.di.Injectable
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

object AppInjector {
    fun init(billingApp: BillingApp) {
        DaggerAppComponent.builder().application(billingApp)
                .build().inject(billingApp)
        billingApp.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is Injectable) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }

                        }
                    }, true)
        }
    }
}