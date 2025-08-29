package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.config.ShieldLibConfig;
import com.github.stellarwind22.shieldlib.lib.event.ShieldTooltipEvent;
import com.github.stellarwind22.shieldlib.lib.model.*;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import com.github.stellarwind22.shieldlib.lib.render.*;
import com.github.stellarwind22.shieldlib.mixin.SheetsAccessor;
import com.github.stellarwind22.shieldlib.mixin.SpecialModelRenderersAccessor;
import com.github.stellarwind22.shieldlib.test.ShieldLibTests;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.phys.Vec2;

import java.util.Map;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ShieldLibClient {

    public static final ResourceLocation TOWER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "tower_shield");
    public static final ResourceLocation BUCKLER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "buckler_shield");
    public static final ResourceLocation HEATER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "heater_shield");
    public static final ResourceLocation TARGE_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "targe_shield");
    public static final ResourceLocation SPIKED_TOWER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "spiked_tower_shield");
    public static final ResourceLocation SPIKED_BUCKLER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "spiked_buckler_shield");
    public static final ResourceLocation SPIKED_HEATER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "spiked_heater_shield");
    public static final ResourceLocation SPIKED_TARGE_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "spiked_targe_shield");

    public static final ResourceLocation SHIELD_ATLAS_LOCATION = ResourceLocation.withDefaultNamespace("textures/atlas/shield_patterns.png");

    public static final ExtraCodecs.LateBoundIdMapper<ResourceLocation, MapCodec<? extends SpecialModelRenderer.Unbaked>> ID_MAPPER = SpecialModelRenderersAccessor.getIDMapper();

    public static boolean IS_DEV;

    public static void init(boolean isDev) {

        IS_DEV = isDev;

        ID_MAPPER.put(
                TOWER_SHIELD_MODEL_TYPE,
                TowerShieldModelRenderer.Unbaked.CODEC
        );

        ID_MAPPER.put(
                BUCKLER_SHIELD_MODEL_TYPE,
                BucklerShieldModelRenderer.Unbaked.CODEC
        );

        ID_MAPPER.put(
                HEATER_SHIELD_MODEL_TYPE,
                HeaterShieldModelRenderer.Unbaked.CODEC
        );

        ID_MAPPER.put(
                TARGE_SHIELD_MODEL_TYPE,
                TargeShieldModelRenderer.Unbaked.CODEC
        );

        ID_MAPPER.put(
                SPIKED_TOWER_SHIELD_MODEL_TYPE,
                SpikedTowerShieldModelRenderer.Unbaked.CODEC
        );

        ID_MAPPER.put(
                SPIKED_BUCKLER_SHIELD_MODEL_TYPE,
                SpikedBucklerShieldModelRenderer.Unbaked.CODEC
        );

        ID_MAPPER.put(
                SPIKED_HEATER_SHIELD_MODEL_TYPE,
                SpikedHeaterShieldModelRenderer.Unbaked.CODEC
        );

        ID_MAPPER.put(
                SPIKED_TARGE_SHIELD_MODEL_TYPE,
                SpikedTargeShieldModelRenderer.Unbaked.CODEC
        );

        EntityModelLayerRegistry.register(BucklerShieldLibModel.LOCATION,       BucklerShieldLibModel::createLayer);
        EntityModelLayerRegistry.register(HeaterShieldModel.LOCATION,           HeaterShieldModel::createLayer);
        EntityModelLayerRegistry.register(TargeShieldModel.LOCATION,            TargeShieldModel::createLayer);
        EntityModelLayerRegistry.register(SpikedTowerShieldModel.LOCATION,      SpikedTowerShieldModel::createLayer);
        EntityModelLayerRegistry.register(SpikedBucklerShieldModel.LOCATION,    SpikedBucklerShieldModel::createLayer);
        EntityModelLayerRegistry.register(SpikedHeaterShieldModel.LOCATION,     SpikedHeaterShieldModel::createLayer);
        EntityModelLayerRegistry.register(SpikedTargeShieldModel.LOCATION,      SpikedTargeShieldModel::createLayer);

        ShieldTooltipEvent.EVENT.register((player,stack, context, flag, tooltip) -> {
            if(stack.get(DataComponents.BLOCKS_ATTACKS) == null || stack.is(ShieldLibTags.NO_TOOLTIP)) return;

            switch (ShieldLibConfig.cooldown_tooltip_mode) {

                case DISABLED -> { return; }

                case NORMAL, COMPACT -> {
                    if(!stack.has(DataComponents.BLOCKS_ATTACKS)) return;

                    BlocksAttacks blocksAttacks = stack.get(DataComponents.BLOCKS_ATTACKS);

                    if(blocksAttacks != null) {
                        float cooldownSeconds = ShieldLib.getCooldownSecondsWithModifiers(player, stack, blocksAttacks);

                        tooltip.add(Component.literal(""));

                        String translated = String.format(Component.translatable("shieldlib.cooldown_tooltip.head").getString(), Component.translatable("tag.item.c.tools.axe").getString());

                        tooltip.add(Component.literal(translated).withStyle(ChatFormatting.GRAY));

                        String cooldown = String.valueOf(cooldownSeconds).replaceAll("\\.0*$", "");
                        String cooldownTranslated = String.format(Component.translatable("shieldlib.cooldown_tooltip.body").getString(), cooldown);
                        tooltip.add(
                                Component.literal(" " + cooldownTranslated).withStyle(ChatFormatting.DARK_GREEN)
                        );
                    }
                }
            }

            switch (ShieldLibConfig.movement_tooltip_mode) {
                case DISABLED -> {
                }

                case NORMAL, COMPACT -> {
                    if(!stack.has(DataComponents.BLOCKS_ATTACKS)) return;

                    BlocksAttacks blocksAttacks = stack.get(DataComponents.BLOCKS_ATTACKS);

                    if(blocksAttacks != null) {
                        float movementMultiplier = (ShieldLib.getMovementWithModifiers(player, stack, blocksAttacks, new Vec2(1,1)).x / 5.0F) - 1.0F;

                        if(movementMultiplier == 0) {
                            break;
                        }

                        tooltip.add(Component.literal(""));
                        tooltip.add(Component.translatable("shieldlib.movement_tooltip.head").withStyle(ChatFormatting.GRAY));

                        String multiplierStr = String.valueOf(movementMultiplier * 100.0F).replaceAll("\\.0*$", "");

                        if(movementMultiplier > 0) {
                            String movement = ("+" + multiplierStr);
                            String movementTranslated = String.format(Component.translatable("shieldlib.movement_tooltip.body").getString(), movement);

                            tooltip.add(Component.literal(" " + movementTranslated).withStyle(ChatFormatting.BLUE));

                        } else if (movementMultiplier < 0) {

                            String movementTranslated = String.format(Component.translatable("shieldlib.movement_tooltip.body").getString(), multiplierStr);
                            tooltip.add(Component.literal(" " + movementTranslated).withStyle(ChatFormatting.RED));
                        }
                    }
                }
            }
        });

        if(IS_DEV) {
            ShieldLibTests.initClient();
        }
    }

    public static Material getShapedBannerMaterial(String shape, Holder<BannerPattern> bannerPattern) {
        return getShapedBannerMaterial(shape, bannerPattern.value().assetId());
    }

    public static Material getShapedBannerMaterial(String shape, ResourceLocation assetId) {
        Map<ResourceLocation, Material> map = SheetsAccessor.getShieldMaterials();

        if(!Objects.equals(shape, "tower")) {
            assetId = assetId.withPrefix(shape + "_");
        }

        MaterialMapper mapper = Sheets.SHIELD_MAPPER;
        Objects.requireNonNull(mapper);
        return map.computeIfAbsent(assetId, mapper::apply);
    }
}
