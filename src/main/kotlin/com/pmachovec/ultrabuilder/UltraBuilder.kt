package com.pmachovec.ultrabuilder

import com.pmachovec.ultrabuilder.constants.NeededTaskNames
import com.pmachovec.ultrabuilder.extensions.ultraBuilderTask
import com.pmachovec.ultrabuilder.taskmodels.UltraBuild
import com.pmachovec.ultrabuilder.taskmodels.UltraTest
import org.gradle.api.Plugin
import org.gradle.api.Project

class UltraBuilder : Plugin<Project> {
    companion object {
        const val NAME = "ultrabuilder"
    }

    override fun apply(project: Project) {
        project.ultraBuilderTask(UltraBuild(), NeededTaskNames.CLEAN, NeededTaskNames.BUILD)
        project.ultraBuilderTask(UltraTest(), NeededTaskNames.CLEANTEST, NeededTaskNames.TEST)
    }
}
