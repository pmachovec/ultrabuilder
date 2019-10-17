package com.pmachovec.ultrabuilder

import com.pmachovec.ultrabuilder.constants.NeededTaskNames
import com.pmachovec.ultrabuilder.constants.Texts
import com.pmachovec.ultrabuilder.taskmodels.ForceBuild
import com.pmachovec.ultrabuilder.taskmodels.ForceTest
import com.pmachovec.ultrabuilder.taskmodels.TaskModel
import com.pmachovec.ultrabuilder.taskmodels.UltraBuild
import com.pmachovec.ultrabuilder.taskmodels.UltraTest

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class UltraBuilder : Plugin<Project> {
    companion object {
        const val NAME = "ultrabuilder"
    }

    override fun apply(project: Project) {
        val forceBuildTask = project.ultraBuilderTask(ForceBuild())
        val forceTestTask = project.ultraBuilderTask(ForceTest())
        val ultraBuildTask = project.ultraBuilderTask(UltraBuild())
        val ultraTestTask = project.ultraBuilderTask(UltraTest())
        val buildTask = project.tasks.findByName(NeededTaskNames.BUILD)
        val cleanTask = project.tasks.findByName(NeededTaskNames.CLEAN)
        val cleanTestTask = project.tasks.findByName(NeededTaskNames.CLEANTEST)
        val testTask = project.tasks.findByName(NeededTaskNames.TEST)

        if (buildTask !== null) {
            buildTask.outputs.upToDateWhen { false }
            forceBuildTask.dependsOn.add(buildTask)
            ultraBuildTask.dependsOn.add(buildTask)
        } else {
            forceBuildTask.doLast {
                println(Texts.TASK_NOT_AVAILABLE.format(NeededTaskNames.BUILD))
            }

            ultraBuildTask.doLast {
                println(Texts.TASK_NOT_AVAILABLE.format(NeededTaskNames.BUILD))
            }
        }

        if (cleanTask !== null) {
            ultraBuildTask.dependsOn.add(cleanTask)

            if (buildTask !== null) {
                buildTask.mustRunAfter(cleanTask)
            }
        } else {
            ultraBuildTask.doLast {
                println(Texts.TASK_NOT_AVAILABLE.format(NeededTaskNames.CLEAN))
            }
        }

        if (testTask !== null) {
            testTask.outputs.upToDateWhen { false }
            forceTestTask.dependsOn.add(testTask)
            ultraTestTask.dependsOn.add(testTask)
        } else {
            forceTestTask.doLast {
                println(Texts.TASK_NOT_AVAILABLE.format(NeededTaskNames.TEST))
            }

            ultraTestTask.doLast {
                println(Texts.TASK_NOT_AVAILABLE.format(NeededTaskNames.TEST))
            }
        }

        if (cleanTestTask !== null) {
            ultraTestTask.dependsOn.add(cleanTestTask)

            if (testTask !== null) {
                testTask.mustRunAfter(cleanTestTask)
            }
        } else {
            ultraTestTask.doLast {
                println(Texts.TASK_NOT_AVAILABLE.format(NeededTaskNames.CLEANTEST))
            }
        }
    }

    private fun Project.ultraBuilderTask(taskModel: TaskModel): Task {
        val projectTask = this.task(taskModel.name)
        projectTask.group = taskModel.group
        projectTask.description = taskModel.description
        return projectTask
    }
}
