package com.diljith.whiterabbit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.diljith.whiterabbit.R
import com.diljith.whiterabbit.adapter.EmployeeListAdapter
import com.diljith.whiterabbit.database.DBOpenHelper
import com.diljith.whiterabbit.model.Employee
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), EmployeeListAdapter.OnItemClick {

    lateinit var listAdapter: EmployeeListAdapter
    private val list = ArrayList<Employee>()
    private val listFromDb = ArrayList<Employee>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rcList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        listAdapter = EmployeeListAdapter(getList(), this, this)
        rcList.adapter = listAdapter
        getAllTodoDetails()
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterList(s.toString().trim())
            }
        })

    }

    override fun onItemClick(data: Employee) {
        val intent = Intent(this, ProfileDetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("data", data)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val dbHelper = DBOpenHelper(this, null)
        dbHelper.clearAllLocalTableData()
        dbHelper.close()
    }

    private fun getAllTodoDetails() {
        list.clear()
        val dbHelper = DBOpenHelper(this, null)
        dbHelper.getDetails()?.let {
            list.addAll(it)
            listFromDb.addAll(it)
        }
        dbHelper.close()
        listAdapter.notifyDataSetChanged()

    }

    private fun getList(): ArrayList<Employee> {
        return list
    }

    private fun filterList(text: String) {
        list.clear()
        if (text.isEmpty() || text == "") {
            list.addAll(listFromDb)
        } else {
            val filterList = ArrayList<Employee>()

            for (i in listFromDb) {
                if (i.name.lowercase().startsWith(text.lowercase()) ||
                    i.email.lowercase().startsWith(text.lowercase())
                ) {
                    filterList.add(i)
                }
            }
            list.addAll(filterList)
        }
        listAdapter.notifyDataSetChanged()
    }


}