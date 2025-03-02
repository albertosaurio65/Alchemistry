package com.smashingmods.alchemistry.common.recipe.dissolver;

import com.smashingmods.alchemistry.common.recipe.AbstractAlchemistryRecipe;
import com.smashingmods.alchemistry.registry.RecipeRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class DissolverRecipe extends AbstractAlchemistryRecipe {

    private final Ingredient input;
    private final ProbabilitySet output;

    public DissolverRecipe(ResourceLocation pId, String pGroup, Ingredient pInput, ProbabilitySet pOutput) {
        super(pId, pGroup);
        this.input = pInput;
        this.output = pOutput;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.DISSOLVER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.DISSOLVER_TYPE;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(input);
    }

    @Override
    public String toString(){
        return String.format("input=%s, outputs=%s", input, output);
    }

    public Ingredient getInput() {
        return input;
    }

    public ProbabilitySet getOutput() {
        return output;
    }

    public boolean matches(ItemStack pItemStack) {
        return input.test(pItemStack);
    }
}