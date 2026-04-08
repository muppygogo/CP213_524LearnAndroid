package com.example.randomapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AiFoodChatActivity : ComponentActivity() {

    private lateinit var db: AppDatabase

    private lateinit var btnBack: Button
    private lateinit var chatText: TextView
    private lateinit var editMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var chatScroll: ScrollView

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
        chatText = findViewById(R.id.chatText)
        editMessage = findViewById(R.id.editMessage)
        btnSend = findViewById(R.id.btnSend)
        chatScroll = findViewById(R.id.chatScroll)

        addMessage("🤖: สวัสดี เราช่วยแนะนำอาหารให้ได้นะ ลองพิมพ์มาว่าอยากกินแบบไหน")

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

        addMessage("👤: $userMsg")
        editMessage.setText("")

        val reply = generateReplyFromDb(userMsg)
        addMessage("🤖: $reply")
    }

    private fun addMessage(msg: String) {
        chatText.append(msg + "\n\n")
        chatScroll.post {
            chatScroll.fullScroll(View.FOCUS_DOWN)
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
                val choices = pickRandomChoices(lightFoods.ifEmpty { foodNames }, 3)
                "เย็นนี้ลองเลือกดู:\n- ${choices.joinToString("\n- ")}\nน่าจะอิ่มกำลังดีนะ"
            }

            containsAny(text, listOf("กลางวัน", "มื้อกลางวัน")) -> {
                val choices = pickRandomChoices(easyFoods.ifEmpty { foodNames }, 3)
                "มื้อกลางวันลองดูพวกนี้ไหม:\n- ${choices.joinToString("\n- ")}"
            }

            containsAny(text, listOf("ดึก", "กลางคืน")) -> {
                val safeLateNightFoods = (lightFoods + healthyFoods).distinct().filterNot { isSpicyFood(it) }
                if (safeLateNightFoods.isNotEmpty()) {
                    val choices = pickRandomChoices(safeLateNightFoods, 3)
                    "ถ้ากินตอนดึก ลองดูพวกนี้ไหม:\n- ${choices.joinToString("\n- ")}"
                } else {
                    "ตอนนี้ยังไม่เจอเมนูเบา ๆ สำหรับตอนดึกในรายการเลย"
                }
            }

            // ต้องเช็กไม่เผ็ดก่อนเผ็ด
            containsAny(text, listOf("ไม่เผ็ด", "ไม่เอาเผ็ด", "ไม่กินเผ็ด")) -> {
                when {
                    nonSpicyFoods.isNotEmpty() -> {
                        val choices = pickRandomChoices(nonSpicyFoods, 3)
                        "ถ้าอยากกินไม่เผ็ด ลองเลือกดู:\n- ${choices.joinToString("\n- ")}"
                    }

                    lightFoods.isNotEmpty() -> {
                        val safeLightFoods = lightFoods.filterNot { isSpicyFood(it) }

                        if (safeLightFoods.isNotEmpty()) {
                            val choices = pickRandomChoices(safeLightFoods, 3)
                            "ถ้าอยากกินไม่เผ็ด ลองเลือกดู:\n- ${choices.joinToString("\n- ")}"
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
                    val choices = pickRandomChoices(spicyFoods, 3)
                    "ถ้าอยากกินเผ็ด ๆ ลองเลือกดู:\n- ${choices.joinToString("\n- ")}"
                } else {
                    "ตอนนี้ยังไม่เจอเมนูเผ็ดชัด ๆ ในรายการเลย"
                }
            }

            containsAny(text, listOf("เส้น", "ก๋วยเตี๋ยว", "ผัดซีอิ๊ว", "ราดหน้า", "บะหมี่", "noodle")) -> {
                if (noodleFoods.isNotEmpty()) {
                    val choices = pickRandomChoices(noodleFoods, 3)
                    "ถ้าอยากกินเมนูเส้น ลองดูพวกนี้ไหม:\n- ${choices.joinToString("\n- ")}"
                } else {
                    "ตอนนี้ยังไม่เจอเมนูเส้นในรายการเลย"
                }
            }

            containsAny(text, listOf("คลีน", "ลดน้ำหนัก", "เฮลตี้", "สุขภาพ")) -> {
                val safeHealthyFoods = (healthyFoods + lightFoods).distinct().filterNot { isSpicyFood(it) }
                if (safeHealthyFoods.isNotEmpty()) {
                    val choices = pickRandomChoices(safeHealthyFoods, 3)
                    "ถ้าอยากกินแบบเฮลตี้ ลองดูพวกนี้ไหม:\n- ${choices.joinToString("\n- ")}"
                } else {
                    "ตอนนี้ยังไม่เจอเมนูเฮลตี้ชัด ๆ ในรายการเลย"
                }
            }

            containsAny(text, listOf("ง่าย", "เร็ว", "รีบ", "ไม่คิดเยอะ")) -> {
                val choices = pickRandomChoices(easyFoods.ifEmpty { foodNames }, 3)
                "เอาเมนูง่าย ๆ ลองเลือกจากพวกนี้ได้เลย:\n- ${choices.joinToString("\n- ")}"
            }

            containsAny(text, listOf("ของหวาน", "หวาน", "ขนม", "dessert")) -> {
                if (sweetFoods.isNotEmpty()) {
                    val choices = pickRandomChoices(sweetFoods, 3)
                    "ถ้าอยากกินของหวาน ลองดูพวกนี้ไหม:\n- ${choices.joinToString("\n- ")}"
                } else {
                    "ตอนนี้ยังไม่เจอเมนูของหวานในรายการเลย"
                }
            }

            containsAny(text, listOf("กินอะไร", "กินไร", "ไม่รู้", "อะไรดี", "สุ่มให้หน่อย")) -> {
                val choices = pickRandomChoices(foodNames, 3)
                "ลองเลือกดู:\n- ${choices.joinToString("\n- ")}"
            }

            else -> {
                val choices = pickRandomChoices(foodNames, 3)
                "จากเมนูที่คุณมี เราขอแนะนำ:\n- ${choices.joinToString("\n- ")}"
            }
        }
    }

    private fun pickRandomChoices(list: List<String>, count: Int): List<String> {
        return if (list.size <= count) list.shuffled()
        else list.shuffled().take(count)
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