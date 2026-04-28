# Random App (แอปสุ่ม)

## 1. Concept

**Random App** เป็นแอปพลิเคชันสำหรับช่วยผู้ใช้ตัดสินใจโดยการสุ่มรายการต่าง ๆ เช่น

* สุ่มอาหาร
* สุ่มชื่อ
* สุ่มตัวเลข

ผู้ใช้สามารถเลือกประเภทการสุ่มและกดปุ่มเพื่อให้ระบบทำการสุ่มและแสดงผลลัพธ์บนหน้าจอทันที

แนวคิดหลักของแอปคือช่วยลดความลังเลในการเลือกสิ่งต่าง ๆ ในชีวิตประจำวัน เช่น

* ไม่รู้จะกินอะไร
* ต้องเลือกชื่อจากรายชื่อหลายคน

แอปพลิเคชันนี้ถูกพัฒนาบนระบบปฏิบัติการ **Android** โดยใช้ **Kotlin** และ **Jetpack Compose** สำหรับการออกแบบ UI

---

# 2. Project Scope

แอปพลิเคชันสามารถสุ่มรายการได้ดังนี้

* สุ่มอาหาร
* สุ่มชื่อ
* สุ่มตัวเลข

ผู้ใช้สามารถกดปุ่มเพื่อสุ่ม และระบบจะแสดงผลลัพธ์บนหน้าจอทันที

---

# 3. Application Flow

1. ผู้ใช้งานเปิดแอปพลิเคชัน
2. ระบบแสดงหน้าเมนูหลัก
3. ผู้ใช้งานเลือกประเภทการสุ่ม
4. ผู้ใช้งานป้อนข้อมูลหรือกำหนดช่วง
5. ผู้ใช้งานกดปุ่มสุ่ม
6. ระบบประมวลผลการสุ่ม
7. ระบบแสดงผลลัพธ์บนหน้าจอ

---

# 4. Screen Structure

1. **Main Menu** – หน้าเมนูหลัก
2. **Random Food** – หน้าสุ่มอาหาร
3. **Random Name** – หน้าสุ่มชื่อ
4. **Random Number** – หน้าสุ่มตัวเลข

---

# 5. System Overview

ผู้ใช้งานสามารถเลือกประเภทของการสุ่มที่ต้องการได้จากหน้าเมนูหลัก

### Random Food

ระบบจะสุ่มเมนูอาหารจากรายการที่กำหนดไว้

### Random Name

ผู้ใช้สามารถกรอกรายชื่อ จากนั้นระบบจะสุ่มเลือกหนึ่งชื่อ

### Random Number

ผู้ใช้กำหนดช่วงตัวเลข เช่น **1 – 100** แล้วระบบจะสุ่มตัวเลขในช่วงนั้น

---

# 6. MVP Features (Minimum Viable Product)

ฟีเจอร์หลักของแอปในเวอร์ชันแรก

* หน้าเมนูหลักสำหรับเลือกประเภทการสุ่ม
* ระบบสุ่มอาหารจากรายการเมนู
* ระบบสุ่มชื่อจากรายชื่อที่ผู้ใช้ป้อน
* ระบบสุ่มตัวเลขตามช่วงที่กำหนด
* แสดงผลลัพธ์การสุ่มบนหน้าจอ
* UI พื้นฐานด้วย Jetpack Compose

---

# 7. Setup Phase

ขั้นตอนการเริ่มต้นพัฒนาแอป

1. ติดตั้ง **Android Studio**
2. สร้าง Android Project ด้วย **Kotlin**
3. ตั้งค่า **Jetpack Compose** สำหรับ UI
4. ออกแบบโครงสร้าง Activity ของแอป
5. สร้างหน้าจอหลักและหน้าการสุ่มแต่ละประเภท
6. เขียน logic สำหรับการสุ่มข้อมูล
7. ทดสอบการทำงานของแอปบน Emulator หรืออุปกรณ์จริง

---

# 8. Tech Stack

Technology ที่ใช้ในการพัฒนา

| Technology           | Description                                      |
| -------------------- | ------------------------------------------------ |
| Platform             | Android                                          |
| Programming Language | Kotlin                                           |
| IDE                  | Android Studio                                   |
| UI Framework         | Jetpack Compose                                  |
| Architecture         | MVVM (Model - View - ViewModel)                  |
| Navigation           | Multi Activity + startActivity                   |
| Database             | SQLite / Room                                    |
| Offline Support      | Core features ใช้งานได้โดยไม่ต้องใช้อินเทอร์เน็ต |

---

# 9. Technology Implementation

### Android App

พัฒนาเป็น Mobile Application บนระบบ Android

### Jetpack Compose UI

ใช้ Jetpack Compose สำหรับสร้าง UI แทนการใช้ XML

### MVVM Basic

ใช้สถาปัตยกรรม **MVVM** เพื่อแยกส่วนของระบบ

* **Model** → Data
* **View** → UI
* **ViewModel** → Logic

### Multi Activity

ใช้หลาย Activity เช่น

* `MainActivity`
* `FoodActivity`
* `NameActivity`
* `NumberActivity`

และใช้ `startActivity()` สำหรับการเปลี่ยนหน้า เพื่อเรียนรู้ **Android Activity LifeCycle**

---

# 10. Next Phase (Future Development)

ฟีเจอร์ที่สามารถพัฒนาเพิ่มในอนาคต

* เพิ่มระบบ **History การสุ่ม**
* เพิ่มระบบ **Favorite รายการที่ชอบ**
* เพิ่ม **รูปภาพของเมนูอาหาร**
* เพิ่ม **Animation ตอนสุ่ม**
* เพิ่ม **เสียงประกอบการสุ่ม**
* เพิ่มการสุ่มแบบ **Spin Wheel**

---

## Mockup UI


<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/b11aae7b-5122-4869-b2a4-ebeb5c30d77d" />

