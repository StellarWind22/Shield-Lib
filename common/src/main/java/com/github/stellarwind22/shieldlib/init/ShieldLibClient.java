package com.github.stellarwind22.shieldlib.init;

import com.github.stellarwind22.shieldlib.lib.config.ShieldLibConfig;
import com.github.stellarwind22.shieldlib.lib.event.ShieldTooltipEvent;
import com.github.stellarwind22.shieldlib.lib.model.BucklerShieldLibModel;
import com.github.stellarwind22.shieldlib.lib.object.ShieldLibTags;
import com.github.stellarwind22.shieldlib.lib.render.BucklerShieldModelRenderer;
import com.github.stellarwind22.shieldlib.lib.render.VanillaShieldModelRenderer;
import com.github.stellarwind22.shieldlib.mixin.SheetsAccessor;
import com.github.stellarwind22.shieldlib.mixin.SpecialModelRenderersAccessor;
import com.mojang.serialization.MapCodec;
import dev.architectury.event.EventResult;
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

import java.util.Map;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ShieldLibClient {

    public static final ResourceLocation VANILLA_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "vanilla_shield");
    public static final ResourceLocation BUCKLER_SHIELD_MODEL_TYPE = ResourceLocation.fromNamespaceAndPath(ShieldLib.MOD_ID, "buckler_shield");

    public static final ResourceLocation SHIELD_ATLAS_LOCATION = ResourceLocation.withDefaultNamespace("textures/atlas/shield_patterns.png");

    public static final ExtraCodecs.LateBoundIdMapper<ResourceLocation, MapCodec<? extends SpecialModelRenderer.Unbaked>> ID_MAPPER = SpecialModelRenderersAccessor.getIDMapper();

    public static boolean IS_DEV;

    public static void init(boolean isDev) {

        IS_DEV = isDev;

        ID_MAPPER.put(
                VANILLA_SHIELD_MODEL_TYPE,
                VanillaShieldModelRenderer.Unbaked.CODEC
        );

        ID_MAPPER.put(
                BUCKLER_SHIELD_MODEL_TYPE,
                BucklerShieldModelRenderer.Unbaked.CODEC
        );

        ShieldTooltipEvent.EVENT.register((player,stack, context, flag, tooltip) -> {
            if(stack.get(DataComponents.BLOCKS_ATTACKS) == null || stack.is(ShieldLibTags.NO_TOOLTIP)) return EventResult.pass();

            switch (ShieldLibConfig.cooldown_tooltip_mode) {

                case DISABLED -> {
                    return EventResult.pass();
                }

                case NORMAL, ADVANCED -> {
                    if(!stack.has(DataComponents.BLOCKS_ATTACKS)) return EventResult.pass();

                    BlocksAttacks blocksAttacks = stack.get(DataComponents.BLOCKS_ATTACKS);

                    if(blocksAttacks != null) {
                        double cooldownTicks = ShieldLib.getCooldownTicksWithModifiers(player, stack, blocksAttacks);

                        tooltip.add(Component.literal(""));
                        tooltip.add(Component.translatable("shieldlib.shield_tooltip.start")
                                .append(Component.literal(":"))
                                .withStyle(ChatFormatting.GRAY));

                        String cooldown = String.valueOf((Double) (cooldownTicks / 20.0D));
                        char[] splitCooldown = cooldown.toCharArray();
                        if (splitCooldown.length >= 3) {
                            if(splitCooldown[2] == '0') {
                                if(!(splitCooldown.length >= 4)) {
                                    cooldown = String.valueOf(splitCooldown[0]);
                                }
                            }
                        }

                        tooltip.add(Component.literal(" " + cooldown)
                                .withStyle(ChatFormatting.DARK_GREEN)
                                .append(Component.translatable("shieldlib.shield_tooltip.unit"))
                                .append(" ")
                                .append(Component.translatable("shieldlib.shield_tooltip.end")));
                    }
                }

            }
            return EventResult.pass();
        });

        EntityModelLayerRegistry.register(BucklerShieldLibModel.LOCATION, BucklerShieldLibModel::createLayer);
    }

    public static Material getShapedBannerMaterial(String shape, Holder<BannerPattern> bannerPattern) {
        return getShapedBannerMaterial(shape, bannerPattern.value().assetId());
    }

    public static Material getShapedBannerMaterial(String shape, ResourceLocation assetId) {
        Map<ResourceLocation, Material> map = SheetsAccessor.getShieldMaterials();

        if(!Objects.equals(shape, "vanilla")) {
            assetId = assetId.withPrefix(shape + "_");
        }

        MaterialMapper mapper = Sheets.SHIELD_MAPPER;
        Objects.requireNonNull(mapper);
        return map.computeIfAbsent(assetId, mapper::apply);
    }
}
