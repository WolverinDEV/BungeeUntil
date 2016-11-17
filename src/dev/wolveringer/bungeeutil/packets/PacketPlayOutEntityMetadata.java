package dev.wolveringer.bungeeutil.packets;

import dev.wolveringer.bungeeutil.entity.datawatcher.DataWatcher;
import dev.wolveringer.bungeeutil.packetlib.reader.PacketDataSerializer;
import dev.wolveringer.bungeeutil.packets.types.PacketPlayOut;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketPlayOutEntityMetadata extends Packet implements PacketPlayOut {
	private int id;
	private DataWatcher meta;

	public void read(PacketDataSerializer packetdataserializer) {
		this.id = packetdataserializer.readInt();
		this.meta = DataWatcher.createDataWatcher(getBigVersion(),packetdataserializer);
	}

	public void write(PacketDataSerializer packetdataserializer) {
		if(getVersion().getVersion() < 16){
			packetdataserializer.writeInt(this.id);
		}else{
			packetdataserializer.writeVarInt(this.id);
		}
		meta.write(packetdataserializer);
	}

	@Override
	public String toString() {
		return "PacketPlayOutEntityMetadata@" + System.identityHashCode(this) + "[id=" + id + ", meta=" + meta + "]";
	}
	
}
