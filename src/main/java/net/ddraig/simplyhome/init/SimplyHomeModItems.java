
package net.ddraig.simplyhome.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

import net.ddraig.simplyhome.item.HomeCrystalItem;
import net.ddraig.simplyhome.SimplyHomeMod;

public class SimplyHomeModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, SimplyHomeMod.MODID);
	public static final RegistryObject<Item> HOME_CRYSTAL = REGISTRY.register("home_crystal", () -> new HomeCrystalItem());
}
