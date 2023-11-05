package net.ddraig.simplyhome.procedures;

import net.minecraft.world.entity.Entity;

import net.ddraig.simplyhome.network.SimplyHomeModVariables;

public class HomeCrystalMakeItemGlowProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (0 == (entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimplyHomeModVariables.PlayerVariables())).WCOOL) {
			return true;
		}
		return false;
	}
}
