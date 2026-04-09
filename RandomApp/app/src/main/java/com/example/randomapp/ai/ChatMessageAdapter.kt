package com.example.randomapp.ai

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.randomapp.R

class ChatMessageAdapter(
    private val context: Context,
    private val messages: MutableList<ChatMessage>,
    private val isDarkMode: Boolean
) : RecyclerView.Adapter<ChatMessageAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserMessage: TextView = itemView.findViewById(R.id.tvUserMessage)
        val tvAiMessage: TextView = itemView.findViewById(R.id.tvAiMessage)
        val cardUserMessage: CardView = itemView.findViewById(R.id.cardUserMessage)
        val cardAiMessage: CardView = itemView.findViewById(R.id.cardAiMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]

        if (message.isUser) {
            holder.cardUserMessage.visibility = View.VISIBLE
            holder.cardAiMessage.visibility = View.GONE
            holder.tvUserMessage.text = message.text

            holder.cardUserMessage.setCardBackgroundColor(Color.parseColor("#E85C87"))
            holder.tvUserMessage.setTextColor(Color.WHITE)
        } else {
            holder.cardUserMessage.visibility = View.GONE
            holder.cardAiMessage.visibility = View.VISIBLE
            holder.tvAiMessage.text = message.text

            if (isDarkMode) {
                holder.cardAiMessage.setCardBackgroundColor(Color.parseColor("#3A3342"))
                holder.tvAiMessage.setTextColor(Color.parseColor("#F6EAF0"))
            } else {
                holder.cardAiMessage.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                holder.tvAiMessage.setTextColor(Color.parseColor("#44333F"))
            }
        }
    }

    override fun getItemCount(): Int = messages.size

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.lastIndex)
    }
}