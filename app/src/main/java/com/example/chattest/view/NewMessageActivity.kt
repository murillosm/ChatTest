package com.example.chattest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chattest.databinding.ActivityNewMessageBinding

class NewMessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_new_message)
        binding = ActivityNewMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Select User"

        //binding.rvNewessage.adapter
        //binding.rvNewessage.layoutManager = LinearLayoutManager(this)
        //fetchUsers()
    }

    /*companion object {
        val USER_KEY = "USER_KEY"
    }*/

    private fun fetchUsers() {
        /*val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<RecyclerView.ViewHolder>()

                p0.children.forEach {
                    Log.d("NewMessage", it.toString())
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        adapter.add(UserItem(user))
                    }
                }

                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem

                    val intent = Intent(view.context, ChatLogActivity::class.java)
        //          intent.putExtra(USER_KEY,  userItem.user.username)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)

                    finish()

                }*/

                //binding.rvNewessage.adapter = adapter
           /* }

            override fun onCancelled(p0: DatabaseError) {

            }
        })*/
    }
}

/*class UserItem(val user: User): ClipData.Item<RecyclerView.ViewHolder>() {
    override fun bind(viewHolder: RecyclerView.ViewHolder, position: Int) {
        viewHolder.itemView.username_textview_new_message.text = user.username

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageview_new_message)
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}*/