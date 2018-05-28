package com.nexte.nexte.ShowProfileScene

import android.os.Bundle
import com.nexte.nexte.Player
import com.nexte.nexte.UserSingleton
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import kotlin.concurrent.thread

class ShowProfileFragmentTest {

    private var view: ShowProfileFragment? = null
    private var mock: MockShowProfileBusinessLogic? = null

    @Before
    fun setUp() {
        view = ShowProfileFragment()
        mock = MockShowProfileBusinessLogic()
    }

    @Test
    fun testGetInstance(){
        //prepare
        val playerToShowName = "luis"

        thread {
            //call
            val instance = view?.getInstance(playerToShowName)

            //assert
            assertNotNull(instance)
        }.join()

    }

    @Test
    fun testOnCreate(){
        //prepare
        val bundle = Bundle()

        thread {
            //call
            view?.onCreate(bundle)

            //assert
            assertNotNull(view?.userManager)

        }.join()
    }

    @Test
    fun testOnCreateView(){

    }

    @Test
    fun testCreateShowProfileRequest(){
        //prepare
        view?.showProfileInteractor = mock
        view?.anotherPlayerName = "123"

        //call
        view?.createShowProfileRequest()

        //assert
        assertEquals(this.mock?.request?.userId, "123")
    }

    @Test
    fun testGraphManagerInstantiation(){
        val graphManager = ShowProfileFragment.GraphManager(view!!)

        assertNotNull(graphManager)
    }

    @Test
    fun testGraphManagerSetYAxisValues(){
        //prepare
        val graphManager = ShowProfileFragment.GraphManager(view!!)

        //call
        val yVals = graphManager.setYAxisValues()

        //assert
        assertEquals(yVals.size, 6)
        assertEquals(yVals[0].x, 0f)
        assertEquals(yVals[0].y, 2f)
        assertEquals(yVals[3].x, 3f)
        assertEquals(yVals[3].y, 2f)
    }

    @Test
    fun testGraphManagerSetYAxisValuesRanking(){
        //prepare
        val graphManager = ShowProfileFragment.GraphManager(view!!)

        //call
        val yVals = graphManager.setYAxisValuesRanking()

        //assert
        assertEquals(yVals.size, 6)
        assertEquals(yVals[0].x, 0f)
        assertEquals(yVals[0].y, 3f)
        assertEquals(yVals[3].x, 3f)
        assertEquals(yVals[3].y, 2f)
    }

    @Test
    fun testGraphManagerCreateGraph(){
        thread {
            view?.graphManager?.createGraph()
        }.join()

        assertNotNull(view?.graphManager)
    }

    @Test
    fun testGraphManagerCreateRankingGraph(){
        thread {
            view?.graphManager?.createGraph()
        }.join()

        assertNotNull(view?.graphManager)
    }

    @Test
    fun testOnResume(){
        //prepare
        view?.showProfileInteractor = mock
        view?.anotherPlayerName = "teste123"

        //call
        view?.onResume()

        //assert
        assertEquals(mock?.request?.userId, "teste123")
    }

    @Test
    fun testSetupShowProfileScene(){
        view?.setupShowProfileScene()
        assertNotNull(view?.showProfileInteractor)
    }

    @Test
    fun testDisplayProfileEqualsUserSingleton(){
        //prepare
        val viewModel = ShowProfileModel.ViewModel(ShowProfileModel.
                FormattedPlayer(email = "123", name = "luis", rank = "1"))
        UserSingleton.setUserInformations(Player(name = "luis", email = "123", gender = "male",
                category = "1", rankingPosition = 1, age = 20, club = "abc", password = "123",
                pictureAddress = "1"))

        thread {
            view?.displayProfile(viewModel)
        }.join()

        assertEquals(viewModel.playerInfo.name, UserSingleton.getUserInformations().name)
        assertEquals(viewModel.playerInfo.email, UserSingleton.getUserInformations().email)
        assertEquals(viewModel.playerInfo.rank, UserSingleton.getUserInformations().rankingPosition.toString())
    }

    @Test
    fun testDisplayProfileDifferentUserSingleton(){
        //prepare
        val viewModel = ShowProfileModel.ViewModel(ShowProfileModel.
                FormattedPlayer(email = "12", name = "lui", rank = "2"))
        UserSingleton.setUserInformations(Player(name = "luis", email = "123", gender = "male",
                category = "1", rankingPosition = 1, age = 20, club = "abc", password = "123",
                pictureAddress = "1"))

        thread {
            view?.displayProfile(viewModel)
        }.join()

        assertNotEquals(viewModel.playerInfo.name, UserSingleton.getUserInformations().name)
        assertNotEquals(viewModel.playerInfo.email, UserSingleton.getUserInformations().email)
        assertNotEquals(viewModel.playerInfo.rank, UserSingleton.getUserInformations().rankingPosition.toString())
    }

    @Test
    fun testConstant(){
        val lines = 110

        assertEquals(ShowProfileFragment.houndredLine, lines)
    }

    @After
    fun tearDown() {
    }
}

private class MockShowProfileBusinessLogic: ShowProfileBusinessLogic {

    var request: ShowProfileModel.Request? = null

    override fun showProfile(request: ShowProfileModel.Request) {
        this.request = request
    }
}