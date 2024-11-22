package jp.ac.neec.it.k022c0039.accounting_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SettlementActivity : AppCompatActivity() {

    private var grandTotal: Int = 0
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement)

        // Intentからデータを取得
        name = intent?.getStringExtra("name") ?: "未入力"

        // Intentからデータを取得
        val selectedItems = intent.getStringArrayListExtra("selectedItems")
        val totalAmount = intent.getIntExtra("totalAmount", 0)
        val taxAmount = intent.getDoubleExtra("taxAmount", 0.0).toInt()  // 小数点を切り捨てて整数に変換
        grandTotal = totalAmount + taxAmount  // 合計金額を計算

        selectedItems?.let {
            val textView = findViewById<TextView>(R.id.textView) // 表示用TextView

            // 表示内容を組み立て
            textView.text =
                it.joinToString("\n") + "\n小計 ¥$totalAmount\n税金 ¥$taxAmount\n合計金額 ¥$grandTotal"
        }
    }

    fun settlementButtonClick(view: View) {
        val intent = Intent(this@SettlementActivity, MainActivity::class.java)

        // SharedPreferencesを取得
        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Gsonを使用してデータリストを更新
        val gson = Gson()
        val savedDataJson = sharedPreferences.getString("dataList", "[]") ?: "[]"
        val type = object : TypeToken<MutableList<SavedData>>() {}.type
        val savedDataList: MutableList<SavedData> = gson.fromJson(savedDataJson, type)

        // 新しいデータを追加
        val newData = SavedData(name ?: "未入力", grandTotal)
        savedDataList.add(newData)

        // JSONに変換して保存
        val updatedJson = gson.toJson(savedDataList)
        editor.putString("dataList", updatedJson)
        editor.apply()

        startActivity(intent)
    }

    fun backButtonClick(view: View){
        finish()
    }
}