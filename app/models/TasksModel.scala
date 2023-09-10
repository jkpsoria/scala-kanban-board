package models

import java.util.UUID
import scala.collection.mutable.ListBuffer


case class TodoTask(id: UUID, taskTitle: String, taskDesc: String)
case class InProgressTask(id: UUID, taskTitle: String, taskDesc: String)
case class DoneTask(id: UUID, taskTitle: String, taskDesc: String)



object TodoTask {
    val toDo: ListBuffer[TodoTask] = ListBuffer()

    def createTask(newTask: TodoTask) = toDo += newTask

    def deleteTask(taskID: UUID) = {
        val task = toDo.find(_.id == taskID)
        task.foreach((x) => toDo -= x)

        println(toDo)
    }

    def moveToInProgress(taskID: UUID) = {

        val taskToMove = toDo.find(_.id == taskID)

        taskToMove match {
            case Some(task) =>
                toDo -= task
                val inProgressTask = InProgressTask(
                    id = task.id,
                    taskTitle = task.taskTitle,
                    taskDesc = task.taskDesc
                )
                InProgressTask.createInProgress(inProgressTask)
                Some(inProgressTask)
                
            case None =>
                None
        }

        println(toDo)
    }
}

object InProgressTask {
    val inProgress: ListBuffer[InProgressTask] = ListBuffer()

    def createInProgress(newTask: InProgressTask) = inProgress += newTask
    
    def moveToDone(taskID: UUID) = {

        val taskToMove = inProgress.find(_.id == taskID)

        taskToMove match {
            case Some(task) =>
                inProgress -= task
                val doneTasks = DoneTask(
                    id = task.id,
                    taskTitle = task.taskTitle,
                    taskDesc = task.taskDesc
                )
                DoneTask.createDoneTask(doneTasks)
                Some(doneTasks)
                
            case None =>
                None
        }
        println(inProgress)
    }
    
}

object DoneTask {
    val doneTask: ListBuffer[DoneTask] = ListBuffer()

    def createDoneTask(newTask: DoneTask) = doneTask += newTask

    def deleteDoneTask(taskID: UUID) = {
        val task = doneTask.find(_.id == taskID)
        task.foreach((x) => doneTask -= x)
        println(doneTask)
    }
        


    // def deleteTodo(id: UUID) = {
    //     findById(id) match {
    //         case Some(task) =>
    //         toDo -= task 
    //         case None => 
    //     }
    // }

}




