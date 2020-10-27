package tech.kristoffer.teambuilder2000

class Team(val players: MutableList<String> = ArrayList()) {

    fun addPlayer(player: String) {
        this.players.add(player)
    }
}