package com.destroyaa.container_blocker.mixin;

import com.destroyaa.container_blocker.config.BlockedItemsConfig;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class PlayerInteractEntityMixin {
    
    @Inject(
        method = "interactEntity",
        at = @At("HEAD"),
        cancellable = true
    )
    private void preventBlockedItemInItemFrame(
        PlayerEntity player,
        Entity entity,
        Hand hand,
        CallbackInfoReturnable<ActionResult> cir
    ) {
        // This covers both regular and glow item frames (GlowItemFrameEntity extends ItemFrameEntity)
        if (entity instanceof ItemFrameEntity) {
            ItemStack heldStack = player.getStackInHand(hand);
            
            if (BlockedItemsConfig.isBlocked(heldStack.getItem())) {
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}
