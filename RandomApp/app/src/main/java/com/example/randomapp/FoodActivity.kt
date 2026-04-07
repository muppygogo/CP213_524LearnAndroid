package com.example.randomapp

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class FoodActivity : ComponentActivity() {

    private lateinit var db: AppDatabase

    private lateinit var btnBack: TextView
    private lateinit var headerTitle: TextView
    private lateinit var headerSubtitle: TextView

    private lateinit var cardWheelMode: LinearLayout
    private lateinit var cardAiMode: LinearLayout
    private lateinit var wheelModeIcon: TextView
    private lateinit var wheelModeTitle: TextView
    private lateinit var wheelModeSubtitle: TextView
    private lateinit var aiModeIcon: TextView
    private lateinit var aiModeTitle: TextView
    private lateinit var aiModeSubtitle: TextView

    private lateinit var sectionTitle: TextView
    private lateinit var menuListText: TextView
    private lateinit var emptyText: TextView
    private lateinit var wheelView: FoodWheelView
    private lateinit var btnSpin: Button
    private lateinit var resultText: TextView
    private lateinit var edtFoodName: EditText
    private lateinit var btnAddFood: Button

    private lateinit var rootFoodPage: View
    private lateinit var foodScrollView: View
    private lateinit var contentContainer: LinearLayout
    private lateinit var headerCard: LinearLayout
    private lateinit var mainFoodCard: LinearLayout
    private lateinit var menuListContainer: View
    private lateinit var bottomNavBar: LinearLayout

    private lateinit var navHome: LinearLayout
    private lateinit var navName: LinearLayout
    private lateinit var navNumber: LinearLayout
    private lateinit var navFood: LinearLayout
    private lateinit var navProfile: LinearLayout

    private var foods: List<FoodEntity> = emptyList()
    private var isDarkMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        val settings = getSharedPreferences("app_settings", MODE_PRIVATE)
        isDarkMode = settings.getBoolean("dark_mode_enabled", false)

        bindViews()
        setupBottomNav()
        applyTheme(isDarkMode)
        setBottomNavSelected("food")
        wheelView.setDarkMode(isDarkMode)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "random_app_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        btnBack.setOnClickListener { finish() }
        cardWheelMode.setOnClickListener { selectWheelMode() }
        cardAiMode.setOnClickListener { selectAiMode() }
        btnAddFood.setOnClickListener { addFood() }

        btnSpin.setOnClickListener {
            lifecycleScope.launch {
                loadFoods()
                spinRandomFood()
            }
        }

        lifecycleScope.launch {
            seedFoodIfNeeded()
            loadFoods()
            selectWheelMode()
        }
    }

    override fun onResume() {
        super.onResume()
        val settings = getSharedPreferences("app_settings", MODE_PRIVATE)
        val latestDarkMode = settings.getBoolean("dark_mode_enabled", false)

        if (latestDarkMode != isDarkMode) {
            isDarkMode = latestDarkMode
            applyTheme(isDarkMode)
            wheelView.setDarkMode(isDarkMode)
            setBottomNavSelected("food")
            selectWheelMode()
        }
    }

    private fun bindViews() {
        rootFoodPage = findViewById(R.id.rootFoodPage)
        foodScrollView = findViewById(R.id.foodScrollView)
        contentContainer = findViewById(R.id.contentContainer)
        headerCard = findViewById(R.id.headerCard)
        btnBack = findViewById(R.id.btnBack)
        headerTitle = findViewById(R.id.headerTitle)
        headerSubtitle = findViewById(R.id.headerSubtitle)

        cardWheelMode = findViewById(R.id.cardWheelMode)
        cardAiMode = findViewById(R.id.cardAiMode)
        wheelModeIcon = findViewById(R.id.wheelModeIcon)
        wheelModeTitle = findViewById(R.id.wheelModeTitle)
        wheelModeSubtitle = findViewById(R.id.wheelModeSubtitle)
        aiModeIcon = findViewById(R.id.aiModeIcon)
        aiModeTitle = findViewById(R.id.aiModeTitle)
        aiModeSubtitle = findViewById(R.id.aiModeSubtitle)

        mainFoodCard = findViewById(R.id.mainFoodCard)
        sectionTitle = findViewById(R.id.sectionTitle)
        menuListText = findViewById(R.id.menuListText)
        emptyText = findViewById(R.id.emptyText)
        wheelView = findViewById(R.id.wheelView)
        btnSpin = findViewById(R.id.btnSpin)
        resultText = findViewById(R.id.resultText)
        edtFoodName = findViewById(R.id.edtFoodName)
        btnAddFood = findViewById(R.id.btnAddFood)
        menuListContainer = findViewById(R.id.menuListContainer)
        bottomNavBar = findViewById(R.id.bottomNavBar)

        navHome = findViewById(R.id.navHome)
        navName = findViewById(R.id.navName)
        navNumber = findViewById(R.id.navNumber)
        navFood = findViewById(R.id.navFood)
        navProfile = findViewById(R.id.navProfile)
    }

    private fun setupBottomNav() {
        navHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        navName.setOnClickListener {
            startActivity(Intent(this, NameActivity::class.java))
            finish()
        }

        navNumber.setOnClickListener {
            startActivity(Intent(this, NumberActivity::class.java))
            finish()
        }

        navFood.setOnClickListener { }

        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }

    private fun applyTheme(darkMode: Boolean) {
        val pageBg = if (darkMode) Color.parseColor("#1E1B22") else Color.parseColor("#F7E5EC")
        val cardBg = if (darkMode) Color.parseColor("#362F3D") else Color.parseColor("#FFFFFF")
        val inputBg = if (darkMode) Color.parseColor("#4A404F") else Color.parseColor("#F5E8EE")
        val listBg = if (darkMode) Color.parseColor("#2F2936") else Color.parseColor("#F7EDF1")
        val textPrimary = if (darkMode) Color.parseColor("#FFFFFF") else Color.parseColor("#44333E")
        val textSecondary = if (darkMode) Color.parseColor("#D2B9C6") else Color.parseColor("#6C5863")
        val accent = Color.parseColor("#E14D77")
        val accentSoft = if (darkMode) Color.parseColor("#4A3141") else Color.parseColor("#F7E9EE")
        val navBg = if (darkMode) Color.parseColor("#2E2833") else Color.parseColor("#F9F8F8")
        val normalNavText = if (darkMode) Color.parseColor("#C5AAB8") else Color.parseColor("#AD8D9C")

        rootFoodPage.setBackgroundColor(pageBg)
        foodScrollView.setBackgroundColor(pageBg)
        contentContainer.setBackgroundColor(pageBg)

        headerTitle.setTextColor(if (darkMode) Color.WHITE else Color.parseColor("#44333E"))
        headerSubtitle.setTextColor(if (darkMode) Color.parseColor("#F6EAF0") else Color.parseColor("#6B6583"))
        btnBack.setTextColor(Color.WHITE)

        cardWheelMode.background = roundedDrawable(cardBg, 28f)
        cardAiMode.background = roundedDrawable(cardBg, 28f)

        wheelModeIcon.background = roundedDrawable(
            if (darkMode) Color.parseColor("#4A404F") else Color.parseColor("#F8E9EE"),
            18f
        )
        aiModeIcon.background = roundedDrawable(
            if (darkMode) Color.parseColor("#4A404F") else Color.parseColor("#EEF0FB"),
            18f
        )

        wheelModeTitle.setTextColor(textPrimary)
        wheelModeSubtitle.setTextColor(textSecondary)
        aiModeTitle.setTextColor(textPrimary)
        aiModeSubtitle.setTextColor(textSecondary)

        mainFoodCard.background = roundedDrawable(cardBg, 30f)
        sectionTitle.setTextColor(if (darkMode) Color.parseColor("#F6EAF0") else Color.parseColor("#725A68"))

        edtFoodName.background = roundedDrawable(inputBg, 18f)
        edtFoodName.setTextColor(if (darkMode) Color.WHITE else Color.parseColor("#4A3942"))
        edtFoodName.setHintTextColor(if (darkMode) Color.parseColor("#C5AAB8") else Color.parseColor("#A17F8E"))

        btnAddFood.backgroundTintList = null
        btnAddFood.background = roundedDrawable(accent, 16f)
        btnAddFood.setTextColor(Color.WHITE)

        menuListContainer.background = roundedDrawable(
            if (darkMode) Color.parseColor("#8C7AC8") else Color.parseColor("#C9B4E5"),
            22f
        )

        menuListText.background = roundedDrawable(listBg, 18f)
        menuListText.setTextColor(if (darkMode) Color.WHITE else Color.parseColor("#9A7484"))
        menuListText.alpha = 1f
        emptyText.setTextColor(if (darkMode) Color.parseColor("#E0CFD8") else Color.parseColor("#9A7484"))

        btnSpin.background = roundedDrawable(accent, 22f)
        btnSpin.setTextColor(Color.WHITE)

        resultText.setTextColor(accent)
        bottomNavBar.background = roundedDrawable(navBg, 28f)

        styleBottomNavItem(navHome, false, accentSoft, accent, normalNavText)
        styleBottomNavItem(navName, false, accentSoft, accent, normalNavText)
        styleBottomNavItem(navNumber, false, accentSoft, accent, normalNavText)
        styleBottomNavItem(navFood, true, accentSoft, accent, normalNavText)
        styleBottomNavItem(navProfile, false, accentSoft, accent, normalNavText)

        wheelView.setDarkMode(darkMode)
    }

    private fun setBottomNavSelected(selected: String) {
        val accentSoft = if (isDarkMode) Color.parseColor("#4A3141") else Color.parseColor("#F7E9EE")
        val accent = Color.parseColor("#E14D77")
        val normalNavText = if (isDarkMode) Color.parseColor("#C5AAB8") else Color.parseColor("#AD8D9C")

        styleBottomNavItem(navHome, selected == "home", accentSoft, accent, normalNavText)
        styleBottomNavItem(navName, selected == "name", accentSoft, accent, normalNavText)
        styleBottomNavItem(navNumber, selected == "number", accentSoft, accent, normalNavText)
        styleBottomNavItem(navFood, selected == "food", accentSoft, accent, normalNavText)
        styleBottomNavItem(navProfile, selected == "profile", accentSoft, accent, normalNavText)
    }

    private fun styleBottomNavItem(
        item: LinearLayout,
        isSelected: Boolean,
        selectedBg: Int,
        selectedText: Int,
        normalText: Int
    ) {
        item.alpha = if (isSelected) 1f else 0.78f
        item.background = roundedDrawable(if (isSelected) selectedBg else Color.TRANSPARENT, 22f)
        item.scaleX = if (isSelected) 1.04f else 1f
        item.scaleY = if (isSelected) 1.04f else 1f
        setTextColorRecursive(item, if (isSelected) selectedText else normalText)
    }

    private fun roundedDrawable(color: Int, radiusDp: Float): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = radiusDp * resources.displayMetrics.density
            setColor(color)
        }
    }

    private fun setTextColorRecursive(view: View, color: Int) {
        when (view) {
            is TextView -> view.setTextColor(color)
            is ViewGroup -> {
                for (i in 0 until view.childCount) {
                    setTextColorRecursive(view.getChildAt(i), color)
                }
            }
        }
    }

    private fun addFood() {
        val newFood = edtFoodName.text.toString().trim()

        if (newFood.isBlank()) {
            Toast.makeText(this, "กรุณาใส่ชื่ออาหาร", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.foodDao().insert(FoodEntity(name = newFood))
            }
            edtFoodName.setText("")
            loadFoods()
            Toast.makeText(this@FoodActivity, "เพิ่มเมนูแล้ว", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun loadFoods() {
        val loadedFoods = withContext(Dispatchers.IO) {
            db.foodDao().getAllFoods()
        }

        foods = loadedFoods

        if (foods.isEmpty()) {
            menuListText.text = ""
            emptyText.visibility = View.VISIBLE
            wheelView.setItems(listOf("ไม่มีเมนู"))
            btnSpin.isEnabled = false
        } else {
            emptyText.visibility = View.GONE
            btnSpin.isEnabled = true
            val foodNames = foods.map { it.name }
            menuListText.text = foodNames.joinToString("\n")
            wheelView.setItems(foodNames)
        }
    }

    private fun spinRandomFood() {
        if (foods.isEmpty()) {
            resultText.visibility = View.VISIBLE
            resultText.text = "ยังไม่มีเมนูสำหรับสุ่ม"
            return
        }

        val chosenIndex = Random.nextInt(foods.size)
        val chosenFood = foods[chosenIndex].name

        resultText.visibility = View.INVISIBLE

        wheelView.spinToIndex(chosenIndex) {
            resultText.visibility = View.VISIBLE
            resultText.text = "เมนูที่สุ่มได้: $chosenFood"
        }
    }

    private fun selectWheelMode() {
        cardWheelMode.alpha = 1f
        cardAiMode.alpha = if (isDarkMode) 0.82f else 0.9f
        resultText.visibility = View.GONE
    }

    private fun selectAiMode() {
        cardWheelMode.alpha = if (isDarkMode) 0.82f else 0.9f
        cardAiMode.alpha = 1f
        resultText.visibility = View.VISIBLE
        resultText.text = "AI แนะนำ: ลองเริ่มจากเมนูที่กินง่าย เช่น ข้าวมันไก่"
    }

    private suspend fun seedFoodIfNeeded() {
        val count = withContext(Dispatchers.IO) {
            db.foodDao().getCount()
        }

        if (count == 0) {
            withContext(Dispatchers.IO) {
                db.foodDao().insertAll(
                    listOf(
                        FoodEntity(name = "Pad Thai"),
                        FoodEntity(name = "Krapao"),
                        FoodEntity(name = "Somtam"),
                        FoodEntity(name = "Pizza"),
                        FoodEntity(name = "Burger"),
                        FoodEntity(name = "Sushi")
                    )
                )
            }
        }
    }
}