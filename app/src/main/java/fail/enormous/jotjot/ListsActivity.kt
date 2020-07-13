package fail.enormous.jotjot

import android.app.DialogFragment
import android.content.pm.ActivityInfo
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import fail.enormous.jotjot.db.TodoListDBContract
import fail.enormous.jotjot.db.TodoListDBContract.DATABASE_NAME
import fail.enormous.jotjot.db.room.AppDatabase
import kotlinx.android.synthetic.main.activity_lists.*


class ListsActivity : AppCompatActivity(), NewTaskDialogFragment.NewTaskDialogListener {

    private var listView: ListView? = null
    private var listAdapter: TaskListAdapter? = null
    private var todoListItems = ArrayList<Task>()
    private var selectedItem = -1
    private var database: AppDatabase? = null // declaring private variables

//    private var dbHelper: TodoListDBHelper = TodoListDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // Force portrait mode

        listView = findViewById(R.id.list_view)
        val listsButton = findViewById<FloatingActionButton>(R.id.listsAddButton) // Find listsButton as R.id.listsAddButton
        val lv = findViewById<ListView>(R.id.list_view)
        val editButton = findViewById<ImageButton>(R.id.edit_item)
        val deleteButton = findViewById<ImageButton>(R.id.delete_item)

        this.database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME) // Defining the database
            .addMigrations(object : Migration(TodoListDBContract.DATABASE_VERSION - 1, TodoListDBContract.DATABASE_VERSION) {
                override fun migrate(database: SupportSQLiteDatabase) { // use Room but support migrating  SQL
                }
            }).build() // build database based on parameters

        populateListView() // fill ListView with items from database

        editButton.setOnClickListener{ editItems() }
        deleteButton.setOnClickListener{ deleteItems() }
       // list_view.onItemClickListener = OnItemClickListener { parent, view, position, id ->
       //     showUpdateTaskUI(position)
       //     print('h')
       // }

        listsButton.setOnClickListener { showNewTaskUI() } // call showNewTaskUI when listsButton is pressed

       /* list_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            showUpdateTaskUI(selected = position) // set listener for when a ListView item is tapped
            val selectedItem = parent.getItemAtPosition(position) as String
            Toast.makeText(applicationContext, selectedItem, LENGTH_SHORT).show()
        }
        */
    }


    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.to_do_list_menu, menu)
        val editItem = menu.findItem(R.id.edit_item)
        val deleteItem = menu.findItem(R.id.delete_item)
        val completeItem = menu.findItem(R.id.mark_as_done_item)

        if (showMenuItems) {
            editItem.isVisible = true
            deleteItem.isVisible = true
            completeItem.isVisible = true
        }

        return true
    }
     */

    private fun editItems() {
        if (-1 != selectedItem) {
            val updateFragment = NewTaskDialogFragment.newInstance(
                R.string.update_task_dialog_title,
                todoListItems[selectedItem].taskDetails
            )
            updateFragment.show(this.fragmentManager, "updatetask")
        }
    }

    private fun deleteItems() {
            val selectedTask = todoListItems[selectedItem]
            DeleteTaskAsyncTask(database, selectedTask).execute()
            //                dbHelper.deleteTask(selectedTask)

            todoListItems.removeAt(selectedItem)
            listAdapter?.notifyDataSetChanged()
            selectedItem = -1
            Snackbar.make(listsAddButton, R.string.task_delete, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }
   /* override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (-1 != selectedItem) {
            when {
                R.id.edit_item == item?.itemId -> {

                    val updateFragment = NewTaskDialogFragment.newInstance(
                        R.string.update_task_dialog_title,
                        todoListItems[selectedItem].taskDetails
                    )
                    updateFragment.show(this.fragmentManager, "updatetask")

                }
                R.id.delete_item == item?.itemId -> {
                    val selectedTask = todoListItems[selectedItem]
                    DeleteTaskAsyncTask(database, selectedTask).execute()
        //                dbHelper.deleteTask(selectedTask)

                    todoListItems.removeAt(selectedItem)
                    listAdapter?.notifyDataSetChanged()
                    selectedItem = -1
                    Snackbar.make(listsAddButton, R.string.task_delete, Snackbar.LENGTH_LONG).setAction("Action", null).show()

                }
                R.id.mark_as_done_item == item?.itemId -> {
                    todoListItems[selectedItem].completed = true

                    UpdateTaskAsyncTask(database, todoListItems[selectedItem]).execute()
                    listAdapter?.notifyDataSetChanged()
                    selectedItem = -1

                    Snackbar.make(listsAddButton, "Task has been marked as completed", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                }

            }
        }

        return super.onOptionsItemSelected(item)
    }
*/

    private fun showNewTaskUI() {

        val newFragment =
            NewTaskDialogFragment.newInstance(R.string.add_new_task_dialog_title, null)
        newFragment.show(fragmentManager, "newtask")
    }

    private fun showUpdateTaskUI(selected: Int) {

        selectedItem = selected
        invalidateOptionsMenu()
    }

    private fun populateListView() {
        todoListItems = RetrieveTasksAsyncTask(database).execute().get() as ArrayList<Task>
        listAdapter = TaskListAdapter(this, todoListItems)
        listView?.adapter = listAdapter
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, taskDetails:String) {

        if("newtask" == dialog.tag) {

            var addNewTask = Task(taskDetails, "")

            addNewTask.taskId = AddTaskAsyncTask(database, addNewTask).execute().get()
            todoListItems.add(addNewTask)
            listAdapter?.notifyDataSetChanged()

            Snackbar.make(listsAddButton, R.string.task_success, Snackbar.LENGTH_LONG).setAction("Action", null).show()

        } else if ("updatetask" == dialog.tag) {
            todoListItems[selectedItem].taskDetails = taskDetails
            //dbHelper.updateTask(todoListItems[selectedItem])
            UpdateTaskAsyncTask(database, todoListItems[selectedItem]).execute()

            listAdapter?.notifyDataSetChanged()

            selectedItem = -1

            Snackbar.make(listsAddButton, R.string.task_update, Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        // do nothing
    }

    override fun onDestroy() {
//        dbHelper.close()
        super.onDestroy()
    }


    private class RetrieveTasksAsyncTask(private val database: AppDatabase?) : AsyncTask<Void, Void, List<Task>>() {

        override fun doInBackground(vararg params: Void): List<Task>? {
            return database?.listsDao()?.retrieveTaskList()
        }
    }

    private class AddTaskAsyncTask(private val database: AppDatabase?, private val newTask: Task) : AsyncTask<Void, Void, Long>() {

        override fun doInBackground(vararg params: Void): Long? {
            return database?.listsDao()?.addNewTask(newTask)
        }
    }

    private class UpdateTaskAsyncTask(private val database: AppDatabase?, private val selectedTask: Task) : AsyncTask<Void, Void, Unit>() {

        override fun doInBackground(vararg params: Void): Unit? {
            return database?.listsDao()?.updateTask(selectedTask)
        }
    }

    private class DeleteTaskAsyncTask(private val database: AppDatabase?, private val selectedTask: Task) : AsyncTask<Void, Void, Unit>() {

        override fun doInBackground(vararg params: Void): Unit? {
            return database?.listsDao()?.deleteTask(selectedTask)
        }
    }

}
