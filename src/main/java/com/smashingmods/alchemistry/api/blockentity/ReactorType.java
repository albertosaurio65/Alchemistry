package com.smashingmods.alchemistry.api.blockentity;

import net.minecraft.util.StringRepresentable;

public enum ReactorType implements StringRepresentable {
    FUSION("fusion"),
    FISSION("fission");

    private final String name;

    ReactorType(String pName) {
        this.name = pName;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
