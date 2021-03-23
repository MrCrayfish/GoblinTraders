package com.mrcrayfish.goblintraders.mixin;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import net.minecraft.command.Commands;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.world.chunk.listener.IChunkStatusListenerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.net.Proxy;

/**
 * Author: MrCrayfish
 */
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin
{
    @Shadow
    @Final
    private IReloadableResourceManager resourceManager;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void constructorTail(File file, Proxy proxy, DataFixer dataFixerIn, Commands commands, YggdrasilAuthenticationService authService, MinecraftSessionService sessionService, GameProfileRepository repository, PlayerProfileCache profileCache, IChunkStatusListenerFactory listenerFactory, String s, CallbackInfo ci)
    {
        this.resourceManager.addReloadListener(TradeManager.instance());
    }
}
