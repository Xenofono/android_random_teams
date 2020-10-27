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

        val allTeams = (0 until teamNumber)
            .map { Team() }

        tailrec fun addToTeam(  names: List<String>, teamIdx: Int = 0, nameIdx: Int = 0){
            if (nameIdx == names.size) return
            allTeams[teamIdx].addPlayer(names[nameIdx])
            if (teamIdx == allTeams.size - 1) addToTeam(names,0, nameIdx+1 )
            else addToTeam(names,teamIdx+1, nameIdx+1 )
        }

//        var idx = 0
        val names = playerNames.filter { it.isNotBlank() }
            .map {
                it.trim()
                it.capitalize(Locale.ROOT)
            }
            .shuffled()
//            .forEach {
//                allTeams[idx].addPlayer(it)
//                if(idx == allTeams.size - 1) idx = 0 else idx++
//            }
        addToTeam(names)


        val arrayAdapter = TeamAdapter(this, R.layout.activity_list_team, allTeams)
        lstview.adapter = arrayAdapter
    }


}