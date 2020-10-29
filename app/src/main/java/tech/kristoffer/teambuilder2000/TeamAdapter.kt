package tech.kristoffer.teambuilder2000

import android.content.Context
import android.graphics.Color
import android.os.Parcelable
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.parcel.Parcelize

class TeamView(v: View) {
    val teamName: EditText = v.findViewById(R.id.teamName)
    val teamMembers: TextView = v.findViewById(R.id.teamMembers)
    val teamContainer: ConstraintLayout = v.findViewById(R.id.teamContainer)
    val isCurrentText: TextView = v.findViewById(R.id.isCurrent)

}


fun buildTextWatcher(team: Team): TextWatcher {
    return object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            team.teamName = s.toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }
}

class TeamAdapter(
    context: Context,
    private val resource: Int,
    private val teams: List<Team>,
    private val teamToPlay: () -> Int,
    private val updateNextTeam: () -> Unit
) : ArrayAdapter<List<List<String>>>(context, resource) {

    private val inflater = LayoutInflater.from(context)


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
        if(position == teamToPlay()){
            viewHolder.teamContainer.setBackgroundColor(Color.DKGRAY)
            viewHolder.isCurrentText.text = "Nästa lag"
        }
        else {
            viewHolder.teamContainer.setBackgroundColor(Color.BLACK)
            viewHolder.isCurrentText.text = ""
        }

        viewHolder.teamContainer.setOnLongClickListener {
            updateNextTeam()
            notifyDataSetChanged()
            false
        }
        viewHolder.teamName.text = SpannableStringBuilder(currentTeam.teamName)
        viewHolder.teamMembers.text = currentTeam.players.joinToString("\n")
        viewHolder.teamName.addTextChangedListener(buildTextWatcher(currentTeam))
        return view
    }


}