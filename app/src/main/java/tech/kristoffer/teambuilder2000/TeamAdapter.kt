package tech.kristoffer.teambuilder2000

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class TeamView(v: View) {
    val teamName: TextView = v.findViewById(R.id.teamName)
    val teamMembers: TextView = v.findViewById(R.id.teamMembers)
}


class TeamAdapter(
    context: Context,
    private val resource: Int,
    private val teams: List<Team>
) : ArrayAdapter<List<List<String>>>(context, resource) {

    private val inflater = LayoutInflater.from(context)

    private val teamPrefix = listOf("Flying", "Mighty", "Powerful", "Amazing", "Beautiful", "Agile", "Intelligent", "Courageous")
    private val teamSuffix = listOf("Cobras", "Gorillas", "Gingers", "Tigers", "Lemurs", "Squirrels", "Kangaroos", "Panthers")
    private fun createTeamName(): String = "${teamPrefix.random()} ${teamSuffix.random()}"

    override fun getCount(): Int {
        return teams.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: TeamView
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = TeamView(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as TeamView
        }
        val currentTeam = teams[position]
        viewHolder.teamName.text = createTeamName()
        viewHolder.teamMembers.text = currentTeam.players.joinToString("\n")
        return view
    }


}