package com.mrcrayfish.goblintraders.trades.type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mrcrayfish.goblintraders.entity.GoblinTrades;
import com.mrcrayfish.goblintraders.trades.GoblinTrade;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public class BasicTrade implements ITradeType<GoblinTrade>
{
    @Override
    public GoblinTrade deserialize(JsonObject object)
    {
        ItemStack offerStack = CraftingHelper.getItemStack(JSONUtils.getJsonObject(object, "offer_item"), true);
        ItemStack paymentStack = CraftingHelper.getItemStack(JSONUtils.getJsonObject(object, "payment_item"), true);
        ItemStack secondaryPaymentStack = ItemStack.EMPTY;
        if(JSONUtils.hasField(object, "secondary_payment_item"))
        {
            secondaryPaymentStack = CraftingHelper.getItemStack(JSONUtils.getJsonObject(object, "secondary_payment_item"), true);
        }
        float priceMultiplier = JSONUtils.getFloat(object, "price_multiplier", 0.05F);
        int maxTrades = JSONUtils.getInt(object, "max_trades", 12);
        int experience = JSONUtils.getInt(object, "experience", 0);
        if(JSONUtils.hasField(object, "enchantments"))
        {
            EnchantmentData[] enchantments = this.getEnchantments(JSONUtils.getJsonArray(object, "enchantments"));
            if(offerStack.getItem() == Items.ENCHANTED_BOOK)
            {
                EnchantmentHelper.setEnchantments(Stream.of(enchantments).collect(Collectors.toMap(o -> o.enchantment, e -> e.enchantmentLevel)), offerStack);
            }
            else
            {
                for(EnchantmentData data : enchantments)
                {
                    offerStack.addEnchantment(data.enchantment, data.enchantmentLevel);
                }
            }
        }
        if(JSONUtils.hasField(object, "potion_effects"))
        {
            Collection<EffectInstance> effects = this.getPotionEffects(JSONUtils.getJsonArray(object, "potion_effects"));
            PotionUtils.appendEffects(offerStack, effects);
        }
        return new GoblinTrade(offerStack, paymentStack, secondaryPaymentStack, maxTrades, experience, priceMultiplier);
    }

    private EnchantmentData[] getEnchantments(JsonArray enchantmentArray)
    {
        List<EnchantmentData> enchantments = new ArrayList<>();
        for(JsonElement enchantmentElement : enchantmentArray)
        {
            JsonObject enchantmentObject = enchantmentElement.getAsJsonObject();
            String id = JSONUtils.getString(enchantmentObject, "id");
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(id));
            if(enchantment != null)
            {
                int level = JSONUtils.getInt(enchantmentObject, "level", 1);
                enchantments.add(new EnchantmentData(enchantment, level));
            }
        }
        return enchantments.toArray(new EnchantmentData[0]);
    }

    private Collection<EffectInstance> getPotionEffects(JsonArray effectsArray)
    {
        List<EffectInstance> effects = new ArrayList<>();
        for(JsonElement effectElement : effectsArray)
        {
            JsonObject effectObject = effectElement.getAsJsonObject();
            String id = JSONUtils.getString(effectObject, "id");
            Effect effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(id));
            if(effect != null)
            {
                int duration = JSONUtils.getInt(effectObject, "duration", 1);
                int amplifier = JSONUtils.getInt(effectObject, "amplifier", 1);
                boolean showParticles = JSONUtils.getBoolean(effectObject, "show_particles", true);
                effects.add(new EffectInstance(effect, duration, amplifier, false, showParticles));
            }
        }
        return effects;
    }
}
