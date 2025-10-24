package com.destroyaa.container_blocker.mixin;

import com.destroyaa.container_blocker.config.BlockedItemsConfig;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {

    @Inject(
        method = "canExtract",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void preventBlockedItemExtraction(
        Inventory inventory,
        Inventory targetInventory,
        ItemStack stack,
        int slot,
        Direction direction,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (BlockedItemsConfig.isBlocked(stack.getItem())) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
        method = "transfer(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/item/ItemStack;ILnet/minecraft/util/math/Direction;)Lnet/minecraft/item/ItemStack;",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void preventBlockedItemTransfer(
        Inventory from,
        Inventory to,
        ItemStack stack,
        int slot,
        Direction direction,
        CallbackInfoReturnable<ItemStack> cir
    ) {
        if (BlockedItemsConfig.isBlocked(stack.getItem())) {
            cir.setReturnValue(stack); // Return the stack unchanged to prevent transfer
        }
    }

    @Inject(
        method = "canMergeItems",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void preventBlockedItemMerge(
        ItemStack stack1,
        ItemStack stack2,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (BlockedItemsConfig.isBlocked(stack1.getItem()) || BlockedItemsConfig.isBlocked(stack2.getItem())) {
            cir.setReturnValue(false);
        }
    }
}
