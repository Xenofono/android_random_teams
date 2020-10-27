package tech.kristoffer.teambuilder2000

class Team(val players: MutableList<String> = ArrayList(), var teamName: String = createTeamName()) {



    fun addPlayer(player: String) {
        this.players.add(player)
    }

    companion object {
        private val teamPrefix = listOf("Flying", "Mighty", "Powerful", "Amazing", "Beautiful", "Agile", "Intelligent", "Courageous")
        private val teamSuffix = listOf("Cobras", "Gorillas", "Gingers", "Tigers", "Lemurs", "Squirrels", "Kangaroos", "Panthers")
        private fun createTeamName(): String = "${teamPrefix.random()} ${teamSuffix.random()}"
    }
}