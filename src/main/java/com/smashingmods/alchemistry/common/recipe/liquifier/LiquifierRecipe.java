package com.smashingmods.alchemistry.common.recipe.liquifier;

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
import net.minecraftforge.fluids.FluidStack;


public class LiquifierRecipe implements Recipe<Inventory> {

    private final ResourceLocation recipeId;
    private final String group;
    private final ItemStack input;
    private final FluidStack output;

    public LiquifierRecipe(ResourceLocation pId, String pGroup, ItemStack pInput, FluidStack pOutput) {
        this.recipeId = pId;
        this.group = pGroup;
        this.input = pInput;
        this.output = pOutput;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.LIQUIFIER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.LIQUIFIER_TYPE;
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
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
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
        return String.format("input=%s, outputs=%s", input, output);
    }

    public ItemStack getInput() {
        return input;
    }

    public FluidStack getOutput() {
        return output;
    }
}