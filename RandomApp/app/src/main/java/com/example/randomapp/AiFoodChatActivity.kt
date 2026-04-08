package com.example.randomapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AiFoodChatActivity : ComponentActivity() {

    private lateinit var db: AppDatabase

    private lateinit var btnBack: Button
    private lateinit var editMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var recyclerChat: RecyclerView

    private lateinit var chatAdapter: ChatMessageAdapter
    private val chatMessages = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ai_food_chat)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "random_app_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        btnBack = findViewById(R.id.btnBack)
        editMessage = findViewById(R.id.editMessage)
        btnSend = findViewById(R.id.btnSend)
        recyclerChat = findViewById(R.id.recyclerChat)

        chatAdapter = ChatMessageAdapter(this, chatMessages)
        recyclerChat.layoutManager = LinearLayoutManager(this)
        recyclerChat.adapter = chatAdapter

        addMessage("สวัสดี เราช่วยแนะนำอาหารให้ได้นะ ลองพิมพ์มาว่าอยากกินแบบไหน", false)

        btnBack.setOnClickListener {
            finish()
        }

        btnSend.setOnClickListener {
            lifecycleScope.launch {
                sendMessage()
            }
        }

        editMessage.setOnEditorActionListener { _, _, _ ->
            lifecycleScope.launch {
                sendMessage()
            }
            true
        }
    }

    private suspend fun sendMessage() {
        val userMsg = editMessage.text.toString().trim()

        if (userMsg.isEmpty()) return

        addMessage(userMsg, true)
        editMessage.setText("")

        val reply = generateReplyFromDb(userMsg)
        addMessage(reply, false)
    }

    private fun addMessage(text: String, isUser: Boolean) {
        chatAdapter.addMessage(ChatMessage(text = text, isUser = isUser))
        recyclerChat.post {
            recyclerChat.scrollToPosition(chatMessages.lastIndex)
        }
    }

    private suspend fun generateReplyFromDb(message: String): String {
        val text = message.lowercase().trim()

        val foods = withContext(Dispatchers.IO) {
            db.foodDao().getAllFoods()
        }

        if (foods.isEmpty()) {
            return "ตอนนี้ยังไม่มีเมนูในระบบเลย ลองเพิ่มเมนูในหน้าสุ่มอาหารก่อนนะ"
        }

        val foodNames = foods.map { it.name.trim() }.filter { it.isNotBlank() }

        val spicyFoods = foodNames.filter { isSpicyFood(it) }
        val nonSpicyFoods = foodNames.filter { isNonSpicyFood(it) }
        val noodleFoods = foodNames.filter { isNoodleFood(it) }
        val healthyFoods = foodNames.filter { isHealthyFood(it) }
        val lightFoods = foodNames.filter { isLightFood(it) }
        val easyFoods = foodNames.filter { isEasyFood(it) }
        val sweetFoods = foodNames.filter { isSweetFood(it) }

        return when {
            containsAny(text, listOf("มีอะไร", "เมนู", "อะไรบ้าง", "มีอะไรบ้าง")) -> {
                val list = foodNames.joinToString("\n- ")
                "ตอนนี้คุณมีเมนู:\n- $list"
            }

            containsAny(text, listOf("เย็น", "มื้อเย็น")) &&
                    containsAny(text, listOf("ไม่รู้", "กินอะไร", "กินไร")) -> {
                buildRecommendationReply(
                    candidates = lightFoods.ifEmpty { foodNames },
                    reason = "เพราะน่าจะเหมาะกับมื้อเย็นและไม่หนักเกินไป"
                )
            }

            containsAny(text, listOf("กลางวัน", "มื้อกลางวัน")) -> {
                buildRecommendationReply(
                    candidates = easyFoods.ifEmpty { foodNames },
                    reason = "เพราะกินง่ายและเหมาะกับมื้อกลางวัน"
                )
            }

            containsAny(text, listOf("ดึก", "กลางคืน")) -> {
                val safeLateNightFoods = (lightFoods + healthyFoods).distinct().filterNot { isSpicyFood(it) }
                if (safeLateNightFoods.isNotEmpty()) {
                    buildRecommendationReply(
                        candidates = safeLateNightFoods,
                        reason = "เพราะเบากว่าเมนูหนัก ๆ และเหมาะกับตอนดึก"
                    )
                } else {
                    "ตอนนี้ยังไม่เจอเมนูเบา ๆ สำหรับตอนดึกในรายการเลย"
                }
            }

            containsAny(text, listOf("ไม่เผ็ด", "ไม่เอาเผ็ด", "ไม่กินเผ็ด")) -> {
                when {
                    nonSpicyFoods.isNotEmpty() -> {
                        buildRecommendationReply(
                            candidates = nonSpicyFoods,
                            reason = "เพราะเป็นเมนูที่ไม่เผ็ดและกินง่าย"
                        )
                    }

                    lightFoods.isNotEmpty() -> {
                        val safeLightFoods = lightFoods.filterNot { isSpicyFood(it) }
                        if (safeLightFoods.isNotEmpty()) {
                            buildRecommendationReply(
                                candidates = safeLightFoods,
                                reason = "เพราะเป็นเมนูเบา ๆ และไม่เผ็ด"
                            )
                        } else {
                            "ตอนนี้ยังไม่เจอเมนูไม่เผ็ดในรายการเลย ลองเพิ่มเมนูอย่าง ข้าวมันไก่ ไข่เจียว หรือโจ๊กได้นะ"
                        }
                    }

                    else -> {
                        "ตอนนี้ยังไม่เจอเมนูไม่เผ็ดในรายการเลย ลองเพิ่มเมนูอย่าง ข้าวมันไก่ ไข่เจียว หรือโจ๊กได้นะ"
                    }
                }
            }

            containsAny(text, listOf("เผ็ด", "แซ่บ", "รสจัด")) -> {
                if (spicyFoods.isNotEmpty()) {
                    buildRecommendationReply(
                        candidates = spicyFoods,
                        reason = "เพราะเป็นเมนูรสจัดที่น่าจะตรงกับที่อยากกิน"
                    )
                } else {
                    "ตอนนี้ยังไม่เจอเมนูเผ็ดชัด ๆ ในรายการเลย"
                }
            }

            containsAny(text, listOf("เส้น", "ก๋วยเตี๋ยว", "ผัดซีอิ๊ว", "ราดหน้า", "บะหมี่", "noodle")) -> {
                if (noodleFoods.isNotEmpty()) {
                    buildRecommendationReply(
                        candidates = noodleFoods,
                        reason = "เพราะเป็นเมนูเส้นที่น่าจะตรงกับที่อยากกิน"
                    )
                } else {
                    "ตอนนี้ยังไม่เจอเมนูเส้นในรายการเลย"
                }
            }

            containsAny(text, listOf("คลีน", "ลดน้ำหนัก", "เฮลตี้", "สุขภาพ")) -> {
                val safeHealthyFoods = (healthyFoods + lightFoods).distinct().filterNot { isSpicyFood(it) }
                if (safeHealthyFoods.isNotEmpty()) {
                    buildRecommendationReply(
                        candidates = safeHealthyFoods,
                        reason = "เพราะเป็นตัวเลือกที่ดูเบาและเฮลตี้กว่าเมนูอื่น"
                    )
                } else {
                    "ตอนนี้ยังไม่เจอเมนูเฮลตี้ชัด ๆ ในรายการเลย"
                }
            }

            containsAny(text, listOf("ง่าย", "เร็ว", "รีบ", "ไม่คิดเยอะ")) -> {
                buildRecommendationReply(
                    candidates = easyFoods.ifEmpty { foodNames },
                    reason = "เพราะเป็นเมนูที่ตัดสินใจง่ายและหากินสะดวก"
                )
            }

            containsAny(text, listOf("ของหวาน", "หวาน", "ขนม", "dessert")) -> {
                if (sweetFoods.isNotEmpty()) {
                    buildRecommendationReply(
                        candidates = sweetFoods,
                        reason = "เพราะน่าจะตอบโจทย์เวลาที่อยากกินอะไรหวาน ๆ"
                    )
                } else {
                    "ตอนนี้ยังไม่เจอเมนูของหวานในรายการเลย"
                }
            }

            containsAny(text, listOf("กินอะไร", "กินไร", "ไม่รู้", "อะไรดี", "สุ่มให้หน่อย")) -> {
                buildRecommendationReply(
                    candidates = foodNames,
                    reason = "เพราะเป็นตัวเลือกที่เด่นจากเมนูที่คุณมีอยู่ตอนนี้"
                )
            }

            else -> {
                buildRecommendationReply(
                    candidates = foodNames,
                    reason = "เพราะดูเข้ากับเมนูที่คุณมีในตอนนี้"
                )
            }
        }
    }

    private fun buildRecommendationReply(candidates: List<String>, reason: String): String {
        if (candidates.isEmpty()) {
            return "ตอนนี้ยังไม่มีเมนูที่ตรงเงื่อนไขเลย"
        }

        val shuffled = candidates.shuffled()
        val mainChoice = shuffled.first()
        val backupChoices = shuffled.drop(1).take(2)

        return buildString {
            append("เราแนะนำ $mainChoice เป็นตัวหลักนะ เพราะ$reason")
            if (backupChoices.isNotEmpty()) {
                append("\nเมนูสำรอง:")
                backupChoices.forEach {
                    append("\n- $it")
                }
            }
        }
    }

    private fun containsAny(text: String, keywords: List<String>): Boolean {
        return keywords.any { text.contains(it) }
    }

    private fun isSpicyFood(name: String): Boolean {
        val text = name.lowercase()
        return containsAny(
            text,
            listOf(
                "กะเพรา", "กระเพรา", "ต้มยำ", "ลาบ", "ยำ", "ส้มตำ", "ตำไทย", "พริก", "ผัดเผ็ด",
                "แกงป่า", "แกงเผ็ด", "กะเพราหมู", "กะเพราไก่",
                "krapao", "kaprao", "tom yum", "tomyum", "somtam", "larb", "yum", "spicy"
            )
        )
    }

    private fun isNonSpicyFood(name: String): Boolean {
        val text = name.lowercase()
        if (isSpicyFood(text)) return false

        return containsAny(
            text,
            listOf(
                "ข้าวมันไก่",
                "ไข่เจียว",
                "โจ๊ก",
                "สุกี้น้ำ",
                "เกาเหลา",
                "ซูชิ",
                "ข้าวต้ม",
                "ข้าวไข่เจียว",
                "omelet",
                "sushi",
                "porridge",
                "chicken rice",
                "rice soup"
            )
        )
    }

    private fun isNoodleFood(name: String): Boolean {
        val text = name.lowercase()
        return containsAny(
            text,
            listOf(
                "เส้น", "ก๋วยเตี๋ยว", "ผัดซีอิ๊ว", "ราดหน้า", "บะหมี่", "มาม่า",
                "pad thai", "noodle", "ramen", "spaghetti", "pasta"
            )
        )
    }

    private fun isHealthyFood(name: String): Boolean {
        val text = name.lowercase()
        return containsAny(
            text,
            listOf(
                "สลัด", "เกาเหลา", "สุกี้น้ำ", "ต้ม", "ต้มจืด", "ปลา", "อกไก่",
                "salad", "sukiyaki", "soup", "fish"
            )
        )
    }

    private fun isLightFood(name: String): Boolean {
        val text = name.lowercase()
        if (isSpicyFood(text)) return false

        return containsAny(
            text,
            listOf(
                "โจ๊ก", "สุกี้น้ำ", "เกาเหลา", "ข้าวต้ม", "ซุป",
                "salad", "soup", "porridge"
            )
        )
    }

    private fun isEasyFood(name: String): Boolean {
        val text = name.lowercase()
        return containsAny(
            text,
            listOf(
                "กะเพรา", "ไข่เจียว", "ข้าวมันไก่", "หมูทอด", "ผัดซีอิ๊ว",
                "pizza", "burger", "sushi", "pad thai", "krapao"
            )
        )
    }

    private fun isSweetFood(name: String): Boolean {
        val text = name.lowercase()
        return containsAny(
            text,
            listOf(
                "บิงซู", "โทสต์", "ไอศกรีม", "เค้ก", "ชาไทย", "นม",
                "toast", "ice cream", "cake", "dessert", "milk tea"
            )
        )
    }
}