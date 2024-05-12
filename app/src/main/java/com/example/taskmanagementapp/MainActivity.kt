package com.example.taskmanagementapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagementapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TasksDatabaseHelper
    private lateinit var tasksAdapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        db = TasksDatabaseHelper(this)
        tasksAdapter = TasksAdapter(db.getAllTasks(),this)     // list of every notes in database and contex

        binding.RecycleVTasks.layoutManager = LinearLayoutManager(this)

        binding.RecycleVTasks.adapter = tasksAdapter       //set tasksAdapter on RecyclerView


        binding.addBtn.setOnClickListener {
            val intent = Intent(this@MainActivity,CreateTaskActivity::class.java)
            startActivity(intent)
        }

    }

    //In a state of running, when app is open it is in onResume state then automatically refresh the data
    override fun onResume() {
        super.onResume()
        tasksAdapter.refreshTaskData(db.getAllTasks())    //data coming from database
    }
}





