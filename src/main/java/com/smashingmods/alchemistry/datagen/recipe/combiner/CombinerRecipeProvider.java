package com.smashingmods.alchemistry.datagen.recipe.combiner;

import com.google.common.collect.Lists;
import com.smashingmods.alchemistry.Alchemistry;
import com.smashingmods.alchemistry.registry.BlockRegistry;
import com.smashingmods.chemlib.common.items.CompoundItem;
import com.smashingmods.chemlib.common.items.ElementItem;
import com.smashingmods.chemlib.registry.ItemRegistry;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;
import java.util.function.Consumer;

import static com.smashingmods.alchemistry.datagen.recipe.StackUtils.toStack;

public class CombinerRecipeProvider {

    private final Consumer<FinishedRecipe> consumer;

    public CombinerRecipeProvider(Consumer<FinishedRecipe> pConsumer) {
        this.consumer = pConsumer;
    }

    public static void register(Consumer<FinishedRecipe> pConsumer) {
        new CombinerRecipeProvider(pConsumer).register();
    }

    private void register() {

        // Add Chemlib recipes
        ItemRegistry.getCompounds().stream().forEach(compoundItem -> {
            List<ItemStack> components = new ArrayList<>();
            compoundItem.getComponents().forEach((name, count) -> {
                Optional<ElementItem> optionalElement = ItemRegistry.getElementByName(name);
                Optional<CompoundItem> optionalCompound = ItemRegistry.getCompoundByName(name);
                optionalElement.ifPresent(element -> components.add(new ItemStack(element, count)));
                optionalCompound.ifPresent(compound -> components.add(new ItemStack(compound, count)));
            });
            combiner(new ItemStack(compoundItem), components);
        });

        // Reactor Glass
        combiner(new ItemStack(BlockRegistry.REACTOR_GLASS.get().asItem()), Lists.newArrayList(toStack("silicon_dioxide"), toStack("lead_oxide")));

        // saplings
        combiner(new ItemStack(Items.OAK_SAPLING), Lists.newArrayList(toStack("oxygen"),
                toStack("cellulose", 2)));

        combiner(new ItemStack(Items.SPRUCE_SAPLING), Lists.newArrayList(toStack("oxygen"),
                toStack("cellulose", 2)));

        combiner(new ItemStack(Items.JUNGLE_SAPLING), Lists.newArrayList(toStack("oxygen"),
                toStack("cellulose", 2)));

        combiner(new ItemStack(Items.ACACIA_SAPLING), Lists.newArrayList(toStack("oxygen"),
                toStack("cellulose", 2)));

        combiner(new ItemStack(Items.DARK_OAK_SAPLING), Lists.newArrayList(toStack("oxygen"),
                toStack("cellulose", 2)));

        combiner(new ItemStack(Items.BIRCH_SAPLING), Lists.newArrayList(toStack("oxygen"),
                toStack("cellulose", 2)));

        // food
        combiner(new ItemStack(Items.CARROT), Lists.newArrayList(toStack("cellulose"),
                toStack("beta_carotene")));

        combiner(new ItemStack(Items.SUGAR_CANE), Lists.newArrayList(toStack("cellulose"),
                toStack("sucrose")));

        combiner(new ItemStack(Items.EGG), Lists.newArrayList(toStack("calcium_carbonate", 8),
                toStack("protein", 2)));

        combiner(new ItemStack(Items.POTATO), Lists.newArrayList(toStack("starch"),
                toStack("potassium", 4)));

        combiner(new ItemStack(Items.APPLE), Lists.newArrayList(toStack("cellulose"),
                toStack("sucrose")));

        combiner(new ItemStack(Items.WHEAT_SEEDS), Lists.newArrayList(toStack("triglyceride"),
                toStack("sucrose")));

        combiner(new ItemStack(Items.PUMPKIN_SEEDS), Lists.newArrayList(toStack("triglyceride"),
                toStack("sucrose")));

        combiner(new ItemStack(Items.MELON_SEEDS), Lists.newArrayList(toStack("triglyceride"),
                toStack("sucrose")));

        combiner(new ItemStack(Items.BEETROOT_SEEDS), Lists.newArrayList(toStack("triglyceride"),
                toStack("sucrose"),
                toStack("iron_oxide")));

        combiner(new ItemStack(Items.BEETROOT), Lists.newArrayList(toStack("sucrose"),
                toStack("iron_oxide")));

        // mob drops
        combiner(new ItemStack(Items.BONE_MEAL), Lists.newArrayList(toStack("hydroxylapatite", 2)));

        combiner(new ItemStack(Items.LEATHER), Lists.newArrayList(toStack("protein", 3)));

        combiner(new ItemStack(Items.ROTTEN_FLESH), Lists.newArrayList(toStack("protein", 3)));
        combiner(new ItemStack(Items.SLIME_BALL), Lists.newArrayList(toStack("protein",
                2), toStack("sucrose", 2)));

        combiner(new ItemStack(Items.STRING), Lists.newArrayList(
                toStack("protein", 2)));

        combiner(new ItemStack(Items.FEATHER), Lists.newArrayList(
                toStack("protein", 2)));

        combiner(new ItemStack(Items.SPIDER_EYE), Lists.newArrayList(
                toStack("beta_carotene", 2),
                toStack("protein", 2)));

        combiner(new ItemStack(Items.WHITE_WOOL), Lists.newArrayList(
                toStack("protein"),
                toStack("triglyceride")));

//        combiner(new ItemStack(Items.NETHER_STAR), Lists.newArrayList(
//                toStack("lutetium", 64),
//                toStack("hydrogen", 64),
//                toStack("titanium", 64),
//                toStack("hydrogen", 64),
//                toStack("hydrogen", 64),
//                toStack("hydrogen", 64),
//                toStack("dysprosium", 64),
//                toStack("hydrogen", 64),
//                toStack("mendelevium", 64)));

        // gems
//        combiner(new ItemStack(Items.DIAMOND), Lists.newArrayList(
//                toStack("carbon", 64),
//                toStack("carbon", 64),
//                toStack("carbon", 64),
//                toStack("carbon", 64),
//                toStack("carbon", 64),
//                toStack("carbon", 64),
//                toStack("carbon", 64),
//                toStack("carbon", 64)));

//        combiner(new ItemStack(Items.EMERALD), Lists.newArrayList(
//                toStack("beryl", 8),
//                toStack("chromium", 8),
//                toStack("vanadium", 4)));

//        combiner(new ItemStack(Items.LAPIS_LAZULI), Lists.newArrayList(
//                toStack("sodium", 6),
//                toStack("calcium", 2),
//                toStack("aluminum", 6),
//                toStack("silicon", 6),
//                toStack("oxygen", 24),
//                toStack("sulfur", 2)));

//        combiner(new ItemStack(Items.NETHER_WART), Lists.newArrayList(
//                toStack("cellulose"),
//                toStack("germanium", 4),
//                toStack("selenium", 4)));

//        combiner(new ItemStack(Items.DIRT), Lists.newArrayList(toStack("water"),
//                toStack("cellulose"),
//                toStack("kaolinite")));

//        combiner(new ItemStack(Items.GRASS_BLOCK), Lists.newArrayList(toStack("water"),
//                toStack("cellulose"),
//                toStack("kaolinite")));

//        combiner(new ItemStack(Items.MYCELIUM), Lists.newArrayList(toStack("psilocybin"),
//                toStack("water"),
//                toStack("cellulose"),
//                toStack("kaolinite")));

        combiner(new ItemStack(Items.WATER_BUCKET), Lists.newArrayList(toStack("water", 16),
                new ItemStack(Items.BUCKET)));

//        combiner(new ItemStack(Items.MILK_BUCKET), Lists.newArrayList(toStack("calcium", 4),
//                toStack("protein", 2),
//                toStack("water", 16),
//                toStack("sucrose"),
//                new ItemStack(Items.BUCKET)));

        combiner(new ItemStack(Items.REDSTONE_BLOCK), Lists.newArrayList(toStack("iron_oxide", 9),
                toStack("strontium_carbonate", 9)));

        combiner(new ItemStack(Items.REDSTONE), Lists.newArrayList(toStack("iron_oxide"), toStack("strontium_carbonate")));
    }

    private void combiner(ItemStack pOutput, List<ItemStack> pInput) {
        CombinerRecipeBuilder.createRecipe(pOutput, pInput)
                .group(Alchemistry.MODID + ":combiner")
                .unlockedBy("has_the_recipe", RecipeUnlockedTrigger.unlocked(getLocation(pOutput)))
                .save(consumer);
    }

    private ResourceLocation getLocation(ItemStack itemStack) {
        Objects.requireNonNull(itemStack.getItem().getRegistryName());
        return new ResourceLocation(Alchemistry.MODID, String.format("combiner/%s", itemStack.getItem().getRegistryName().getPath()));
    }
}
