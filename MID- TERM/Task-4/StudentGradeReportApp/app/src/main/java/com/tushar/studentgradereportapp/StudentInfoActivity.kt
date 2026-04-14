package com.tushar.studentgradereportapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class StudentInfoActivity : AppCompatActivity() {

    private lateinit var etStudentName: EditText
    private lateinit var etStudentID: EditText
    private lateinit var etSemester: EditText
    private lateinit var etDepartment: EditText
    private lateinit var btnSaveStudent: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_info)

        etStudentName = findViewById(R.id.etStudentName)
        etStudentID = findViewById(R.id.etStudentID)
        etSemester = findViewById(R.id.etSemester)
        etDepartment = findViewById(R.id.etDepartment)
        btnSaveStudent = findViewById(R.id.btnSaveStudent)

        btnSaveStudent.setOnClickListener {
            val name = etStudentName.text.toString().trim()
            val id = etStudentID.text.toString().trim()
            val semester = etSemester.text.toString().trim()
            val dept = etDepartment.text.toString().trim()

            if (name.isEmpty() || id.isEmpty() || semester.isEmpty() || dept.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, GradeReportActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("id", id)
            intent.putExtra("semester", semester)
            intent.putExtra("department", dept)
            startActivity(intent)
        }
    }
}
