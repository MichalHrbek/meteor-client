/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.world.GameMode;

public class NoDemo extends Module {
    public NoDemo() {
        super(Categories.Misc, "no-demo", "Disables server sending demo packets.");
    }
    @EventHandler
    private void onReadPacket(PacketEvent.Receive event) {
        if (event.packet instanceof GameStateChangeS2CPacket packet) {
            if (packet.getReason() == GameStateChangeS2CPacket.DEMO_MESSAGE_SHOWN || packet.getReason() == GameStateChangeS2CPacket.GAME_WON) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.world.getWorldBorder().getCenterX() != 0 || mc.world.getWorldBorder().getCenterZ() != 0 || mc.interactionManager.getCurrentGameMode() != GameMode.SURVIVAL) {
            mc.world.getWorldBorder().setSize(5.9999968E7);
            mc.world.getWorldBorder().setCenter(0, 0);
            mc.interactionManager.setGameMode(GameMode.SURVIVAL);
        }
    }

}
