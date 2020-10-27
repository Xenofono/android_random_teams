package tech.kristoffer.teambuilder2000

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_team.*
import java.util.*
import kotlin.collections.ArrayList


class TeamActivity<T> : AppCompatActivity() {

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

        val teams = (0 until teamNumber)
            .map { Team() }

        playerNames.filter { it.isNotBlank() }
            .map { it.trim().capitalize(Locale.ROOT) }
            .shuffled()
            .chunked(teams.size)
            .forEach{
                it.zip(teams) {player, team -> team.addPlayer(player)}
            }

        val arrayAdapter = TeamAdapter(this, R.layout.activity_list_team, teams)
        lstview.adapter = arrayAdapter
    }


//    tailrec fun addToTeam(  names: List<String>, teamIdx: Int = 0, nameIdx: Int = 0){
//        if (nameIdx == names.size) return
//        teams[teamIdx].addPlayer(names[nameIdx])
//        if (teamIdx == teams.size - 1) addToTeam(names,0, nameIdx+1 )
//        else addToTeam(names,teamIdx+1, nameIdx+1 )
//    }
//fun List<String>.circleList(input: List<Team>) {
//    var inputIndex = 0
//    for (element in this) {
//        input[inputIndex].addPlayer(element)
//        if (inputIndex == input.size - 1) inputIndex = 0 else inputIndex++
//    }
//}
}