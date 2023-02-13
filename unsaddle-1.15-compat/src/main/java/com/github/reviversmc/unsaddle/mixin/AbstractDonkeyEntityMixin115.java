package com.github.reviversmc.unsaddle.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.reviversmc.unsaddle.mixinterface.AbstractDonkeyEntityMixinterface;

@Mixin(AbstractDonkeyEntity.class)
public abstract class AbstractDonkeyEntityMixin115 extends HorseBaseEntity implements AbstractDonkeyEntityMixinterface {
	private boolean currentlyRemovingChest;

	protected AbstractDonkeyEntityMixin115(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	public abstract boolean hasChest();

	@Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
	private void unsaddle_removeChest(PlayerEntity player, Hand hand, CallbackInfoReturnable<Boolean> callback) {
		if (player.isSneaking() && hasChest() && !hasPassengers()) {
			currentlyRemovingChest = true;
			dropInventory();
			method_6721(); // onChestedStatusChanged();
			currentlyRemovingChest = false;
			callback.setReturnValue(true);
		}
	}

	@Override
	public boolean isCurrentlyRemovingChest() {
		return currentlyRemovingChest;
	}
}
