package com.smashingmods.alchemistry.api.blockentity.handler;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class AutomationStackHandler implements IItemHandlerModifiable {

    private final IItemHandlerModifiable handler;

    public AutomationStackHandler(IItemHandlerModifiable pHandler) {
        this.handler = pHandler;
    }

    public int getSlots() {
        return handler.getSlots();
    }

    public ItemStack getStackInSlot(int pSlot) {
        return handler.getStackInSlot(pSlot);
    }

    public void setStackInSlot(int pSlot, ItemStack pItemStack) {
        handler.setStackInSlot(pSlot, pItemStack);
    }

    public int getSlotLimit(int pSlot) {
        return handler.getSlotLimit(pSlot);
    }

    @Override
    public boolean isItemValid(int pSlot, ItemStack pItemStack) {
        return true;
    }

    public ItemStack insertItem(int pSlot, ItemStack pItemStack, boolean pSimulate) {
        return handler.insertItem(pSlot, pItemStack, pSimulate);
    }

    public ItemStack extractItem(int pSlot, int pAmount, boolean pSimulate) {
        return handler.extractItem(pSlot, pAmount, pSimulate);
    }
}