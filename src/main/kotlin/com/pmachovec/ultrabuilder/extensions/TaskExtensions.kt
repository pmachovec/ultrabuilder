package com.pmachovec.ultrabuilder.extensions

import com.pmachovec.ultrabuilder.constants.Texts

import org.gradle.api.Project
import org.gradle.api.Task

fun Task.ultraBuildTaskDependsOn(project: Project, vararg neededTaskNames: String) {
    project.afterEvaluate {
        this.applyNeededTasks(project, neededTaskNames)
    }
}

private fun Task.applyNeededTasks(project: Project, neededTaskNames: Array<out String>) {
    var previousTask: Task? = null

    neededTaskNames.forEach { neededTaskName -> // Must be explicit and different from 'it' because used further in deeper closure
        val neededTask = this.applyNeededTask(previousTask, project, neededTaskName)

        if (neededTask != null) {
            previousTask = neededTask
        }
    }
}

private fun Task.applyNeededTask(previousTask: Task?, project: Project, neededTaskName: String): Task? {
    val neededTask = project.tasks.findByName(neededTaskName)

    if (neededTask != null) {
        this.dependsOn(neededTask)

        if (previousTask != null) {
            neededTask.mustRunAfter(previousTask)
        }
    } else {
        this.doLast {
            println(Texts.TASK_NOT_AVAILABLE.format(neededTaskName))
        }
    }

    return neededTask
}
