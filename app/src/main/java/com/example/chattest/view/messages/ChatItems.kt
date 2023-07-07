package com.example.chattest.view.messages

import android.widget.TextView
import com.example.chattest.R
import com.example.chattest.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import de.hdodenhof.circleimageview.CircleImageView

class ChatFromItem(val text: String, val user: User): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.txtFromRow).text = text

        // carregue a imagem de usuário no registro do chat
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.findViewById<CircleImageView>(R.id.ivChatFromRow)
        Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String, val user: User): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.txtToRow).text = text

        // carregue a imagem de usuário no registro do chat
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.findViewById<CircleImageView>(R.id.ivChatToRow)
        Picasso.get().load(uri).into(targetImageView)

    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}