package tech.kristoffer.teambuilder2000

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_team.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round


class TeamActivity : AppCompatActivity() {

    lateinit var playerNames: ArrayList<String>
    var teamNumber: Int = 0

    @ExperimentalStdlibApi
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

    @ExperimentalStdlibApi
    private fun createTeams() {
        val shuffledPlayers = playerNames.filter { it.isNotBlank() }
            .map {
                it.trim()
                it.capitalize(Locale.ROOT)
            }
            .shuffled()

        //Skapa antalet lag och ge varje en en spelare
        val allTeams: MutableList<Team> = ArrayList()
        for (i in 0 until teamNumber) {
            val newTeam = Team()
            newTeam.addPlayer(shuffledPlayers[i])
            println(shuffledPlayers[i])
            allTeams.add(newTeam)
        }

        //Börja om från första laget och gå igenom alla kvarstående namn och lägg dem i ett lag, repeat
        var teamIndex = 0
        for(i in teamNumber until shuffledPlayers.size){
            val name = shuffledPlayers[i]
            allTeams[teamIndex].addPlayer(name)
            if(teamIndex == teamNumber - 1){
                teamIndex = 0
            }
            else{
                teamIndex++
            }
        }



        val arrayAdapter = TeamAdapter(this, R.layout.activity_list_team, allTeams)
        lstview.adapter = arrayAdapter
    }


}