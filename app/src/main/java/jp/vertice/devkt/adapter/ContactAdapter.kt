package jp.vertice.devkt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.vertice.devkt.model.Contact
import jp.vertice.devkt.viewholder.ContactViewHolder
import kotlinx.android.synthetic.main.view_holder_contact.view.*

class ContactAdapter() :RecyclerView.Adapter<ContactViewHolder>(){
    private val list: MutableList<Contact> = mutableListOf()
    private var itemClickListener: ((Contact) -> Unit)? = null
    private var itemCallListener: ((Contact) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ContactViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(item)
        }
        holder.itemView.ivCall.setOnClickListener {
            itemCallListener?.invoke(item)
        }
        holder.bind(item)
    }

    fun setItemClickListener(listener: (letter: Contact) -> Unit) {
        itemClickListener = listener
    }

    fun setItemCallListener(listener: (letter: Contact) -> Unit) {
        itemCallListener = listener
    }

    override fun getItemCount(): Int = list.size
    fun update(listItems: List<Contact>) {
        list.clear()
        list.addAll(listItems)
        notifyDataSetChanged()
    }

}
