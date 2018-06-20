package com.nexte.nexte.FeedScene

import com.nexte.nexte.Entities.FeedMocker
import com.nexte.nexte.R
import com.nexte.nexte.UserSingleton

/**
 * Object class for use static mode in other classes.
 * This Object is responsible to control data in memory of Feed.
 *
 * @property feedListMutable Private variable to control list of memory
 * @property feedList Property to access feed list on memory
 */
object FeedManager {

    var feedListMutable: MutableList<FeedModel.FeedActivity> = mutableListOf()

    var feedList: MutableList<FeedModel.FeedActivity> = mutableListOf()
        get() {
            if (feedListMutable.isEmpty()) {
                feedListMutable = FeedMocker.createFeedList()
            }
            return feedListMutable
        }
        private set

    /**
     * Method for get specific activity of feed
     *
     * @param identifier Use for find activity feed with this identifier
     * @return Feed activity with specific identifier
     */
    fun getFeedActivity(identifier: String): FeedModel.FeedActivity? {
        return feedList.find { it.identifier == identifier}
    }

    /**
     * Method to update one specific acitivity of feed
     *
     * @param identifier Use for find activity feed that will be updated
     * @param activity New data for store in specific feed activity
     * @return Activity that is updated
     */
    fun updateFeedActivity(identifier: String?, activity: FeedModel.FeedActivity): FeedModel.FeedActivity {
        val activityOfFeed = feedList.find { it.identifier == identifier }

        if (activityOfFeed == null) {
            this.feedListMutable.add(activity)
        } else {
            activity.identifier = activityOfFeed.identifier
            activityOfFeed.challenge = activity.challenge
            activityOfFeed.feedDate = activity.feedDate
            activityOfFeed.likes = activity.likes
        }

        return activity
    }



    /**
     * Function that receives the unformattedActivity from Interactor and sends it
     * unformatted Activity to presenter as a completion, after calling the function to add
     * or remove users from likes list
     *
     * @param currentUser user that will be added to likesList
     * @param matchingUser user that corresponds to previous randomUser
     * @param indexToChange index of the found activity
     * @return activity changed, with or without the member on likes List
     */
     fun addAndRemoveUser(activity: FeedModel.FeedActivity?): FeedModel.FeedActivity? {
        val currentUser = FeedModel.FeedPlayer(UserSingleton.loggedUser.name, R.mipmap.ic_launcher, 3)
        val matchingUser = activity?.likes?.find { it.name.equals(UserSingleton.loggedUser.name) }
        val indexToChange = activity?.likes?.indexOf(matchingUser)

        if(matchingUser == null) {
            activity?.likes?.add(currentUser)
        }
        else if(indexToChange != null) {
            activity.likes.removeAt(indexToChange)
        }

        return activity
    }

}
