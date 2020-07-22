package tech.kristoffer.teambuilder2000

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = layoutInflater.inflate(R.layout.activity_main, null) as ConstraintLayout
        setContentView(layout)


        val text = findViewById<EditText>(R.id.text)
        val teams = findViewById<EditText>(R.id.teams)
        val btn = findViewById<Button>(R.id.calcBtn)
        btn.setOnClickListener{ view ->
            val numTeams: Int = teams.text.toString().toInt()
            val names: List<String> = text.text.split("\\s+".toRegex())
            if(numTeams <= names.size && numTeams >= 1){
                val menuIntent = Intent(this, TeamActivity::class.java)
                menuIntent.putStringArrayListExtra("list", ArrayList(names))
                menuIntent.putExtra("teams", numTeams)
                startActivity(menuIntent)

            }
            else{
                Toast.makeText(this,
                    "Antalet lag måste vara fler än spelare, antalet lag måste vara minst 1",
                    Toast.LENGTH_LONG).show()
            }


        }

    }
}