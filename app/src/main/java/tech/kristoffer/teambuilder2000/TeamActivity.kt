package tech.kristoffer.teambuilder2000

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_team.*
import kotlin.math.ceil




class TeamActivity : AppCompatActivity() {

    lateinit var playerNames: ArrayList<String>
    var teamNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null)
            supportActionBar?.hide()
        val layout = layoutInflater.inflate(R.layout.activity_team, null) as ConstraintLayout
        setContentView(layout)
        val extras = intent.extras

        playerNames = extras?.getStringArrayList("list") as ArrayList<String>
        teamNumber = extras.getInt("teams")
        createTeams()
    }

    private fun createTeams() {
        val shuffledPlayers = playerNames.filter { it.isNotBlank() }.shuffled()
        //om antalet lag är 1 under antalet spelare så tar vi en spelare i taget
        //tills vi har teamNumber-1, sen blir resterande sista laget
        //annars används inbyggda windowed
        val teams = if (teamNumber == shuffledPlayers.size-1){
            val mutList: MutableList<List<String>> = ArrayList()
            for (i in 0 until teamNumber-1){
                mutList.add(listOf(shuffledPlayers[i]))

            }
            mutList.add(shuffledPlayers.subList(teamNumber-1, shuffledPlayers.size))
            mutList
        } else{
            val limit = ceil((shuffledPlayers.size / teamNumber.toDouble())).toInt()
            shuffledPlayers.windowed(limit, limit, true)
        }

        val arrayAdapter = TeamAdapter(this, R.layout.list_team, teams)
        lstview.adapter = arrayAdapter
    }


}