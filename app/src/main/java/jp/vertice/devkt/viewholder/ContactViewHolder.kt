package jp.vertice.devkt.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.vertice.devkt.R
import jp.vertice.devkt.model.Contact
import kotlinx.android.synthetic.main.view_holder_event.view.*


class ContactViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_contact, parent, false)) {
    fun bind(contact: Contact) {
        itemView.tvTitle.text = contact.title
        itemView.tvNote.text = contact.description
    }
}


