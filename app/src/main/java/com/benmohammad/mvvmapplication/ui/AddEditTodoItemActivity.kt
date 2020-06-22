package com.benmohammad.mvvmapplication.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.benmohammad.mvvmapplication.R
import com.benmohammad.mvvmapplication.data.database.TodoItem
import com.benmohammad.mvvmapplication.notification.NotificationUtils
import com.benmohammad.mvvmapplication.utilities.Constants
import com.benmohammad.mvvmapplication.utilities.convertMillis
import com.benmohammad.mvvmapplication.utilities.dateToMillis
import kotlinx.android.synthetic.main.activity_add_edit_todo_item.*
import java.util.*

class AddEditTodoItemActivity: AppCompatActivity() {

    private var mDueMonth: Int = 0
    private var mDueDay: Int = 0
    private var mDueYear: Int = 0
    private var mDueHour: Int = 0
    private var mDueMinute: Int = 0
    private var dueDate: Long = 0
    private var dateSelected = false
    private var timeSelected = false

    var todoItem: TodoItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_todo_item)

        val intent = intent
        if(intent != null && intent.hasExtra(Constants.KEY_INTENT)) {
            val todoItem: TodoItem = intent.getParcelableExtra(Constants.KEY_INTENT)
            this.todoItem = todoItem

            if(todoItem.dueTime!!.toInt() != 0) {
                dateSelected = true
                timeSelected = true
                val list = convertMillis(todoItem.dueTime)

                mDueDay = list[0]
                mDueMonth = list[1]
                mDueYear = list[2]
                mDueHour = list[3]
                mDueMinute = list[4]
            }
            fillUIWithItemData(todoItem)
        }
        tv_todo_due_date.setOnClickListener {
            showDatePickerDialog()
        }

        tv_todo_due_time.setOnClickListener {
            showTimePickerDialog()
        }

        title =
            if(todoItem != null) getString(R.string.edit_item) else getString(
                R.string.create_item
            )

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_edit_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.save_todo_item -> {
                setDueDateInMillis()

                saveTodoItem()
            }
        }
        return true
    }

    private fun saveTodoItem() {
        if(validateFields()) {
            val id = if(todoItem != null) todoItem?.id else null
            val todo = TodoItem(
                id = id,
                title = et_todo_title.text.toString(),
                description = et_todo_description.text.toString(),
                tags = et_todo_tags.text.toString(),
                dueTime = dueDate,
                completed = todoItem?.completed ?: false
            )

            val intent = Intent()
            intent.putExtra(Constants.KEY_INTENT, todo)
            setResult(Activity.RESULT_OK)
            if(todo.dueTime!!  > 0) {
                NotificationUtils().setNotification(todo, this)
            }
            finish()
        }
    }



    private fun validateFields(): Boolean {
        if(et_todo_title.text.isEmpty()) {
            til_todo_title.error = "Please enter title"
            et_todo_title.requestFocus()
            return false
        }
        if(et_todo_description.text.isEmpty()) {
            til_todo_description.error = "Please enter description"
            et_todo_description.requestFocus()
            return false
        }
        if(et_todo_tags.text.isEmpty()) {
            til_todo_tags.error = "Please provide at least one tag"
            til_todo_tags.requestFocus()
            return false
        }
        Toast.makeText(this, "This isa saved successfully", Toast.LENGTH_SHORT).show()
        return true
    }



    private fun setDueDateInMillis() {
        if(timeSelected && !dateSelected) {
            mDueYear = Calendar.getInstance().get(Calendar.YEAR)
            mDueMonth = Calendar.getInstance().get(Calendar.MONTH)
            mDueDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

            dueDate = dateToMillis(mDueDay, mDueMonth, mDueYear, mDueMinute, mDueHour)
        } else if(!timeSelected && dateSelected) {
            mDueHour = 0
            mDueMinute = 0

            dueDate = dateToMillis(mDueDay, mDueMonth, mDueYear, mDueMinute, mDueHour)
        } else if(timeSelected && dateSelected) {
            dueDate = dateToMillis(mDueDay, mDueMonth, mDueYear, mDueMinute, mDueHour)
        }
    }
    private fun showDatePickerDialog() {}

    private fun showTimePickerDialog() {}

    private fun fillUIWithItemData(todoItem: TodoItem) {}
}