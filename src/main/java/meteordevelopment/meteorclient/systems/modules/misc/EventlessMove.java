/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;
import meteordevelopment.meteorclient.events.meteor.KeyEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.render.Freecam;
import meteordevelopment.meteorclient.utils.misc.input.Input;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.lwjgl.glfw.GLFW;

public class EventlessMove extends Module {
    public EventlessMove() {
        super(Categories.Misc, "eventless-move", "Bypasses WorldGuard.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        mc.player.setVelocity(0, 0, 0);
    }

    @EventHandler
    public void onKey(KeyEvent event) {
        if (Modules.get().get(Freecam.class).isActive()) {
            if (Input.isKeyPressed(GLFW.GLFW_KEY_F3)) return;

            boolean cancel = true;
            double x = 0;
            double y = 0;
            double z = 0;

            if (mc.options.forwardKey.matchesKey(event.key, 0)) {
                x = 0.062;
            }
            else if (mc.options.backKey.matchesKey(event.key, 0)) {
                x = -0.062;
            }
            else if (mc.options.rightKey.matchesKey(event.key, 0)) {
                z = 0.062;
            }
            else if (mc.options.leftKey.matchesKey(event.key, 0)) {
                z = -0.062;
            }
            else if (mc.options.jumpKey.matchesKey(event.key, 0)) {
                y = 0.062;
            }
            else if (mc.options.sneakKey.matchesKey(event.key, 0)) {
                y = -0.062;
            }
            else {
                cancel = false;
            }

            if (cancel){
                event.cancel();
                mc.player.setPos(mc.player.getX() + x, mc.player.getY() + y, mc.player.getZ() + z);
                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), true));
                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + 1000, mc.player.getY() + 1000, mc.player.getZ() + 1000, true));
            }
        }
    }
}
