package com.github.reviversmc.unsaddle.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import com.github.reviversmc.unsaddle.mixinterface.AbstractDonkeyEntityMixinterface;

@Mixin(AbstractDonkeyEntity.class)
public abstract class AbstractDonkeyEntityMixin116 extends HorseBaseEntity implements AbstractDonkeyEntityMixinterface {
	private boolean preventSaddleDrop;

	protected AbstractDonkeyEntityMixin116(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	public abstract boolean hasChest();

	@Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
	private void unsaddle_removeChest(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> callback) {
		if (player.isSneaking() && hasChest() && !hasPassengers()) {
			preventSaddleDrop = true;
			dropInventory();
			onChestedStatusChanged();
			preventSaddleDrop = false;
			callback.setReturnValue(ActionResult.SUCCESS);
		}
	}

	@Override
	public boolean unsaddle_isPreventSaddleDrop() {
		return preventSaddleDrop;
	}

	@Override
	public void unsaddle_setPreventSaddleDrop(boolean value) {
		preventSaddleDrop = value;
	}
}
