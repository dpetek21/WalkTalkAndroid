package hr.foi.rampu.walktalk.helpers

import hr.foi.rampu.walktalk.entities.Friend

object MockFriends {
    fun getFriends() : List<Friend> = listOf(Friend("marin"), Friend("bojan"), Friend("igor"))
    fun getPendingFriends() : MutableList<Friend> = mutableListOf(Friend("Domagoj"), Friend("Gabriel"), Friend("Lea"))
}