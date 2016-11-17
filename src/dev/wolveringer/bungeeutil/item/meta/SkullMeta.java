package dev.wolveringer.bungeeutil.item.meta;

import java.util.UUID;

import net.md_5.bungee.BungeeCord;
import dev.wolveringer.bungeeutil.BungeeUtil;
import dev.wolveringer.bungeeutil.OperationCalback;
import dev.wolveringer.bungeeutil.item.Item;
import dev.wolveringer.nbt.NBTTagCompound;
import dev.wolveringer.bungeeutil.plugin.Main;
import dev.wolveringer.bungeeutil.profile.GameProfile;
import dev.wolveringer.bungeeutil.profile.GameProfileSerializer;
import dev.wolveringer.bungeeutil.profile.Skin;
import dev.wolveringer.bungeeutil.profile.SkinFactory;
import dev.wolveringer.bungeeutil.profile.UUIDFetcher;

public class SkullMeta extends CraftItemMeta {

	static{
		MetaFactory.ItemMetaProxy.addWhiteList(SkullMeta.class, "setSkin");
	}

	GameProfile p = new GameProfile(UUID.randomUUID(), "");
	UUID owner;

	public SkullMeta(Item i) {
		super(i);
		if(getTag().hasKey("SkullOwner"))
			p = GameProfileSerializer.deserialize(getTag().getCompound("SkullOwner"));
	}

	@SuppressWarnings("unchecked")
	public void setSkin(UUID owner) {
		this.owner = owner;
		if(p == null)
			p = new GameProfile(owner, "");
		p.setId(owner);
		SkinFactory.getSkin(owner, new OperationCalback<Skin>() {
			public void done(Skin response) {
				setSkin(response);
			};
		});
		build();
	}

	public void setSkin(final String owner) {
		BungeeCord.getInstance().getScheduler().runAsync(BungeeUtil.getPluginInstance(), new Runnable() {
			@Override
			public void run() {
				try{
					setSkin(UUIDFetcher.getUUIDOf(owner));
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	public void setSkin(Skin s) {
		s.applay(p);
		p.setName(p.getName().equalsIgnoreCase("") ? s.getProfileName() : p.getName());
		build();
	}

	public void setSkullOwner(String name) {
		if(p == null)
			p = new GameProfile(UUID.randomUUID(), name);
		p.setName(name);
		build();
	}

	public GameProfile getProfile() {
		return p;
	}

	public void setProfile(GameProfile p) {
		this.p = p;
		build();
	}

	@Override
	protected void build() {
		if(p != null)
			getTag().set("SkullOwner", GameProfileSerializer.serialize(new NBTTagCompound(), p));
		fireUpdate();
	}

	@Override
	public String toString() {
		return "SkullMeta@" + System.identityHashCode(this) + "[p=" + p + ", owner=" + owner + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		SkullMeta other = (SkullMeta) obj;
		if(owner == null){
			if(other.owner != null)
				return false;
		}else if(!owner.equals(other.owner))
			return false;
		if(p == null){
			if(other.p != null)
				return false;
		}else if(!p.equals(other.p))
			return false;
		return super.equals(obj);
	}
}
