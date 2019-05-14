package org.letmecode.autoinsurance.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
abstract class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    val progressLoading = SingleLiveEvent<Boolean>()
    val errorLoading = SingleLiveEvent<Throwable>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    protected fun progressLoadingCunsumer(): Consumer<Disposable> {
        return Consumer {
            progressLoading.postValue(true)
        }
    }

    protected fun errorLoadingConsumer(): Consumer<Throwable> {
        return Consumer {
            errorLoading.postValue(it)
            progressLoading.postValue(false)
        }
    }

    protected fun terminateProgress(): Action {
        return Action {
            progressLoading.postValue(false)
        }
    }


}