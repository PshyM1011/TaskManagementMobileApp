package com.example.taskmanagementapp

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanagementapp.databinding.ActivityUpdateTaskBinding

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateTaskBinding
    private lateinit var db: TasksDatabaseHelper      // declare and initialize task database
    private var taskId: Int = -1     // -1 or negative integer is the ID which represents that the value is empty    //that number represent empty ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TasksDatabaseHelper(this)

        taskId = intent.getIntExtra("task_id", -1)   // if due to a technical issue, ID was failed to pass in that case it will return a default value as -1 means nothing
        if(taskId == -1){     //no value received
            finish()       //so close current activity
            return
        }

        //need to know whether which task user clicks  //retrieve title, des relevant to particular task
        //For that need getTastByID function    //from TaskDatabaseHelper
        val task = db.getTaskByID(taskId)     //according to ID, title, des can retrieve
        binding.editTaskTitleBox.setText(task.title)
        binding.editTaskDescBox.setText(task.description)

        //when user clicks update btn, newTitle, newDes user input will set on title and description textView
        binding.editTaskBtn.setOnClickListener{
            val newTitle = binding.editTaskTitleBox.text.toString()    // user inputs saved in relevant variable
            val newDesc = binding.editTaskDescBox.text.toString()
            val updateTask = Task(taskId, newTitle, newDesc)
            db.updateTask(updateTask)     //method within UpdateTaskActivity
            finish()
            Toast.makeText(this,"Task is successfully Updated.", Toast.LENGTH_LONG).show()
        }

    }
}