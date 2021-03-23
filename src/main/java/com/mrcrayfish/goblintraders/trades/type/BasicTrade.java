package com.mrcrayfish.goblintraders.trades.type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.trades.GoblinTrade;
import com.mrcrayfish.goblintraders.trades.TradeSerializer;
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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public class BasicTrade implements ITradeType<GoblinTrade>
{
    public static final Serializer SERIALIZER = new Serializer();

    private final ItemStack offerStack;
    private final ItemStack paymentStack;
    private final ItemStack secondaryPaymentStack;
    private final float priceMultiplier;
    private final int maxTrades;
    private final int experience;
    private final EnchantmentData[] enchantments;
    private final Collection<EffectInstance> potionEffects;

    public BasicTrade(ItemStack offerStack, ItemStack paymentStack, ItemStack secondaryPaymentStack, float priceMultiplier, int maxTrades, int experience, EnchantmentData[] enchantments, Collection<EffectInstance> potionEffects)
    {
        this.offerStack = offerStack;
        this.paymentStack = paymentStack;
        this.secondaryPaymentStack = secondaryPaymentStack;
        this.priceMultiplier = priceMultiplier;
        this.maxTrades = maxTrades;
        this.experience = experience;
        this.enchantments = enchantments;
        this.potionEffects = potionEffects;
    }

    @Override
    public GoblinTrade createVillagerTrade()
    {
        ItemStack offerStack = this.offerStack.copy();
        if(this.enchantments.length > 0)
        {
            if(offerStack.getItem() == Items.ENCHANTED_BOOK)
            {
                EnchantmentHelper.setEnchantments(Stream.of(this.enchantments).collect(Collectors.toMap(o -> o.enchantment, e -> e.enchantmentLevel)), offerStack);
            }
            else
            {
                for(EnchantmentData data : this.enchantments)
                {
                    offerStack.addEnchantment(data.enchantment, data.enchantmentLevel);
                }
            }
        }
        if(this.potionEffects.size() > 0)
        {
            PotionUtils.appendEffects(offerStack, this.potionEffects);
        }
        return new GoblinTrade(offerStack, this.paymentStack.copy(), this.secondaryPaymentStack.copy(), this.maxTrades, this.experience, this.priceMultiplier);
    }

    @Override
    public JsonObject serialize()
    {
        return SERIALIZER.serialize(this);
    }

    public static class Serializer extends TradeSerializer<BasicTrade>
    {
        Serializer()
        {
            super(new ResourceLocation(Reference.MOD_ID, "basic"));
        }

        @Override
        public BasicTrade deserialize(JsonObject object)
        {
            Builder builder = Builder.create();
            builder.setOfferStack(CraftingHelper.getItemStack(JSONUtils.getJsonObject(object, "offer_item"), true));
            builder.setPaymentStack(CraftingHelper.getItemStack(JSONUtils.getJsonObject(object, "payment_item"), true));
            if(JSONUtils.hasField(object, "secondary_payment_item"))
            {
                builder.setSecondaryPaymentStack(CraftingHelper.getItemStack(JSONUtils.getJsonObject(object, "secondary_payment_item"), true));
            }
            builder.setPriceMultiplier(JSONUtils.getFloat(object, "price_multiplier", 0.05F));
            builder.setMaxTrades(JSONUtils.getInt(object, "max_trades", 12));
            builder.setExperience(JSONUtils.getInt(object, "experience", 0));
            if(JSONUtils.hasField(object, "enchantments"))
            {
                Collection<EnchantmentData> enchantments = this.getEnchantments(JSONUtils.getJsonArray(object, "enchantments"));
                for(EnchantmentData enchantment : enchantments)
                {
                    builder.addEnchantment(enchantment);
                }
            }
            if(JSONUtils.hasField(object, "potion_effects"))
            {
                Collection<EffectInstance> effects = this.getPotionEffects(JSONUtils.getJsonArray(object, "potion_effects"));
                for(EffectInstance effect : effects)
                {
                    builder.addPotionEffect(effect);
                }
            }
            return builder.build();
        }

        @Override
        public JsonObject serialize(BasicTrade trade)
        {
            JsonObject object = super.serialize(trade);
            object.add("offer_item", this.serializeItemStack(trade.offerStack));
            object.add("payment_item", this.serializeItemStack(trade.paymentStack));
            if(!trade.secondaryPaymentStack.isEmpty())
            {
                object.add("secondary_payment_item", this.serializeItemStack(trade.secondaryPaymentStack));
            }
            if(trade.priceMultiplier != 0.05F)
            {
                object.addProperty("price_multiplier", trade.priceMultiplier);
            }
            if(trade.maxTrades != 12)
            {
                object.addProperty("max_trades", trade.maxTrades);
            }
            if(trade.experience != 0)
            {
                object.addProperty("experience", trade.experience);
            }
            if(trade.enchantments.length > 0)
            {
                JsonArray enchantmentArray = new JsonArray();
                for(EnchantmentData enchantment : trade.enchantments)
                {
                    enchantmentArray.add(this.serializeEnchantment(enchantment));
                }
                object.add("enchantments", enchantmentArray);
            }
            if(trade.potionEffects.size() > 0)
            {
                JsonArray effectArray = new JsonArray();
                for(EffectInstance effect : trade.potionEffects)
                {
                    effectArray.add(this.serializePotionEffect(effect));
                }
                object.add("potion_effects", effectArray);
            }
            return object;
        }

        private JsonObject serializeItemStack(ItemStack stack)
        {
            JsonObject object = new JsonObject();
            object.addProperty("item", Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
            object.addProperty("count", stack.getCount());
            if(stack.hasTag())
            {
                object.addProperty("nbt", Objects.requireNonNull(stack.getTag()).toString());
            }
            return object;
        }

        private JsonObject serializeEnchantment(EnchantmentData enchantment)
        {
            JsonObject object = new JsonObject();
            object.addProperty("id", Objects.requireNonNull(enchantment.enchantment.getRegistryName()).toString());
            object.addProperty("level", enchantment.enchantmentLevel);
            return object;
        }

        private JsonObject serializePotionEffect(EffectInstance effect)
        {
            JsonObject object = new JsonObject();
            object.addProperty("id", Objects.requireNonNull(effect.getPotion().getRegistryName()).toString());
            object.addProperty("duration", effect.getDuration());
            object.addProperty("amplifier", effect.getAmplifier());
            object.addProperty("show_particles", effect.doesShowParticles());
            return object;
        }

        private Collection<EnchantmentData> getEnchantments(JsonArray enchantmentArray)
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
            return enchantments;
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

    public static class Builder
    {
        private ItemStack offerStack;
        private ItemStack paymentStack;
        private ItemStack secondaryPaymentStack = ItemStack.EMPTY;
        private float priceMultiplier = 0.0F;
        private int maxTrades = 12;
        private int experience = 10;
        private List<EnchantmentData> enchantments = new ArrayList<>();
        private List<EffectInstance> potionEffects = new ArrayList<>();

        private Builder() {}

        public static Builder create()
        {
            return new Builder();
        }

        public BasicTrade build()
        {
            return new BasicTrade(this.offerStack, this.paymentStack, this.secondaryPaymentStack, this.priceMultiplier, this.maxTrades, this.experience, this.enchantments.toArray(new EnchantmentData[0]), this.potionEffects);
        }

        public Builder setOfferStack(ItemStack offerStack)
        {
            this.offerStack = offerStack;
            return this;
        }

        public Builder setPaymentStack(ItemStack paymentStack)
        {
            this.paymentStack = paymentStack;
            return this;
        }

        public Builder setSecondaryPaymentStack(ItemStack secondaryPaymentStack)
        {
            this.secondaryPaymentStack = secondaryPaymentStack;
            return this;
        }

        public Builder setPriceMultiplier(float priceMultiplier)
        {
            this.priceMultiplier = priceMultiplier;
            return this;
        }

        public Builder setMaxTrades(int maxTrades)
        {
            this.maxTrades = maxTrades;
            return this;
        }

        public Builder setExperience(int experience)
        {
            this.experience = experience;
            return this;
        }

        public Builder addEnchantment(EnchantmentData enchantment)
        {
            this.enchantments.add(enchantment);
            return this;
        }

        public Builder addPotionEffect(EffectInstance effect)
        {
            this.potionEffects.add(effect);
            return this;
        }
    }
}
