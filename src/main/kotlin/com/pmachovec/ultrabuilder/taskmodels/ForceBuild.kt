package com.pmachovec.ultrabuilder.taskmodels

import com.pmachovec.ultrabuilder.constants.GroupNames
import com.pmachovec.ultrabuilder.constants.NeededTaskNames
import com.pmachovec.ultrabuilder.constants.UltraBuilderTaskNames

class ForceBuild : TaskModel {
    override val name = UltraBuilderTaskNames.FORCEBUILD
    override val group = GroupNames.BUILD
    override val description = "Runs " + NeededTaskNames.BUILD + " task regardless file changes, " +
        "even when no file has been changed since last build."
}
