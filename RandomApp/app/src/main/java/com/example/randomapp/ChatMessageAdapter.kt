package com.example.randomapp

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatMessageAdapter(
    private val context: Context,
    private val messages: MutableList<ChatMessage>
) : RecyclerView.Adapter<ChatMessageAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val container: LinearLayout) : RecyclerView.ViewHolder(container) {
        val messageTextView: TextView = TextView(context).apply {
            textSize = 15.5f
            setTextColor(Color.parseColor("#333333"))
            typeface = Typeface.DEFAULT_BOLD
            setLineSpacing(6f, 1f)
            setPadding(dp(16), dp(12), dp(16), dp(12))
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        init {
            container.addView(messageTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val rowLayout = LinearLayout(context).apply {
            layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dp(12)
            }
            orientation = LinearLayout.HORIZONTAL
            setPadding(dp(6), dp(2), dp(6), dp(2))
        }

        return ChatViewHolder(rowLayout)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        val row = holder.container
        val textView = holder.messageTextView

        textView.text = message.text

        val bubbleDrawable = GradientDrawable().apply {
            cornerRadius = dp(20).toFloat()
            setStroke(
                dp(1),
                if (message.isUser) {
                    Color.parseColor("#C9D8FF")
                } else {
                    Color.parseColor("#F0DDE7")
                }
            )
            setColor(
                if (message.isUser) {
                    Color.parseColor("#DCE7FF")
                } else {
                    Color.parseColor("#FFF8FB")
                }
            )
        }

        textView.background = bubbleDrawable

        val textParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.layoutParams = textParams
        textView.maxWidth = dp(250)

        row.gravity = if (message.isUser) Gravity.END else Gravity.START

        textView.setTextColor(
            if (message.isUser) {
                Color.parseColor("#30425D")
            } else {
                Color.parseColor("#4A3440")
            }
        )
    }

    override fun getItemCount(): Int = messages.size

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.lastIndex)
    }

    private fun dp(value: Int): Int {
        return (value * context.resources.displayMetrics.density).toInt()
    }
}