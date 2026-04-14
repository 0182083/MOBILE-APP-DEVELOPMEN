package com.tushar.contactbookapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*
class ContactAdapter(
    private val context: Context,
    private var contactList: MutableList<Contact>
) : BaseAdapter(), Filterable {

    private var filteredList: MutableList<Contact> = ArrayList(contactList)

    override fun getCount(): Int = filteredList.size
    override fun getItem(position: Int): Any = filteredList[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)

        val contact = filteredList[position]

        val avatar = view.findViewById<TextView>(R.id.avatar)
        val name = view.findViewById<TextView>(R.id.name)
        val phone = view.findViewById<TextView>(R.id.phone)
        val email = view.findViewById<TextView>(R.id.email)

        avatar.text = contact.name.first().uppercase()
        name.text = contact.name
        phone.text = contact.phone
        email.text = contact.email

        return view
    }

    // 🔍 SEARCH FIX
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val query = charSequence.toString().lowercase()
                val resultList = if (query.isEmpty()) {
                    contactList
                } else {
                    contactList.filter {
                        it.name.lowercase().contains(query)
                    }
                }

                val results = FilterResults()
                results.values = resultList
                return results
            }

            override fun publishResults(charSequence: CharSequence?, results: FilterResults?) {
                filteredList = ArrayList(results?.values as List<Contact>)
                notifyDataSetChanged()
            }
        }
    }

    // ❌ DELETE FIX
    fun remove(position: Int) {
        val contact = filteredList[position]
        contactList.remove(contact)
        filteredList.removeAt(position)
        notifyDataSetChanged()
    }

    // ➕ ADD
    fun add(contact: Contact) {
        contactList.add(contact)
        filteredList.add(contact)
        notifyDataSetChanged()
    }

    // ✏️ EDIT
    fun update(position: Int, newContact: Contact) {
        val oldContact = filteredList[position]
        val index = contactList.indexOf(oldContact)

        contactList[index] = newContact
        filteredList[position] = newContact

        notifyDataSetChanged()
    }
}
