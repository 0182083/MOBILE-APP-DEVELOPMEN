package com.tushar.studentgradereportapp

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class GradeReportActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var tvGPA: TextView
    private lateinit var tvSummary: TextView
    private lateinit var etSubjectName: EditText
    private lateinit var etObtained: EditText
    private lateinit var etTotal: EditText
    private lateinit var btnAdd: Button

    private lateinit var tvStudentName: TextView
    private lateinit var tvStudentID: TextView
    private lateinit var tvSemester: TextView
    private lateinit var tvDepartment: TextView

    private val gradesList = mutableListOf<Pair<TableRow, Double>>()
    private var passCount = 0
    private var failCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade_report)

        tableLayout = findViewById(R.id.tableLayout)
        tvGPA = findViewById(R.id.tvGPA)
        tvSummary = findViewById(R.id.tvSummary)
        etSubjectName = findViewById(R.id.etSubjectName)
        etObtained = findViewById(R.id.etObtained)
        etTotal = findViewById(R.id.etTotal)
        btnAdd = findViewById(R.id.btnAdd)

        tvStudentName = findViewById(R.id.tvStudentName)
        tvStudentID = findViewById(R.id.tvStudentID)
        tvSemester = findViewById(R.id.tvSemester)
        tvDepartment = findViewById(R.id.tvDepartment)

        val name = intent.getStringExtra("name") ?: ""
        val id = intent.getStringExtra("id") ?: ""
        val semester = intent.getStringExtra("semester") ?: ""
        val dept = intent.getStringExtra("department") ?: ""

        tvStudentName.text = "Student Name: $name"
        tvStudentID.text = "Student ID: $id"
        tvSemester.text = "Semester: $semester"
        tvDepartment.text = "Department: $dept"

        // Default subjects
        addSubjectRow("Math", 95.0, 100.0)
        addSubjectRow("Physics", 85.0, 100.0)

        btnAdd.setOnClickListener {
            val subject = etSubjectName.text.toString().trim()
            val obtainedStr = etObtained.text.toString().trim()
            val totalStr = etTotal.text.toString().trim()

            if (subject.isEmpty() || obtainedStr.isEmpty() || totalStr.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val obtained = obtainedStr.toDoubleOrNull()
            val total = totalStr.toDoubleOrNull()

            if (obtained == null || total == null) {
                Toast.makeText(this, "Enter valid numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            addSubjectRow(subject, obtained, total)
            etSubjectName.text.clear()
            etObtained.text.clear()
            etTotal.text.clear()
        }
    }

    private fun addSubjectRow(subject: String, obtained: Double, total: Double) {
        val tableRow = TableRow(this)
        val rowIndex = tableLayout.childCount
        tableRow.setBackgroundColor(if (rowIndex % 2 == 0) Color.parseColor("#E8EAF6") else Color.WHITE)

        val tvSubject = TextView(this)
        tvSubject.text = subject
        tableRow.addView(tvSubject)

        val tvObtained = TextView(this)
        tvObtained.text = obtained.toString()
        tableRow.addView(tvObtained)

        val tvTotal = TextView(this)
        tvTotal.text = total.toString()
        tableRow.addView(tvTotal)

        val grade = calculateGrade(obtained, total)
        val tvGrade = TextView(this)
        tvGrade.text = grade
        tvGrade.setTextColor(if (grade == "F") Color.RED else Color.GREEN)
        tableRow.addView(tvGrade)

        val btnDelete = Button(this)
        btnDelete.text = "Delete"
        tableRow.addView(btnDelete)

        tableLayout.addView(tableRow)

        val gpa = convertToGPA(grade)
        gradesList.add(Pair(tableRow, gpa))
        if (grade == "F") failCount++ else passCount++
        updateGPA()
        updateSummary()

        btnDelete.setOnClickListener {
            tableLayout.removeView(tableRow)
            val pairToRemove = gradesList.find { it.first == tableRow }
            if (pairToRemove != null) gradesList.remove(pairToRemove)
            if (grade == "F") failCount-- else passCount--
            updateGPA()
            updateSummary()
        }
    }

    private fun calculateGrade(obtained: Double, total: Double): String {
        val percent = (obtained / total) * 100
        return when {
            percent >= 90 -> "A+"
            percent >= 80 -> "A"
            percent >= 70 -> "B+"
            percent >= 60 -> "B"
            percent >= 50 -> "C"
            percent >= 40 -> "D"
            else -> "F"
        }
    }

    private fun convertToGPA(grade: String): Double {
        return when (grade) {
            "A+" -> 4.0
            "A" -> 3.7
            "B+" -> 3.3
            "B" -> 3.0
            "C" -> 2.0
            "D" -> 1.0
            else -> 0.0
        }
    }

    private fun updateGPA() {
        tvGPA.text = if (gradesList.isNotEmpty()) {
            "GPA: %.2f".format(gradesList.map { it.second }.average())
        } else "GPA: 0.0"
    }

    private fun updateSummary() {
        tvSummary.text = "Total Subjects: ${gradesList.size} | Passed: $passCount | Failed: $failCount"
    }
}
