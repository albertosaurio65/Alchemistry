package com.smashingmods.alchemistry.common.recipe.compactor;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class CompactorRecipeSerializer<T extends CompactorRecipe> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {

    private final CompactorRecipeSerializer.IFactory<T> factory;

    public CompactorRecipeSerializer(CompactorRecipeSerializer.IFactory<T> pFactory) {
        this.factory = pFactory;
    }

    @Override
    public T fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        String group = pSerializedRecipe.has("group") ? pSerializedRecipe.get("group").getAsString() : "compactor";
        ItemStack input = ShapedRecipe.itemStackFromJson(pSerializedRecipe.getAsJsonObject("input"));
        ItemStack output = ShapedRecipe.itemStackFromJson(pSerializedRecipe.getAsJsonObject("result"));
        return this.factory.create(pRecipeId, group, input, output);
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        String group = pBuffer.readUtf(Short.MAX_VALUE);
        ItemStack input = pBuffer.readItem();
        ItemStack output = pBuffer.readItem();
        return this.factory.create(pRecipeId, group, input, output);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
        pBuffer.writeUtf(pRecipe.getGroup());
        pBuffer.writeItem(pRecipe.getInput());
        pBuffer.writeItem(pRecipe.getOutput());
    }

    public interface IFactory<T extends Recipe<Inventory>> {
        T create(ResourceLocation pId, String pGroup, ItemStack pInput, ItemStack pOutput);
    }
}
