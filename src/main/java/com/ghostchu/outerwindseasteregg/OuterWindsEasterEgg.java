package com.ghostchu.outerwindseasteregg;

import com.google.common.collect.EvictingQueue;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class OuterWindsEasterEgg extends JavaPlugin implements Listener {
    private static final String noteSerial = "CGBGCBAGABGEGBBCBAGABDB";
    private final EvictingQueue<Character> notes = EvictingQueue.create(noteSerial.toCharArray().length);

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler(ignoreCancelled = true)
    public void onPiano(NotePlayEvent event) {
        if (event.getInstrument() != Instrument.GUITAR)
            return;
        notes.add(event.getNote().getTone().name().toCharArray()[0]);
        StringBuilder builder = new StringBuilder();
        notes.forEach(builder::append);
        if (builder.toString().equals(noteSerial)) {
            notes.clear();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "toggletimerain false");
            Bukkit.broadcastMessage("一段奇妙的旋律与 Xen 世界产生了共鸣，时间雨停止了！");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
}
