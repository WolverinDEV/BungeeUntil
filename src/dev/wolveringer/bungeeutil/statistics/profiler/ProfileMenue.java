package dev.wolveringer.bungeeutil.statistics.profiler;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.BungeeCord;
import dev.wolveringer.bungeeutil.BungeeUtil;
import dev.wolveringer.bungeeutil.chat.ChatColorUtils;
import dev.wolveringer.bungeeutil.inventory.Inventory;
import dev.wolveringer.bungeeutil.inventory.ScrolingInventory;
import dev.wolveringer.bungeeutil.item.ItemStack;
import dev.wolveringer.bungeeutil.item.Material;
import dev.wolveringer.bungeeutil.item.MultiClickItemStack;
import dev.wolveringer.bungeeutil.plugin.Main;

public class ProfileMenue {
	private static ProfileMenue menue = new ProfileMenue();
	static{
		menue = new ProfileMenue();
	}

	public static ProfileMenue getProfilerMenue() {
		return menue;
	}

	private ScrolingInventory inv = new ScrolingInventory(4, ""+ChatColorUtils.COLOR_CHAR+"aTimings");
	private Inventory inv_disabled = new Inventory(9, ""+ChatColorUtils.COLOR_CHAR+"cTimings Disabled");

	public ProfileMenue() {
		BungeeCord.getInstance().getScheduler().schedule(BungeeUtil.getPluginInstance(), new Runnable() {
			@Override
			public void run() {
				rebuild();
			}
		}, 1, 5, TimeUnit.SECONDS);
		ItemStack is = new MultiClickItemStack(Material.BARRIER);
		is.getItemMeta().setDisplayName(""+ChatColorUtils.COLOR_CHAR+"cTimings are "+ChatColorUtils.COLOR_CHAR+"c"+ChatColorUtils.COLOR_CHAR+"nDisabled");
		inv_disabled.setItem(4, is);
	}

	protected void rebuild() {
		if(!Profiler.isEnabled())
			return;
		inv.disableUpdate();
		inv.clear();
		for(Profiler p : Profiler.getProfilers()){
			p.updateInventory();
			inv.addItem(build(p));
		}
		inv.enableUpdate();
	}

	private ItemStack build(final Profiler profile) {
		ItemStack is = new ItemStack(Material.WATCH) {
			@Override
			public void click(Click p) {
				p.getPlayer().openInventory(profile.getInventory());
			}
		};
		is.getItemMeta().setDisplayName(""+ChatColorUtils.COLOR_CHAR+"bProfiler: "+ChatColorUtils.COLOR_CHAR+"5" + profile.getName());
		return is;
	}

	public Inventory getMenue() {
		if(Profiler.isEnabled())
			return inv;
		else
			return inv_disabled;
	}
}
