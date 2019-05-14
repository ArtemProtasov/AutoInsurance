package org.letmecode.autoinsurance.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
abstract class BaseActivity : AppCompatActivity() {

    protected val firebaseDatabase = FirebaseDatabase.getInstance()
    protected var databaseReference: DatabaseReference? = null

    protected abstract fun contentResource(): Int

    protected abstract fun setupView()

    protected open fun setupViewModel() {

    }

    protected open fun setupDatabase(): DatabaseReference? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentResource())
        setupView()
        databaseReference = setupDatabase()
        setupViewModel()
    }

}