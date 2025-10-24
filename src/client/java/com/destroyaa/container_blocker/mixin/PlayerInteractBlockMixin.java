package com.destroyaa.container_blocker.mixin;

import com.destroyaa.container_blocker.config.BlockedItemsConfig;
import net.minecraft.block.Block;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.DecoratedPotBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class PlayerInteractBlockMixin {
    
    @Inject(
        method = "interactBlock",
        at = @At("HEAD"),
        cancellable = true
    )
    private void preventBlockedItemPlacement(
        ClientPlayerEntity player,
        Hand hand,
        BlockHitResult hitResult,
        CallbackInfoReturnable<ActionResult> cir
    ) {
        ItemStack heldStack = player.getStackInHand(hand);
        
        if (BlockedItemsConfig.isBlocked(heldStack.getItem())) {
            Block block = player.getWorld().getBlockState(hitResult.getBlockPos()).getBlock();
            
            // Only prevent interaction with pots and chiseled bookshelves
            if (block instanceof DecoratedPotBlock || block instanceof ChiseledBookshelfBlock) {
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}
