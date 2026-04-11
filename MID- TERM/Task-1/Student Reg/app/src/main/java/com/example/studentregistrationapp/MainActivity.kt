package com.example.studentregistrationapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var txtId: EditText
    lateinit var txtFullName: EditText
    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText
    lateinit var txtAge: EditText
    lateinit var spnCountry: Spinner
    lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtId=findViewById(R.id.txtId)
        txtFullName=findViewById(R.id.txtFullName)
        txtEmail=findViewById(R.id.txtEmail)
        txtPassword=findViewById(R.id.txtPassword)
        txtAge=findViewById(R.id.txtAge)
        spnCountry=findViewById(R.id.spnCountry)
        btnSubmit=findViewById(R.id.btnSubmit)

        //spinner data set
        val countryList = arrayOf("Bangladesh", "Afganistan", "Pakistan", "Russia", "Japan")
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, countryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnCountry.adapter=adapter

        btnSubmit.setOnClickListener {
            val id = txtId.text.toString()
            val name = txtFullName.text.toString()
            val email=txtEmail.text.toString()
            val password=txtPassword.text.toString()
            val age=txtAge.text.toString()

            val message = "Id: $id, name: $name, email: $email, Password: $password, Age: $age"
            Toast.makeText(this,message,Toast.LENGTH_LONG).show()
        }


    }
}