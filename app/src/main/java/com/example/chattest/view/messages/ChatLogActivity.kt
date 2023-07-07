package com.example.chattest.view.messages

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.chattest.R
import com.example.chattest.databinding.ActivityChatLogBinding
import com.example.chattest.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class ChatLogActivity : AppCompatActivity() {
    companion object {
        val TAG = "ChatLog"
    }
    private lateinit var binding: ActivityChatLogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val username = intent.getStringExtra(NewMessageActivity.USER_KEY)
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        supportActionBar?.title = user?.username

        //setupDummyData()
        listenForMessages()

        binding.btSendChatLog.setOnClickListener{
            Log.d(TAG, "Tentar enviar uma mensagem....")
            performSendMessage()
        }
    }

    private fun setupDummyData() {
        val adapter = GroupAdapter<GroupieViewHolder>()
        adapter.add(ChatFromItem(""))
        adapter.add(ChatToItem(""))
        adapter.add(ChatFromItem(""))
        adapter.add(ChatToItem(""))
        adapter.add(ChatFromItem(""))
        adapter.add(ChatToItem(""))

        binding.rvChatLog.adapter = adapter
    }


    private fun listenForMessages() {


        //val fromId = FirebaseAuth.getInstance().uid
        //val toId = toUser?.uid

        FirebaseDatabase.getInstance().getReference("/messages")
        //val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        //ref.addChildEventListener(object: ChildEventListener {

            /*override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    Log.d(TAG, chatMessage.text)

                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        val currentUser = LatestMessagesActivity.currentUser ?: return
                        adapter.add(ChatFromItem(chatMessage.text, currentUser))
                    } else {
                        adapter.add(ChatToItem(chatMessage.text, toUser!!))
                    }
                }

                recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)

            }*/

            //override fun onCancelled(p0: DatabaseError) {}

            //override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

            //override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

            //override fun onChildRemoved(p0: DataSnapshot) {}

        //})

    }

    class ChatMessage(val id: String, val text: String, val fromId: String,val toId: String, val timestamp: Long){

    }


    private fun performSendMessage() {
        // Aqui e onde enviamos uma mensagem para o firebase
        val text = binding.txtChatLog.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user!!.uid

        if (fromId == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/messages")

        val chatMessage = ChatMessage(reference.key!!, text, fromId, toId, System.currentTimeMillis()/1000)
        reference.setValue(chatMessage)
           .addOnSuccessListener {
               Log.d(TAG, "Salvou a mensagem do Chat: ${reference.key}")
               //binding.txtChatLog.text.clear()
               //binding.rvChatLog.scrollToPosition(adapter.itemCount - 1)
           }


       //val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()

       //val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()




       //toReference.setValue(chatMessage)

        //val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
        //latestMessageRef.setValue(chatMessage)

        //val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")
        //latestMessageToRef.setValue(chatMessage)
    }
}
class ChatFromItem(val text: String/*, val user: User*/): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.txtFromRow).text = text
        //viewHolder.itemView.textview_from_row.text = text
        //val uri = user.profileImageUrl
        //val targetImageView = viewHolder.itemView.imageview_chat_from_row
        //Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}
class ChatToItem(val text: String/*, val user: User*/): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.txtToRow).text = text

        // load our user image into the star
        //val uri = user.profileImageUrl
        //val targetImageView = viewHolder.itemView.imageview_chat_to_row
        //Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}
