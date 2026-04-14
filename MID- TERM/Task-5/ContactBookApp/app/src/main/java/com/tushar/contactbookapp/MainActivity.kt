package com.tushar.contactbookapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ContactAdapter
    private lateinit var searchView: EditText
    private lateinit var emptyView: TextView

    private val contactList = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        searchView = findViewById(R.id.searchView)
        emptyView = findViewById(R.id.emptyView)
        val fab = findViewById<Button>(R.id.fab)

        adapter = ContactAdapter(this, contactList)
        listView.adapter = adapter



        // Add contact
        fab.setOnClickListener {
            showAddDialog()
        }

        // Delete contact (long press)
        listView.setOnItemLongClickListener { _, _, position, _ ->
            AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Delete this contact?")
                .setPositiveButton("Yes") { _, _ ->
                    adapter.remove(position)
                    checkEmpty()
                }
                .setNegativeButton("No", null)
                .show()
            true
        }

        searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                adapter.filter.filter(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

    private fun showAddDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_add_contact, null)

        val etName = view.findViewById<EditText>(R.id.etName)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)

        AlertDialog.Builder(this)
            .setTitle("Add Contact")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                val name = etName.text.toString()
                val phone = etPhone.text.toString()
                val email = etEmail.text.toString()

                if (name.isNotEmpty()) {
                    adapter.add(Contact(name, phone, email))
                    checkEmpty()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun checkEmpty() {
        emptyView.visibility = if (adapter.count == 0) TextView.VISIBLE else TextView.GONE
    }

    private fun showEditDialog(position: Int) {
        val view = layoutInflater.inflate(R.layout.dialog_add_contact, null)

        val etName = view.findViewById<EditText>(R.id.etName)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)

        val contact = adapter.getItem(position) as Contact

        etName.setText(contact.name)
        etPhone.setText(contact.phone)
        etEmail.setText(contact.email)

        AlertDialog.Builder(this)
            .setTitle("Edit Contact")
            .setView(view)
            .setPositiveButton("Update") { _, _ ->
                val newContact = Contact(
                    etName.text.toString(),
                    etPhone.text.toString(),
                    etEmail.text.toString()
                )
                adapter.update(position, newContact)
            }
            .setNegativeButton("Cancel", null)
            .show()

    }

}
