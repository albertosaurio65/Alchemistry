package com.smashingmods.alchemistry.common.recipe.liquifier;

import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Objects;

public class LiquifierRecipeSerializer<T extends LiquifierRecipe> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {

    private final IFactory<T> factory;

    public LiquifierRecipeSerializer(IFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public T fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {

        String group = pSerializedRecipe.has("group") ? pSerializedRecipe.get("group").getAsString() : "liquifier";

        if (!pSerializedRecipe.has("input")) {
            throw new JsonSyntaxException("Missing input, expected to find an object.");
        }

        ItemStack input = pSerializedRecipe.has("input") ? ShapedRecipe.itemStackFromJson(pSerializedRecipe.getAsJsonObject("input")) : ItemStack.EMPTY;

        if (!pSerializedRecipe.has("result")) {
            throw new JsonSyntaxException("Missing result, expected to find an object.");
        }

        JsonObject outputObject = pSerializedRecipe.getAsJsonObject("result");
        ResourceLocation fluidLocation = new ResourceLocation(outputObject.get("fluid").getAsString());
        int fluidAmount = outputObject.has("amount") ? outputObject.get("amount").getAsInt() : 1000;
        FluidStack output = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(fluidLocation)), fluidAmount);

        return this.factory.create(pRecipeId, group, input, output);
    }

    @Override
    public T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        String group = pBuffer.readUtf(Short.MAX_VALUE);
        ItemStack input = pBuffer.readItem();
        FluidStack output = pBuffer.readFluidStack();
        return this.factory.create(pRecipeId, group, input, output);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
        pBuffer.writeUtf(pRecipe.getGroup());
        pBuffer.writeItem(pRecipe.getInput());
        pBuffer.writeFluidStack(pRecipe.getOutput());
    }

    public interface IFactory<T extends Recipe<Inventory>> {
        T create(ResourceLocation pId, String pGroup, ItemStack pInput, FluidStack pOutput);
    }
}