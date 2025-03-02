package com.smashingmods.alchemistry.client.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.smashingmods.alchemistry.Alchemistry;
import com.smashingmods.alchemistry.client.jei.RecipeTypes;
import com.smashingmods.alchemistry.common.recipe.dissolver.DissolverRecipe;
import com.smashingmods.alchemistry.common.recipe.dissolver.ProbabilityGroup;
import com.smashingmods.alchemistry.registry.BlockRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.text.NumberFormat;
import java.util.*;

@SuppressWarnings("removal")
public class DissolverRecipeCategory implements IRecipeCategory<DissolverRecipe> {

    private IGuiHelper guiHelper;

    public DissolverRecipeCategory() {}

    public DissolverRecipeCategory(IGuiHelper pGuiHelper) {
        this.guiHelper = pGuiHelper;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("alchemistry.jei.dissolver");
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createDrawable(new ResourceLocation(Alchemistry.MODID, "textures/gui/dissolver_jei.png"), 0, 0, 150, 150);
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.DISSOLVER.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return getRecipeType().getUid();
    }

    @Override
    public Class<? extends DissolverRecipe> getRecipeClass() {
        return DissolverRecipe.class;
    }

    @Override
    public RecipeType<DissolverRecipe> getRecipeType() {
        return RecipeTypes.DISSOLVER;
    }

    @Override
    public void draw(DissolverRecipe pRecipe, IRecipeSlotsView pRecipeSlotsView, PoseStack pPoseStack, double pMouseX, double pMouseY) {

        Font font = Minecraft.getInstance().font;

        List<Double> probabilities = new LinkedList<>();

        pRecipe.getOutput().getProbabilityGroups().forEach(group -> group.getOutput().forEach(itemStack -> probabilities.add(group.getProbability())));
        Collections.sort(probabilities);
        Collections.reverse(probabilities);

        double totalProbability = pRecipe.getOutput().getProbabilityGroups().stream().mapToDouble(ProbabilityGroup::getProbability).sum();
        boolean weighted = pRecipe.getOutput().isWeighted();
        int rolls = pRecipe.getOutput().getRolls();

        font.drawShadow(pPoseStack, String.format("Weighted: %s", weighted), 0, 0, 0xFFFFFFFF);
        font.drawShadow(pPoseStack, String.format("Rolls: %s", rolls), 0, 24, 0xFFFFFFFF);

        int xOrigin = 45;
        int yOrigin = 52;

        for (int row = 0; row < probabilities.size() / 2; row++) {
            for (int column = 0; column < 2; column++) {
                int index = column + row * 2;
                int x = xOrigin + column * 52;
                int y = yOrigin + row * 18;

                if (probabilities.size() > index) {
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    numberFormat.setMaximumFractionDigits(2);
                    double percent = (probabilities.get(index) / totalProbability) * 100;
                    font.drawShadow(pPoseStack, numberFormat.format(percent) + "%", x, y, 0xFFFFFFFF);
                }
            }
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder pBuilder, DissolverRecipe pRecipe, IFocusGroup pFocusGroup) {
        pBuilder.addSlot(RecipeIngredientRole.INPUT, 78, 17).addIngredients(pRecipe.getInput());

        int xOrigin = 25;
        int yOrigin = 46;

        Map<ItemStack, Double> map = new HashMap<>();
        pRecipe.getOutput().getProbabilityGroups().forEach(group -> group.getOutput().forEach(itemStack -> map.put(itemStack, group.getProbability())));

        List<ItemStack> items = map.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<ItemStack, Double>::getValue).reversed())
                .map(Map.Entry::getKey)
                .toList();

        items.forEach(itemStack -> {
            for (int row = 0; row < items.size() / 2; row++) {
                for (int column = 0; column < 2; column++) {
                    int itemIndex = column + row * 2;
                    int x = xOrigin + column * 52;
                    int y = yOrigin + row * 18;

                    if (items.size() > itemIndex) {
                        if (!items.get(itemIndex).isEmpty()) {
                            pBuilder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addItemStack(items.get(itemIndex));
                        } else {
                            pBuilder.addSlot(RecipeIngredientRole.RENDER_ONLY, x, y).addItemStack(new ItemStack(Items.BARRIER)).addTooltipCallback((iRecipeSlotView, list) -> {
                                list.clear();
                                list.add(new TranslatableComponent("alchemistry.container.nothing"));
                            });
                        }
                    }
                }
            }
        });
    }
}
