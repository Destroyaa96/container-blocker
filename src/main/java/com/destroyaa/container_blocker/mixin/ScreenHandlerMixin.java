package com.destroyaa.container_blocker.mixin;

import com.destroyaa.container_blocker.config.BlockedItemsConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {
    
    @Shadow
    public abstract Slot getSlot(int index);

    @Inject(
        method = "internalOnSlotClick",
        at = @At("HEAD"),
        cancellable = true
    )
    private void preventBlockedItemInsertion(
        int slotIndex,
        int button,
        SlotActionType actionType,
        PlayerEntity player,
        CallbackInfo ci
    ) {
        ItemStack cursorStack = player.currentScreenHandler.getCursorStack();
        
        if (BlockedItemsConfig.isBlocked(cursorStack.getItem()) && slotIndex >= 0) {
            Slot slot = getSlot(slotIndex);
            
            if (slot.inventory != player.getInventory()) {
                ci.cancel();
                return;
            }
        }
        
        if (actionType == SlotActionType.QUICK_MOVE && slotIndex >= 0) {
            Slot slot = getSlot(slotIndex);
            ItemStack stack = slot.getStack();
            
            if (BlockedItemsConfig.isBlocked(stack.getItem())) {
                if (slot.inventory == player.getInventory()) {
                    ci.cancel();
                    return;
                }
            }
        }
        
        if (actionType == SlotActionType.SWAP && slotIndex >= 0) {
            Slot slot = getSlot(slotIndex);
            
            if (slot.inventory != player.getInventory()) {
                ItemStack hotbarStack = player.getInventory().getStack(button);
                
                if (BlockedItemsConfig.isBlocked(hotbarStack.getItem())) {
                    ci.cancel();
                    return;
                }
            }
        }
    }
}
