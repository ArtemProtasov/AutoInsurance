package org.letmecode.autoinsurance.base

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
class SingleLiveEvent<K>: MutableLiveData<K?>() {

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in K?>) {
        if(hasActiveObservers()) {
            Log.w(SingleLiveEvent::class.simpleName, "Multiple observers registered but only one will be notified of changes.")
        }

        super.observe(owner, Observer {
            if(pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    override fun setValue(k: K?) {
        pending.set(true)
        super.setValue(k)
    }

    @MainThread
    fun call() {
        value = null
    }

}