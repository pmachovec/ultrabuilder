package com.pmachovec.ultrabuilder

import com.pmachovec.ultrabuilder.constants.NeededTaskNames
import com.pmachovec.ultrabuilder.functionextensions.projectextensions.ultraBuilderTask
import com.pmachovec.ultrabuilder.functionextensions.taskextensions.ultraBuildTaskDependsOn
import com.pmachovec.ultrabuilder.taskmodels.ForceBuild
import com.pmachovec.ultrabuilder.taskmodels.ForceTest
import com.pmachovec.ultrabuilder.taskmodels.UltraBuild
import com.pmachovec.ultrabuilder.taskmodels.UltraTest

import org.gradle.api.Plugin
import org.gradle.api.Project

class UltraBuilder : Plugin<Project> {
    companion object {
        const val NAME = "ultrabuilder"
    }

    override fun apply(project: Project) {
        val forceBuildTask = project.ultraBuilderTask(ForceBuild())
        val forceTestTask = project.ultraBuilderTask(ForceTest())
        val ultraBuildTask = project.ultraBuilderTask(UltraBuild())
        val ultraTestTask = project.ultraBuilderTask(UltraTest())

        forceBuildTask.ultraBuildTaskDependsOn(project, NeededTaskNames.BUILD)
        forceTestTask.ultraBuildTaskDependsOn(project, NeededTaskNames.TEST)
        ultraBuildTask.ultraBuildTaskDependsOn(project, NeededTaskNames.CLEAN, NeededTaskNames.BUILD)
        ultraTestTask.ultraBuildTaskDependsOn(project, NeededTaskNames.CLEANTEST, NeededTaskNames.TEST)
    }
}
