package net.ddraig.simplyhome.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.ddraig.simplyhome.network.SimplyHomeModVariables;

public class HomeCrystalRightclickedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		String cooldownleft = "";
		String dimensionin = "";
		if (entity.isShiftKeyDown()) {
			{
				double _setval = x;
				entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.XCOR = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = y + 1;
				entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.YCOR = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = z;
				entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.YCOR = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = 12000;
				entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.WCOOL = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				String _setval = "" + (world instanceof Level _lvl ? _lvl.dimension() : Level.OVERWORLD);
				entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.DIMM = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if (400 <= (entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimplyHomeModVariables.PlayerVariables())).YCOR) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("Set your home first by Sneaking and Right Clicking a block"), false);
		} else if (400 >= (entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimplyHomeModVariables.PlayerVariables())).YCOR) {
			dimensionin = "" + (world instanceof Level _lvl ? _lvl.dimension() : Level.OVERWORLD);
			if ((dimensionin).equals((entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimplyHomeModVariables.PlayerVariables())).DIMM)) {
				if (0 == (entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimplyHomeModVariables.PlayerVariables())).WCOOL) {
					if (entity instanceof ServerPlayer _player && !_player.level().isClientSide()) {
						ResourceKey<Level> destinationType = Level.OVERWORLD;
						if (_player.level().dimension() == destinationType)
							return;
						ServerLevel nextLevel = _player.server.getLevel(destinationType);
						if (nextLevel != null) {
							_player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0));
							_player.teleportTo(nextLevel, _player.getX(), _player.getY(), _player.getZ(), _player.getYRot(), _player.getXRot());
							_player.connection.send(new ClientboundPlayerAbilitiesPacket(_player.getAbilities()));
							for (MobEffectInstance _effectinstance : _player.getActiveEffects())
								_player.connection.send(new ClientboundUpdateMobEffectPacket(_player.getId(), _effectinstance));
							_player.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
						}
					}
					if (entity instanceof Player _player)
						_player.getCooldowns().addCooldown(itemstack.getItem(), 12000);
					{
						Entity _ent = entity;
						_ent.teleportTo(((entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimplyHomeModVariables.PlayerVariables())).XCOR),
								((entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimplyHomeModVariables.PlayerVariables())).YCOR),
								((entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimplyHomeModVariables.PlayerVariables())).ZCOR));
						if (_ent instanceof ServerPlayer _serverPlayer)
							_serverPlayer.connection.teleport(((entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimplyHomeModVariables.PlayerVariables())).XCOR),
									((entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimplyHomeModVariables.PlayerVariables())).YCOR),
									((entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimplyHomeModVariables.PlayerVariables())).ZCOR), _ent.getYRot(), _ent.getXRot());
					}
					{
						double _setval = 12000;
						entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.WCOOL = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
				} else {
					cooldownleft = (entity.getCapability(SimplyHomeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimplyHomeModVariables.PlayerVariables())).WCOOL / 20 + "Seconds until you can use Home again";
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(cooldownleft), false);
				}
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Make sure you are in the same dimension as your home!"), false);
			}
		}
	}
}
