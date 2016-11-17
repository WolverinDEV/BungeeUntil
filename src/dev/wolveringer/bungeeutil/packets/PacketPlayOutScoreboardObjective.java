package dev.wolveringer.bungeeutil.packets;

import dev.wolveringer.bungeeutil.packetlib.reader.PacketDataSerializer;
import dev.wolveringer.bungeeutil.packets.types.PacketPlayOut;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketPlayOutScoreboardObjective extends Packet implements PacketPlayOut {
	
	public static enum Action {
		CREATE(0), REMOVE(1), UPDATE(2);
		
		int i;
		
		private Action(int i) {
			this.i = i;
		}
		
		public static Action fromInt(int i) {
			for (Action a : values())
				if (a.i == i) return a;
			return null;
		}
	}
	
	public static enum Type {
		INTEGER("integer"), HEARTS("hearts");
		
		private String s;
		
		private Type(String s) {
			this.s = s;
		}
		
		public static Type fromString(String s) {
			for (Type t : values())
				if (t.s.equalsIgnoreCase(s)) return t;
			return null;
		}
		
		public String getIdentifire() {
			return s;
		}
	}
	
	private String scorebordName;
	private Action action;
	private String displayName = "";
	private Type type = Type.INTEGER;
	
	@Override
	public void read(PacketDataSerializer s) {
		scorebordName = s.readString(-1);
		action = Action.fromInt(s.readByte());
		if (action.i != 1) {
			displayName = s.readString(-1);
			type = Type.fromString(s.readString(-1));
		}
	}
	
	@Override
	public void write(PacketDataSerializer s) {
		s.writeString(scorebordName);
		s.writeByte(action.i);
		if (action.i != 1) {
			s.writeString(displayName);
			s.writeString(type.getIdentifire());
		}
	}
	
	public String getScorebordName() {
		return scorebordName;
	}
	
	public void setScorebordName(String scorebordName) {
		this.scorebordName = scorebordName;
	}
	
	public Action getAction() {
		return action;
	}
	
	public void setAction(Action a) {
		this.action = a;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
}
