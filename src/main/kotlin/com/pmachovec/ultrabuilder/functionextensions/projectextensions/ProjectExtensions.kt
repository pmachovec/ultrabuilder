package com.pmachovec.ultrabuilder.functionextensions.projectextensions

import com.pmachovec.ultrabuilder.taskmodels.TaskModel

import org.gradle.api.Project
import org.gradle.api.Task

fun Project.ultraBuilderTask(taskModel: TaskModel): Task {
    val projectTask = this.task(taskModel.name)
    projectTask.group = taskModel.group
    projectTask.description = taskModel.description
    return projectTask
}
