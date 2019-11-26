package me.ironapollo.implementations.v1_14_R1.biomes;

import javax.annotation.Nonnull;

import net.minecraft.server.v1_14_R1.BiomeBase;
import net.minecraft.server.v1_14_R1.Biomes;
import net.minecraft.server.v1_14_R1.IRegistry;

public enum IronBiomeTypes {
	
	JUNGLE(21, IronBiomeBase.wrap(Biomes.JUNGLE)),
	JUNGLE_HILLS(22, IronBiomeBase.wrap(Biomes.JUNGLE_HILLS)),
	JUNGLE_EDGE(23, IronBiomeBase.wrap(Biomes.JUNGLE_EDGE));
	
	private int id;
	private IronBiomeBase biomeBase;
	
	private IronBiomeTypes(int id, IronBiomeBase biomeBase) {
		this.id = id;
		this.biomeBase = biomeBase;
	}
	
	public int getId() {
		return this.id;
	}
	
	public IronBiomeBase getBiomeBase() {
		return this.biomeBase;
	}
	
	@Nonnull
	public IronBiomeBase convert(BiomeBase base) {
		if(IronBiomeCache.cachedBiomeConversions.containsKey(base)) {
			return IronBiomeCache.cachedBiomeConversions.get(base);
		}
		final int baseId = IRegistry.BIOME.a(base);
		System.out.println(base + " :: " + baseId);
		for(IronBiomeTypes type : IronBiomeTypes.values()) {
			if(type.getId() == baseId) {
				IronBiomeCache.cachedBiomeConversions.put(base, type.getBiomeBase());
				return type.getBiomeBase();
			}
		}
		IronBiomeBase iBase = IronBiomeBase.wrap(base);
		IronBiomeCache.cachedBiomeConversions.put(base, iBase);
		return iBase;
	}
}
