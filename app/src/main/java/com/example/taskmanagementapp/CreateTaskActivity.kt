package com.example.taskmanagementapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanagementapp.databinding.ActivityCreateTaskBinding


class CreateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTaskBinding
    private lateinit var db: TasksDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TasksDatabaseHelper(this)

        //Storing data need to happen if only save button is clicked
        binding.createTaskBtn.setOnClickListener{
            val title = binding.TaskTopicBox.text.toString()     //take inputs from user through edit text using dot text method
            val description = binding.TaskDescBox.text.toString()
            val task = Task(0, title, description)
            db.createTask(task)
            finish()    //if CreateTaskActivity closed it automatically goes to MainActivity    //It's like using intent
            Toast.makeText(this, "Task Created Successfully.", Toast.LENGTH_SHORT).show()
        }

    }



    }