package it.scopped.noplobby.utilities.schedule;

import it.scopped.noplobby.NopLobby;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public final class Schedule {

    private static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();

    public static BukkitTask later(boolean async, long later, Runnable runnable) {
        return
                async
                        ? SCHEDULER.runTaskLaterAsynchronously(NopLobby.getInstance(), runnable, later)
                        : SCHEDULER.runTaskLater(NopLobby.getInstance(), runnable, later);
    }

    public static BukkitTask timer(boolean async, long timer, Runnable runnable) {
        return
                async
                        ? SCHEDULER.runTaskTimerAsynchronously(NopLobby.getInstance(), runnable, timer, timer)
                        : SCHEDULER.runTaskTimer(NopLobby.getInstance(), runnable, timer, timer);
    }

    public static BukkitTask submit(boolean async, Runnable runnable) {
        return
                async
                        ? SCHEDULER.runTaskAsynchronously(NopLobby.getInstance(), runnable)
                        : SCHEDULER.runTask(NopLobby.getInstance(), runnable);
    }

    public static BukkitTask later(boolean async, long later, BukkitRunnable runnable) {
        return
                async
                        ? runnable.runTaskLaterAsynchronously(NopLobby.getInstance(), later)
                        : runnable.runTaskLater(NopLobby.getInstance(), later);
    }

    public static BukkitTask timer(boolean async, long timer, BukkitRunnable runnable) {
        return
                async
                        ? runnable.runTaskTimerAsynchronously(NopLobby.getInstance(), timer, timer)
                        : runnable.runTaskTimer(NopLobby.getInstance(), timer, timer);
    }

    public static BukkitTask submit(boolean async, BukkitRunnable runnable) {
        return
                async
                        ? runnable.runTaskAsynchronously(NopLobby.getInstance())
                        : runnable.runTask(NopLobby.getInstance());
    }
}