package com.example.taskmanagementapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(private var tasks: List<Task>, context: Context):
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    private val db: TasksDatabaseHelper = TasksDatabaseHelper(context)

    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){       //item view which extends RecycleView.ViewHolder

        val titleTxtView: TextView = itemView.findViewById(R.id.titleTxt)
        val descTxtView: TextView = itemView.findViewById(R.id.description)
        val editBtn: ImageView = itemView.findViewById(R.id.Edit_icon)
        //set intent on update button and pass the id
        val deleteBtn: ImageView = itemView.findViewById(R.id.deleteIcon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {   //setup item layout view

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_task_layout,parent,false)   //store this within view variable
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size     // return size


                                    //set the data        //helps to determine which item was clicked             //set data on the element
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
         holder.titleTxtView.text = task.title   //whatever title return by user need to display within this
          holder.descTxtView.text = task.description


         holder.editBtn.setOnClickListener{                                        //we want to pass ID from this to UpdateTaskActivity
             val intent = Intent(holder.itemView.context, UpdateTaskActivity::class.java).apply{
                putExtra("task_id", task.id)      //task_id : like a key value & it helps to pass ID to UpdateActivity
             }
             holder.itemView.context.startActivity(intent)
         }
          //when user selects editIcon it will lead him to UpdateTaskActivity
           // ID will help to determine the position of task clicked by user

             holder.deleteBtn.setOnClickListener{
                 db.deleteTask(task.id)  //based on id going to delete entire row
                 refreshTaskData(db.getAllTasks())     //once note is deleted, then list will refresh itself and reload a new set of tasks
                 Toast.makeText(holder.itemView.context, "Task Deleted Successfully!", Toast.LENGTH_SHORT).show()
             }

    }


    //We have to keep refreshing list whenever a new task is inserted otherwise it will display old data

    fun refreshTaskData(newTasks: List<Task>){
        tasks = newTasks     // all tasks become newTasks list
        notifyDataSetChanged()
    }

}