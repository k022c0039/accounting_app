package jp.ac.neec.it.k022c0039.accounting_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

class DailytotalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dailytotal)
    }

    fun CalcButtonClick(view: View) {
        val intent = Intent(this@DailytotalActivity, DailytotalActivity2::class.java)

        val etNames = listOf(
            findViewById<EditText>(R.id.etName1),
            findViewById<EditText>(R.id.etName2),
            findViewById<EditText>(R.id.etName3),
            findViewById<EditText>(R.id.etName4)
        )
        val etSalaries = listOf(
            findViewById<EditText>(R.id.etSalary1),
            findViewById<EditText>(R.id.etSalary2),
            findViewById<EditText>(R.id.etSalary3),
            findViewById<EditText>(R.id.etSalary4)
        )
        val etStartHours = listOf(
            findViewById<EditText>(R.id.etStartTimeH1),
            findViewById<EditText>(R.id.etStartTimeH2),
            findViewById<EditText>(R.id.etStartTimeH3),
            findViewById<EditText>(R.id.etStartTimeH4)
        )
        val etStartMinutes = listOf(
            findViewById<EditText>(R.id.etStartTimeM1),
            findViewById<EditText>(R.id.etStartTimeM2),
            findViewById<EditText>(R.id.etStartTimeM3),
            findViewById<EditText>(R.id.etStartTimeM4)
        )
        val etEndHours = listOf(
            findViewById<EditText>(R.id.etEndTimeH1),
            findViewById<EditText>(R.id.etEndTimeH2),
            findViewById<EditText>(R.id.etEndTimeH3),
            findViewById<EditText>(R.id.etEndTimeH4)
        )
        val etEndMinutes = listOf(
            findViewById<EditText>(R.id.etEndTimeM1),
            findViewById<EditText>(R.id.etEndTimeM2),
            findViewById<EditText>(R.id.etEndTimeM3),
            findViewById<EditText>(R.id.etEndTimeM4)
        )

        val employeeData = ArrayList<EmployeeData>()
        var totalCost = 0.0 // 合計人件費を格納する変数

        for (i in 0..3) {
            try {
                val name = etNames[i].text.toString()
                val salary = etSalaries[i].text.toString().toIntOrNull() ?: 0
                val startHour = etStartHours[i].text.toString().toIntOrNull() ?: 0
                val startMinute = etStartMinutes[i].text.toString().toIntOrNull() ?: 0
                val endHour = etEndHours[i].text.toString().toIntOrNull() ?: 0
                val endMinute = etEndMinutes[i].text.toString().toIntOrNull() ?: 0

                if (name.isEmpty() || salary == 0) continue

                var elapsedHours = endHour - startHour
                var elapsedMinutes = endMinute - startMinute
                if (elapsedMinutes < 0) {
                    elapsedMinutes += 60
                    elapsedHours -= 1
                }
                if (elapsedHours < 0) {
                    elapsedHours += 24
                }

                val totalElapsedTime = elapsedHours + elapsedMinutes / 60.0
                val costs = salary * totalElapsedTime
                totalCost += costs // 合計人件費を更新
                employeeData.add(EmployeeData(name, costs))
            } catch (e: Exception) {
                continue
            }
        }

        // 店舗名と金額の入力欄を取得
        val storeFields = listOf(
            findViewById<EditText>(R.id.etStore1),
            findViewById<EditText>(R.id.etStore2),
            findViewById<EditText>(R.id.etStore3),
            findViewById<EditText>(R.id.etStore4),
            findViewById<EditText>(R.id.etStore5)
        )
        val priceFields = listOf(
            findViewById<EditText>(R.id.etPrice1),
            findViewById<EditText>(R.id.etPrice2),
            findViewById<EditText>(R.id.etPrice3),
            findViewById<EditText>(R.id.etPrice4),
            findViewById<EditText>(R.id.etPrice5)
        )

        val expenseList = ArrayList<ExpenseData>() // 経費データを格納するリスト

        for (i in storeFields.indices) {
            val storeName = storeFields[i].text.toString()
            val price = priceFields[i].text.toString().toIntOrNull() ?: 0
            if (storeName.isNotEmpty() && price > 0) {
                expenseList.add(ExpenseData(storeName, price)) // 店舗名と金額を追加
            }
        }

        // Intent で次のアクティビティにデータを渡す
        intent.putParcelableArrayListExtra("employeeData", employeeData)
        intent.putParcelableArrayListExtra("expenseList", expenseList)
        intent.putExtra("totalCost", totalCost) // 合計人件費を渡す
        startActivity(intent)
    }

    fun BackButtonClick(view: View) {
        finish()
    }
}

// 名前と人件費を Parcelable にするデータクラス
@Parcelize
data class EmployeeData(val name: String, val cost: Double) : Parcelable
// 店舗名と金額を Parcelable にするデータクラス
@Parcelize
data class ExpenseData(val storeName: String, val price: Int) : Parcelable