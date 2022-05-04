package com.example.sqliteopenhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sqliteopenhelper.db.DatabaseHandler
import com.example.sqliteopenhelper.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dbHandler: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = DatabaseHandler(this)

        add.setOnClickListener(View.OnClickListener {
            // checking input text should not be null
            if (validation()){
                val user: User = User()
                var success: Boolean = false
                user.firstName = editText_firstName.text.toString()
                user.lastName = editText_lastName.text.toString()

                success = dbHandler!!.addUser(user)

                if (success){
                    val toast = Toast.makeText(this,"Saved Successfully", Toast.LENGTH_LONG).show()
                }
            }

        })

        show.setOnClickListener(View.OnClickListener {
            var user = dbHandler!!.getAllUsers()
            textView_show.setText(user)
        })

    }
    fun validation(): Boolean{
        var validate = false

        if (!editText_firstName.text.toString().equals("") &&
            !editText_lastName.text.toString().equals("")){
            validate = true
        }else{
            validate = false
            val toast = Toast.makeText(this,"Fill all details", Toast.LENGTH_LONG).show()
        }

        return validate
    }
}