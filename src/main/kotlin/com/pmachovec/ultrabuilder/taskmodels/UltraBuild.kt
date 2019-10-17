package com.pmachovec.ultrabuilder.taskmodels

import com.pmachovec.ultrabuilder.constants.GroupNames
import com.pmachovec.ultrabuilder.constants.NeededTaskNames
import com.pmachovec.ultrabuilder.constants.UltraBuilderTaskNames

class UltraBuild : TaskModel {
    override val name = UltraBuilderTaskNames.ULTRABUILD
    override val group = GroupNames.BUILD
    override val description = "Runs " + NeededTaskNames.CLEAN + " and " + NeededTaskNames.BUILD + " tasks."
}
