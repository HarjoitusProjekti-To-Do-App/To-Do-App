package com.example.todo
//Tuodaan mukaan tarvittavat luokat ja rajapinnat
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast


class MainActivity : AppCompatActivity() { //Määritellään pääaktiviteetin luokka, joka perii AppCompatActivity- luokan

    // Seuraavat rivit määrittelevät näkymäkomponentit. joita käytetään MainActivityn layoutissa
    private lateinit var taskEditText: EditText
    private lateinit var addButton: Button
    private lateinit var taskListView: ListView
    private lateinit var taskAdapter: ArrayAdapter<String>

    //seuraavaksi määritellään muuttujat taskList ja taskStatus, joissa on tehtävien tekstit ja niiden tilat
    private val taskList = mutableListOf<String>()
    private val taskStatus = mutableListOf<Boolean>()


    // seuraava metodi suoritetaan, kun aktiviteetti luodaan. Se asettaa näytettävän layoutin acitivity_main.xml avulla
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    // seuraavat rivit asettavat taskEditText, addButton ja taskListView muuttujat xml näkymien kanssa, jotka on määritelty activity_main.xml tiedostossa.
        taskEditText = findViewById(R.id.taskEditText)
        addButton = findViewById(R.id.addButton)
        taskListView = findViewById(R.id.taskListView)


        // Tarkistetaan, ettei elementtejä jäänyt löytämättä
        if (taskEditText == null || addButton == null || taskListView == null) {
            Toast.makeText(this, "Virhe: Kohde-elementtejä ei voitu löytää", Toast.LENGTH_SHORT).show()
            return
        }

        //seuraavaksi luodaan uusi ArrayAdapter, joka liitetään taskListViewiin. ArrayAdapter käyttää taskList muuttujaa tiedon lähteenä.

        taskAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, taskList)
        taskListView.adapter = taskAdapter


        // seuraava koodi lisää onClickListenerin napille addButton. Kun nappia painetaan, se lisää uuden tehtävän

        addButton.setOnClickListener {
            val newTask = taskEditText.text.toString().trim()
            if (newTask.isNotEmpty()) {
                addTask(newTask)
                taskEditText.text.clear()
            }
        }
        // Tämä laittaa OnItemClickListenerin taskListViewiin. kun käyttäjä valitsee tehtävän, sen tila muuttuu (valmis/keskeneräinen)
        taskListView.setOnItemClickListener { _, _, position, _ ->
            val isChecked = taskStatus[position]
            taskStatus[position] = !isChecked
        }
        //Tämä laittaa OnitemLongClickListenerin taskListViewiin. kun käyttäjä pitää tehtävää pitkään painettuna, se poistetaan taskLististä.
        taskListView.setOnItemLongClickListener { _, _, position, _ ->
            removeTask(position)
            true
        }
    }
        // Seuraava metodi lisää uuden tehtävän taskList muuttujaan, merkitsee sen keskeneräiseksi ja päivittää näkymän.
    private fun addTask(task: String) {
        taskList.add(task)
        taskStatus.add(false)
        taskAdapter.notifyDataSetChanged()
    }
        //tämä metodi vauhtaa tehtävän statusta(valmis/keskeneräinen)
    private fun toggleTaskCompletion(position: Int) {
        val isChecked = taskListView.isItemChecked(position)
        taskListView.setItemChecked(position, !isChecked)
    }
    //Tämä metodi poistaa tehtävän "taskList" muuttujasta ja päivittää näkymän.
    private fun removeTask(position: Int) {
        taskList.removeAt(position)
        taskStatus.removeAt(position)
        taskAdapter.notifyDataSetChanged()
    }
}
