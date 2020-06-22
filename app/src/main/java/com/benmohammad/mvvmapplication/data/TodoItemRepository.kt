package com.benmohammad.mvvmapplication.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.benmohammad.mvvmapplication.data.database.TodoItem
import com.benmohammad.mvvmapplication.data.database.TodoItemDao
import com.benmohammad.mvvmapplication.data.database.TodoItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TodoItemRepository(application: Application) {

    private val todoItemDao: TodoItemDao
    private val allTodoItems: LiveData<MutableList<TodoItem>>

    init {
        val database = TodoItemDatabase.getInstance(application.applicationContext)
        todoItemDao = database!!.todoDao()
        allTodoItems = todoItemDao.getAllTodoList()
    }

    fun saveTodoItems(todoItems: List<TodoItem>) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoItemDao.saveTodoItems(todoItems)
        }
    }
    fun saveTodoItem(todoItem: TodoItem) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoItemDao.saveTodoItem(todoItem)
        }
    }

    fun updateTodoItem(todoItem: TodoItem) = runBlocking {
        this.launch(Dispatchers.IO) {  }
            todoItemDao.updateTodoItem(todoItem)
    }

    fun deleteTodoItem(todoItem: TodoItem) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoItemDao.deleteTodoItem(todoItem)
        }
    }

    fun getAllTodoList(): LiveData<MutableList<TodoItem>> {
        return allTodoItems
    }
}