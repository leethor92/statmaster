package views.settings

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import views.BasePresenter
import views.BaseView
import views.VIEW

class SettingsPresenter(view: BaseView): BasePresenter(view) {

    fun doUpdateSettings(email: String, password: String) {
        view?.showProgress()
        FirebaseAuth.getInstance().currentUser?.updateEmail(email)?.addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                FirebaseAuth.getInstance().currentUser?.updatePassword(password)?.addOnCompleteListener(view!!) { task2 ->
                    if (task2.isSuccessful) {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.TEAMLIST, 0)
                    }
                    else {
                        view?.hideProgress()
                        view?.toast("Update Password Failed: ${task2.exception?.message}")
                    }
                }
            }
            else {
                view?.hideProgress()
                view?.toast("Update Email Failed: ${task.exception?.message}")
            }
        }
    }
}