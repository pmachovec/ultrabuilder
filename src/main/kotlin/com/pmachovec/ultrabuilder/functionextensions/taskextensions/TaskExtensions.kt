package com.pmachovec.ultrabuilder.functionextensions.taskextensions

import com.pmachovec.ultrabuilder.constants.Texts

import org.gradle.api.Project
import org.gradle.api.Task

fun Task.ultraBuildTaskDependsOn(project: Project, vararg neededTaskNames: String) {
    var previousTask: Task? = null

    neededTaskNames.forEach { neededTaskName -> // Must be explicit and different from 'it' because used further in deeper closure
        val neededTask = project.tasks.findByName(neededTaskName)

        if (neededTask != null) {
            this.dependsOn(neededTask)

            if (previousTask != null) {
                neededTask.mustRunAfter(previousTask)
            }

            previousTask = neededTask
        } else {
            this.doLast {
                println(Texts.TASK_NOT_AVAILABLE.format(neededTaskName))
            }
        }
    }
}
