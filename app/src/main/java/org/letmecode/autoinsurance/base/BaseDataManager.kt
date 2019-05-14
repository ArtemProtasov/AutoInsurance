package org.letmecode.autoinsurance.base

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
open class BaseDataManager {

    protected fun <K : Observable<K>> compositeWrapper(source: Observable<K>): Observable<K> {
        return source
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(Function {
                return@Function Observable.just(it)
            })
    }

}