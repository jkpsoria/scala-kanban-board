package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n.I18nSupport
import models._
import java.util.UUID
import play.api.data.Forms._
import play.api.data.Form
import views.html.defaultpages.badRequest
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with I18nSupport{
   
  val todoList = TodoTask.toDo
  val inProgressList = InProgressTask.inProgress
  val doneTaskList = DoneTask.doneTask
   
  def todoForm = Form {
    mapping(
      "id" -> default(uuid, UUID.randomUUID()),
      "Title" -> nonEmptyText(maxLength = 250),
      "Description" -> text(maxLength = 1000),
    )(TodoTask.apply)(TodoTask.unapply)
  }

  def inProgressForm = Form {
    mapping(
      "id" -> default(uuid, UUID.randomUUID()),
      "title" -> nonEmptyText,
      "description" -> nonEmptyText
    )(InProgressTask.apply)(InProgressTask.unapply)
  }

  def doneTaskForm = Form {
    mapping(
      "id" -> default(uuid, UUID.randomUUID()),
      "title" -> nonEmptyText,
      "description" -> nonEmptyText
    )(DoneTask.apply)(DoneTask.unapply)
  }

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index(todoForm, todoList.toList, inProgressList.toList, doneTaskList.toList))
  }

  def createTodoTask() = Action { implicit request =>
    todoForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest("Something went wrong.")
      },
      todo => {
        TodoTask.createTask(todo)
        Redirect(routes.HomeController.index())
      }
    )
  }

  def moveToInProgressTask(id: UUID) = Action {
    TodoTask.moveToInProgress(id)
    Redirect(routes.HomeController.index())
  }

  def inProgressToDone(id: UUID) = Action {
    InProgressTask.moveToDone(id)
    Redirect(routes.HomeController.index())
  }

  def deleteDoneTask(id: UUID) = Action {
    DoneTask.deleteDoneTask(id)
    Redirect(routes.HomeController.index())
  }

}