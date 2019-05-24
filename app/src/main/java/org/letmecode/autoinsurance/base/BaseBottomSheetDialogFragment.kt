package org.letmecode.autoinsurance.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    protected val firebaseDatabase = FirebaseDatabase.getInstance()
    protected var databaseReference: DatabaseReference? = null
    protected var firebaseAuth: FirebaseAuth? = null


    protected abstract fun contentResource(): Int

    protected abstract fun setupView()

    protected open fun getProgress(): View? {
        return null
    }

    protected open fun getViewGroup(): ViewGroup? {
        return null
    }

    protected open fun setupViewModel() {

    }

    protected open fun setupDatabase(): DatabaseReference? {
        return null
    }

    protected open fun setupFirebaseAuth(): FirebaseAuth? {
        return null
    }

    protected fun observerErrorLoading(): Observer<in Throwable?> {
        return Observer {
            Toast.makeText(context, it?.message, Toast.LENGTH_SHORT).show()
        }
    }

    protected fun observerProgressLoading(): Observer<in Boolean?> {
        return Observer {
            if (getProgress() == null) return@Observer
            if (getViewGroup() != null) {
                TransitionManager.beginDelayedTransition(getViewGroup()!!)
            }
            if (it!!) {
                getProgress()?.visibility = View.VISIBLE
            } else {
                getProgress()?.visibility = View.GONE
            }
        }
    }

    protected open fun <K : ViewModel> getViewModel(context: BaseBottomSheetDialogFragment, viewModelClass: Class<K>): K {
        return ViewModelProviders.of(context, Injection.provideViewModelFactory()).get(viewModelClass)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(contentResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = setupFirebaseAuth()
        databaseReference = setupDatabase()
        setupViewModel()
        setupView()
    }

}