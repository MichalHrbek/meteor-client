/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.world.GameMode;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class FixLOCommand extends Command {
    public FixLOCommand() {
        super("fix-lo", "Fixes LiveOverflowServer.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            mc.world.getWorldBorder().setSize(5.9999968E7);
            mc.world.getWorldBorder().setCenter(0, 0);
            mc.interactionManager.setGameMode(GameMode.SURVIVAL);
            return SINGLE_SUCCESS;
        });
    }
}
