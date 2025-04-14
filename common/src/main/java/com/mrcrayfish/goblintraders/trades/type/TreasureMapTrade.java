package com.mrcrayfish.goblintraders.trades.type;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.trades.GoblinMerchantOffer;
import com.mrcrayfish.goblintraders.trades.TradeCost;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

import java.util.Optional;

public class TreasureMapTrade implements BaseTrade
{
    public static final ResourceLocation ID = Utils.resource("treasure_map");

    public static final Codec<TagKey<Structure>> STRUCTURE_CODEC = ResourceLocation.CODEC.xmap(id -> TagKey.create(Registries.STRUCTURE, id), TagKey::location);
    public static final MapCodec<TreasureMapTrade> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
        STRUCTURE_CODEC.fieldOf("structure").forGetter(trade -> trade.structure),
        BuiltInRegistries.MAP_DECORATION_TYPE.holderByNameCodec().fieldOf("decoration").forGetter(trade -> trade.mapDecoration),
        ComponentSerialization.CODEC.fieldOf("name").forGetter(trade -> trade.name),
        TradeCost.CODEC.fieldOf("payment_item").forGetter(trade -> trade.primaryPayment),
        TradeCost.CODEC.lenientOptionalFieldOf("secondary_payment_item").forGetter(trade -> trade.secondaryPayment),
        Codec.FLOAT.optionalFieldOf("price_multiplier", 0F).forGetter(trade -> trade.priceMultiplier),
        Codec.INT.optionalFieldOf("max_trades", 12).forGetter(trade -> trade.maxTrades),
        Codec.INT.optionalFieldOf("experience", 0).forGetter(trade -> trade.experience)
    ).apply(builder, TreasureMapTrade::new));

    private final TagKey<Structure> structure;
    private final Holder<MapDecorationType> mapDecoration;
    private final Component name;
    private final TradeCost primaryPayment;
    private final Optional<TradeCost> secondaryPayment;
    private final float priceMultiplier;
    private final int maxTrades;
    private final int experience;

    public TreasureMapTrade(TagKey<Structure> structure, Holder<MapDecorationType> mapDecoration, Component name, TradeCost primaryPayment, Optional<TradeCost> secondaryPayment, float priceMultiplier, int maxTrades, int experience)
    {
        this.structure = structure;
        this.mapDecoration = mapDecoration;
        this.name = name;
        this.primaryPayment = primaryPayment;
        this.secondaryPayment = secondaryPayment;
        this.priceMultiplier = priceMultiplier;
        this.maxTrades = maxTrades;
        this.experience = experience;
    }

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    @Override
    public MerchantOffer createVanillaOffer(AbstractGoblinEntity goblin, RandomSource random)
    {
        if(goblin.level() instanceof ServerLevel level)
        {
            BlockPos pos = level.findNearestMapStructure(this.structure, goblin.blockPosition(), 100, true);
            if(pos != null)
            {
                ItemStack map = MapItem.create(level, pos.getX(), pos.getZ(), (byte) 2, true, true);
                MapItem.renderBiomePreviewMap(level, map);
                MapItemSavedData.addTargetDecoration(map, pos, "+", this.mapDecoration);
                map.set(DataComponents.ITEM_NAME, this.name);
                map.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
                map.set(DataComponents.RARITY, Rarity.RARE);
                ItemCost primaryCost = this.primaryPayment.createVanillaCost(random);
                Optional<ItemCost> secondaryCost = this.secondaryPayment.map(cost -> cost.createVanillaCost(random));
                return new GoblinMerchantOffer(primaryCost, secondaryCost, map, this.maxTrades, 0, this.experience);
            }
        }
        return null;
    }

    public static class Builder
    {
        private TagKey<Structure> structure;
        private Holder<MapDecorationType> mapDecoration;
        private Component name;
        private TradeCost paymentStack;
        private TradeCost secondaryPaymentStack;
        private float priceMultiplier = 0.0F;
        private int maxTrades = 12;
        private int experience = 10;

        private Builder() {}

        public static Builder create()
        {
            return new Builder();
        }

        public TreasureMapTrade build()
        {
            Preconditions.checkNotNull(this.structure, "No structure was set");
            Preconditions.checkNotNull(this.mapDecoration, "No map decoration was set");
            Preconditions.checkNotNull(this.paymentStack, "No payment was set");
            Preconditions.checkNotNull(this.name, "No name was set");
            return new TreasureMapTrade(this.structure, this.mapDecoration, this.name, this.paymentStack, Optional.ofNullable(this.secondaryPaymentStack), this.priceMultiplier, this.maxTrades, this.experience);
        }

        public Builder setStructure(TagKey<Structure> structure)
        {
            this.structure = structure;
            return this;
        }

        public Builder setMapDecoration(Holder<MapDecorationType> mapDecoration)
        {
            this.mapDecoration = mapDecoration;
            return this;
        }

        public Builder setName(Component name)
        {
            this.name = name;
            return this;
        }

        public Builder setPaymentStack(TradeCost paymentStack)
        {
            this.paymentStack = paymentStack;
            return this;
        }

        public Builder setSecondaryPaymentStack(TradeCost secondaryPaymentStack)
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
    }
}
