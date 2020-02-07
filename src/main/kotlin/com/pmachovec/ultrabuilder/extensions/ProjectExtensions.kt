package com.pmachovec.ultrabuilder.extensions

import com.pmachovec.ultrabuilder.taskmodels.TaskModel

import org.gradle.api.Project
import org.gradle.api.Task

fun Project.ultraBuilderTask(taskModel: TaskModel, vararg neededTaskNames: String): Task {
    val projectTask = this.task(taskModel.name)
    projectTask.group = taskModel.group
    projectTask.description = taskModel.description
    projectTask.ultraBuildTaskDependsOn(this, *neededTaskNames)
    return projectTask
}
