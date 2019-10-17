package com.pmachovec.ultrabuilder.taskmodels

import com.pmachovec.ultrabuilder.constants.GroupNames
import com.pmachovec.ultrabuilder.constants.NeededTaskNames
import com.pmachovec.ultrabuilder.constants.UltraBuilderTaskNames

class ForceTest : TaskModel {
    override val name = UltraBuilderTaskNames.FORCETEST
    override val group = GroupNames.VERIFICATION
    override val description = "Runs " + NeededTaskNames.TEST + " task regardless file changes, " +
        "even when no file has been changed since last build."
}
