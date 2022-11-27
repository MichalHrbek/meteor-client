/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.NoFall;
import meteordevelopment.meteorclient.systems.modules.player.AntiHunger;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.Vec3d;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BotMovement extends Module {
    public BotMovement() {
        super(Categories.Misc, "bot-movement", "Makes the LiveOverflow server not kick you.");
    }
    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (event.packet instanceof PlayerMoveC2SPacket packet) {
            DecimalFormat df = new DecimalFormat("#.00");
            packet.x = Double.parseDouble(df.format(packet.x));
            packet.z = Double.parseDouble(df.format(packet.z));
            System.out.println(packet.x*1000);
            System.out.println((long)(packet.x*1000));
            System.out.println(packet.y*1000);
            System.out.println((long)(packet.y*1000));
            //MinecraftClient mc = MinecraftClient.getInstance();
            //mc.player.sendMessage(MutableText.of(new LiteralTextContent("x:" + packet.x + " y: " + packet.z)));
        } else if (event.packet instanceof VehicleMoveC2SPacket packet) {
            packet.x = round(packet.x, 2);
            packet.z = round(packet.z, 2);
        }
    }
    double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
