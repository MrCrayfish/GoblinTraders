package com.mrcrayfish.goblintraders.trades.type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.trades.GoblinTrade;
import com.mrcrayfish.goblintraders.trades.TradeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
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
    private final EnchantmentInstance[] enchantments;
    private final Collection<MobEffectInstance> mobEffects;

    public BasicTrade(ItemStack offerStack, ItemStack paymentStack, ItemStack secondaryPaymentStack, float priceMultiplier, int maxTrades, int experience, EnchantmentInstance[] enchantments, Collection<MobEffectInstance> mobEffects)
    {
        this.offerStack = offerStack;
        this.paymentStack = paymentStack;
        this.secondaryPaymentStack = secondaryPaymentStack;
        this.priceMultiplier = priceMultiplier;
        this.maxTrades = maxTrades;
        this.experience = experience;
        this.enchantments = enchantments;
        this.mobEffects = mobEffects;
    }

    @Override
    public GoblinTrade createVillagerTrade()
    {
        ItemStack offerStack = this.offerStack.copy();
        if(this.enchantments.length > 0)
        {
            if(offerStack.getItem() == Items.ENCHANTED_BOOK)
            {
                EnchantmentHelper.setEnchantments(Stream.of(this.enchantments).collect(Collectors.toMap(o -> o.enchantment, e -> e.level)), offerStack);
            }
            else
            {
                for(EnchantmentInstance data : this.enchantments)
                {
                    offerStack.enchant(data.enchantment, data.level);
                }
            }
        }
        if(this.mobEffects.size() > 0)
        {
            PotionUtils.setCustomEffects(offerStack, this.mobEffects);
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
            builder.setOfferStack(CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(object, "offer_item"), true));
            builder.setPaymentStack(CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(object, "payment_item"), true));
            if(GsonHelper.isValidNode(object, "secondary_payment_item"))
            {
                builder.setSecondaryPaymentStack(CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(object, "secondary_payment_item"), true));
            }
            builder.setPriceMultiplier(GsonHelper.getAsFloat(object, "price_multiplier", 0.05F));
            builder.setMaxTrades(GsonHelper.getAsInt(object, "max_trades", 12));
            builder.setExperience(GsonHelper.getAsInt(object, "experience", 0));
            if(GsonHelper.isValidNode(object, "enchantments"))
            {
                Collection<EnchantmentInstance> enchantments = this.getEnchantments(GsonHelper.getAsJsonArray(object, "enchantments"));
                for(EnchantmentInstance enchantment : enchantments)
                {
                    builder.addEnchantment(enchantment);
                }
            }
            if(GsonHelper.isValidNode(object, "potion_effects"))
            {
                Collection<MobEffectInstance> effects = this.getPotionEffects(GsonHelper.getAsJsonArray(object, "potion_effects"));
                for(MobEffectInstance effect : effects)
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
                for(EnchantmentInstance enchantment : trade.enchantments)
                {
                    enchantmentArray.add(this.serializeEnchantment(enchantment));
                }
                object.add("enchantments", enchantmentArray);
            }
            if(trade.mobEffects.size() > 0)
            {
                JsonArray effectArray = new JsonArray();
                for(MobEffectInstance effect : trade.mobEffects)
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
            object.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(stack.getItem())).toString());
            object.addProperty("count", stack.getCount());
            if(stack.hasTag())
            {
                object.addProperty("nbt", Objects.requireNonNull(stack.getTag()).toString());
            }
            return object;
        }

        private JsonObject serializeEnchantment(EnchantmentInstance enchantment)
        {
            JsonObject object = new JsonObject();
            object.addProperty("id", Objects.requireNonNull(ForgeRegistries.ENCHANTMENTS.getKey(enchantment.enchantment)).toString());
            object.addProperty("level", enchantment.level);
            return object;
        }

        private JsonObject serializePotionEffect(MobEffectInstance effect)
        {
            JsonObject object = new JsonObject();
            object.addProperty("id", Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getKey(effect.getEffect())).toString());
            object.addProperty("duration", effect.getDuration());
            object.addProperty("amplifier", effect.getAmplifier());
            object.addProperty("show_particles", effect.isVisible());
            return object;
        }

        private Collection<EnchantmentInstance> getEnchantments(JsonArray enchantmentArray)
        {
            List<EnchantmentInstance> enchantments = new ArrayList<>();
            for(JsonElement enchantmentElement : enchantmentArray)
            {
                JsonObject enchantmentObject = enchantmentElement.getAsJsonObject();
                String id = GsonHelper.getAsString(enchantmentObject, "id");
                Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(id));
                if(enchantment != null)
                {
                    int level = GsonHelper.getAsInt(enchantmentObject, "level", 1);
                    enchantments.add(new EnchantmentInstance(enchantment, level));
                }
            }
            return enchantments;
        }

        private Collection<MobEffectInstance> getPotionEffects(JsonArray effectsArray)
        {
            List<MobEffectInstance> effects = new ArrayList<>();
            for(JsonElement effectElement : effectsArray)
            {
                JsonObject effectObject = effectElement.getAsJsonObject();
                String id = GsonHelper.getAsString(effectObject, "id");
                MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(id));
                if(effect != null)
                {
                    int duration = GsonHelper.getAsInt(effectObject, "duration", 1);
                    int amplifier = GsonHelper.getAsInt(effectObject, "amplifier", 1);
                    boolean showParticles = GsonHelper.getAsBoolean(effectObject, "show_particles", true);
                    effects.add(new MobEffectInstance(effect, duration, amplifier, false, showParticles));
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
        private List<EnchantmentInstance> enchantments = new ArrayList<>();
        private List<MobEffectInstance> modEffects = new ArrayList<>();

        private Builder() {}

        public static Builder create()
        {
            return new Builder();
        }

        public BasicTrade build()
        {
            return new BasicTrade(this.offerStack, this.paymentStack, this.secondaryPaymentStack, this.priceMultiplier, this.maxTrades, this.experience, this.enchantments.toArray(new EnchantmentInstance[0]), this.modEffects);
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

        public Builder addEnchantment(EnchantmentInstance enchantment)
        {
            this.enchantments.add(enchantment);
            return this;
        }

        public Builder addPotionEffect(MobEffectInstance effect)
        {
            this.modEffects.add(effect);
            return this;
        }
    }
}
