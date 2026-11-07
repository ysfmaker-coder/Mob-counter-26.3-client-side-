package com.example.client;


import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.EntityTypes;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.core.registries.BuiltInRegistries;


public class WolfCounterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, commandBuildContext) -> {
            dispatcher.register(ClientCommands.literal("count")
                    .then(ClientCommands.argument("Entitiy_name", StringArgumentType.word())
                            .suggests((context, builder) -> {
                                for (var id : BuiltInRegistries.ENTITY_TYPE.keySet()) {
                                    builder.suggest(id.getPath());
                                }
                                return builder.buildFuture();
                            })
                    .executes(context -> {
                        String SearchMob = StringArgumentType.getString(context, "Entitiy_name");
                        var mobId = Identifier.withDefaultNamespace(SearchMob);
                        var searchType = BuiltInRegistries.ENTITY_TYPE.getValue(mobId);
                var level = Minecraft.getInstance().level;
                var entities = level.entitiesForRendering();
                int counter = 0;

                for (var entity : entities) {
                    if (entity.getType() == searchType) {
                    counter++;
                    }
                }

                context.getSource().sendFeedback(Component.literal( SearchMob + "s: " + counter));
                return 1;
            })));
        });

    }
}