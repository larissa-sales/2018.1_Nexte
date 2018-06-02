package com.nexte.nexte.EditProfileScene


/**
 * Interface responsible to interpret received user information and send it to [EditProfileView]
 */
interface ShowProfileToEditPresentationLogic {

    /**
     * Method responsible to present profile data
     *
     * @param response contains unformatted data received from [EditProfileModel]
     */
    fun presentProfileToEdit(response: EditProfileModel.RecoverUserRequest.Response)
}

/**
 * Interface responsible interpret received editProfileError (if exists one) and shows it in [EditProfileView]
 */
interface SendEditedProfileDataPresentationLogic {

    /**
     * Method responsible to send edited data to view
     *
     * @param response contains unformatted data received from [EditProfileModel]
     */
    fun sendEditedProfileStatus(response: EditProfileModel.EditProfileRequest.Response)
}

/**
 * Class responsible to interpretation of [EditProfileModel.RecoverUserRequest]
 * and [EditProfileModel.EditProfileRequest] Responses and generate viewModels
 *
 * @property viewToShowUserInformation Shows profile view with method to show user
 * @property viewToShowEditProfileError Reference for show profile view with method
 * to show edit profile error
 */
class EditProfilePresenter: ShowProfileToEditPresentationLogic, SendEditedProfileDataPresentationLogic{

    var viewToShowUserInformation: ShowProfileToEditDisplayLogic? = null
    var viewToShowEditProfileError: EditProfileDisplayLogic? = null

    /**
     * Formats user information contained in [EditProfileModel.RecoverUserRequest.Response]
     * and sends it to [EditProfileView]
     *
     * @param response contains unformatted data received from [EditProfileModel]
     */
    override fun presentProfileToEdit(response: EditProfileModel.RecoverUserRequest.Response) {

        val username: String = response.user.name
        val ranking: String = String.format("#%d", response.user.rankingPosition)
        val club: String = "UnB"
        val email: String =  response.user.email

        val formattedPlayer: EditProfileModel.RecoverUserRequest.FormattedPlayer =
                EditProfileModel.RecoverUserRequest.FormattedPlayer(username,
                    ranking,
                    club,
                    email)

        val viewModel: EditProfileModel.RecoverUserRequest.ViewModel =
                EditProfileModel.RecoverUserRequest.ViewModel(formattedPlayer)

        viewToShowUserInformation?.displayProfileToEdit(viewModel)
    }

    /**
     * Format error contained in [EditProfileModel.RecoverUserRequest.Response] (if exists)
     * and sends it to [EditProfileView]
     *
     * @param response contains unformatted data received from [EditProfileModel]
     */
    override fun sendEditedProfileStatus(response: EditProfileModel.EditProfileRequest.Response) {

        val errorID: Int? = response.errorID
        val messageError: String?

        messageError = when (errorID) {
            1 -> "Email inválido"
            2 -> "Sua senha deverá conter mais de 6 caracteres"
            else -> {
                null
            }
        }

        val viewModel: EditProfileModel.EditProfileRequest.ViewModel = EditProfileModel.EditProfileRequest.ViewModel(
            messageError)

        viewToShowEditProfileError?.displayEditedProfile(viewModel)
    }
}