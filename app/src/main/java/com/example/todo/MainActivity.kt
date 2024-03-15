package com.example.todo

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var taskEditText: EditText
    private lateinit var addButton: Button
    private lateinit var taskListView: ListView
    private lateinit var taskAdapter: ArrayAdapter<String>

    private val taskList = mutableListOf<String>()
    private val taskStatus = mutableListOf<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskEditText = findViewById(R.id.taskEditText)
        addButton = findViewById(R.id.addButton)
        taskListView = findViewById(R.id.taskListView)

        taskAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, taskList)
        taskListView.adapter = taskAdapter

        addButton.setOnClickListener {
            val newTask = taskEditText.text.toString().trim()
            if (newTask.isNotEmpty()) {
                addTask(newTask)
                taskEditText.text.clear()
            }
        }

        taskListView.setOnItemClickListener { _, _, position, _ ->
            val isChecked = taskStatus[position]
            taskStatus[position] = !isChecked
        }

        taskListView.setOnItemLongClickListener { _, _, position, _ ->
            removeTask(position)
            true
        }
    }

    private fun addTask(task: String) {
        taskList.add(task)
        taskStatus.add(false)
        taskAdapter.notifyDataSetChanged()
    }

    private fun toggleTaskCompletion(position: Int) {
        val isChecked = taskListView.isItemChecked(position)
        taskListView.setItemChecked(position, !isChecked)
    }

    private fun removeTask(position: Int) {
        taskList.removeAt(position)
        taskStatus.removeAt(position)
        taskAdapter.notifyDataSetChanged()
    }
}
