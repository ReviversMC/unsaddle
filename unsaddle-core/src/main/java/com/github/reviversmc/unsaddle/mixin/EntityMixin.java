package com.github.reviversmc.unsaddle.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.reviversmc.unsaddle.mixinterface.AbstractDonkeyEntityMixinterface;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SaddleItem;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Nameable;
import net.minecraft.world.entity.EntityLike;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput {
	private Entity object = (Entity) (Object) this;

	@Inject(method = "dropStack", at = @At("HEAD"), cancellable = true)
	private void unsaddle_dropStack(ItemStack stack, CallbackInfoReturnable<ActionResult> callback) {
		if (object instanceof AbstractDonkeyEntity donkey) {
			if (((AbstractDonkeyEntityMixinterface) donkey).isCurrentlyRemovingChest()
					&& stack.getItem() instanceof SaddleItem) {
				callback.cancel();
			}
		}
	}
}
