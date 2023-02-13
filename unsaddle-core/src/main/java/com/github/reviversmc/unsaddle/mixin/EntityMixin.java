package com.github.reviversmc.unsaddle.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SaddleItem;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;

import com.github.reviversmc.unsaddle.mixinterface.AbstractDonkeyEntityMixinterface;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, CommandOutput {
	private Entity object = (Entity) (Object) this;

	@Inject(method = "dropStack", at = @At("HEAD"), cancellable = true)
	private void unsaddle_dropStack(ItemStack stack, CallbackInfoReturnable<?> callback) {
		if (object instanceof AbstractDonkeyEntity) {
			AbstractDonkeyEntityMixinterface donkey = (AbstractDonkeyEntityMixinterface) (AbstractDonkeyEntity) object;

			if (donkey.unsaddle_isPreventSaddleDrop()
					&& stack.getItem() instanceof SaddleItem) {
				donkey.unsaddle_setPreventSaddleDrop(false);
				callback.cancel();
			}
		}
	}
}
