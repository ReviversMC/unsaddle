package com.github.reviversmc.unsaddle.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(StriderEntity.class)
public abstract class StriderEntityMixin extends AnimalEntity {
	@Shadow
	@Final
	private static TrackedData<Boolean> SADDLED;

	protected StriderEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	public abstract boolean isSaddled();

	@Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
	private void unsaddle_removeSaddle(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> callback) {
		if (player.isSneaking() && isSaddled() && !hasPassengers()) {
			dropStack(new ItemStack(Items.SADDLE));
			dataTracker.set(SADDLED, false);
			callback.setReturnValue(ActionResult.SUCCESS);
		}
	}
}
