package tech.kristoffer.teambuilder2000

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_team_list.*
import java.util.*
import kotlin.collections.ArrayList


class TeamActivity : AppCompatActivity() {

    lateinit var playerNames: ArrayList<String>
    var teamNumber: Int = 0
    lateinit var finalTeams: List<Team>
    var nextTeam = 0

    private val updateNextTeam: () -> Unit = { if(nextTeam == finalTeams.size - 1) nextTeam = 0 else nextTeam++ }



    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null)
            supportActionBar?.hide()
        val layout = layoutInflater.inflate(R.layout.activity_team_list, null) as ConstraintLayout
        setContentView(layout)

        if(savedInstanceState != null){
            finalTeams = savedInstanceState.getParcelableArrayList<Team>("teams") as ArrayList<Team>
            nextTeam = savedInstanceState.getInt("nextTeam")
        }
        else{
            val extras = intent.extras
            playerNames = extras?.getStringArrayList("list") as ArrayList<String>
            teamNumber = extras.getInt("teams")
            val teams = createTeams()
            finalTeams = teams
        }
        lstview.adapter = TeamAdapter(this, R.layout.activity_team, finalTeams, {nextTeam}, updateNextTeam)


    }

    @ExperimentalStdlibApi
    private fun createTeams(): List<Team> {

        val teams = (0 until teamNumber)
            .map { Team() }

        playerNames.filter { it.isNotBlank() }
            .map { it.trim().capitalize(Locale.ROOT) }
            .shuffled()
            .chunked(teams.size)
            .forEach{
                it.zip(teams) {player, team -> team.addPlayer(player)}
            }
        return teams
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("teams", ArrayList<Team>(finalTeams))
        outState.putInt("nextTeam", nextTeam)
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