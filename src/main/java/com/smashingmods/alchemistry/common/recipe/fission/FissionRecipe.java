package com.smashingmods.alchemistry.common.recipe.fission;

import com.smashingmods.alchemistry.registry.RecipeRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public class FissionRecipe implements Recipe<Inventory> {

    private final ResourceLocation recipeId;
    private final String group;
    private final ItemStack input;
    private final ItemStack output1;
    private final ItemStack output2;

    public FissionRecipe(ResourceLocation pId, String pGroup, ItemStack pInput, ItemStack pOutput1, ItemStack pOutput2) {
        this.recipeId = pId;
        this.group = pGroup;
        this.input = pInput;
        this.output1 = pOutput1;
        this.output2 = pOutput2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.FISSION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.FISSION_TYPE;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public boolean matches(Inventory pContainer, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(Inventory pContainer) {
        return output1;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.of(input));
    }

    @Override
    public String toString(){
        return String.format("input=%s, outputs=%s", input, List.of(output1, output2));
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput1() {
        return output1;
    }

    public ItemStack getOutput2() {
        return output2;
    }
}
