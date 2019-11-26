package me.ironapollo.implementations.v1_14_R1;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

import me.ironapollo.implementations.v1_14_R1.biomes.IronBiomeBase;
import net.minecraft.server.v1_14_R1.BiomeBase;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.ChunkGenerator;
import net.minecraft.server.v1_14_R1.CrashReport;
import net.minecraft.server.v1_14_R1.GeneratorAccess;
import net.minecraft.server.v1_14_R1.GeneratorSettingsDefault;
import net.minecraft.server.v1_14_R1.HeightMap.Type;
import net.minecraft.server.v1_14_R1.IChunkAccess;
import net.minecraft.server.v1_14_R1.IRegistry;
import net.minecraft.server.v1_14_R1.RegionLimitedWorldAccess;
import net.minecraft.server.v1_14_R1.ReportedException;
import net.minecraft.server.v1_14_R1.SeededRandom;
import net.minecraft.server.v1_14_R1.WorldChunkManager;
import net.minecraft.server.v1_14_R1.WorldGenStage;

public class IronChunkGenerator<C extends GeneratorSettingsDefault> extends ChunkGenerator<C> {
	
	private final ChunkGenerator<C> parent;
	private static Field generatorAccessField, worldChunkManagerField, settingsField;
	private static GeneratorAccess generatorAccess;
	private static WorldChunkManager worldChunkManager;
	
	static {
		try {
			generatorAccessField = ChunkGenerator.class.getDeclaredField("a");
			generatorAccessField.setAccessible(true);
			worldChunkManagerField = ChunkGenerator.class.getDeclaredField("c");
			worldChunkManagerField.setAccessible(true);
			settingsField = ChunkGenerator.class.getDeclaredField("settings");
			settingsField.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public IronChunkGenerator(@Nonnull ChunkGenerator<C> parent) throws IllegalArgumentException, IllegalAccessException {
		super(generatorAccess = (GeneratorAccess) generatorAccessField.get(parent), worldChunkManager = (WorldChunkManager)worldChunkManagerField.get(parent), (C)settingsField.get(parent));
		this.parent = parent;
	}
	
	@Override
	public BiomeBase getDecoratingBiome(RegionLimitedWorldAccess access, BlockPosition pos) {
		return IronBiomeBase.wrap(this.c.getBiome(pos));
	}
	
	@Override
	public void addDecorations(RegionLimitedWorldAccess access) {
	     int i = access.a();
	        int j = access.b();
	        int k = i * 16;
	        int l = j * 16;
	        BlockPosition blockposition = new BlockPosition(k, 0, l);
	        BiomeBase biomebase = this.getDecoratingBiome(access, blockposition.b(8, 8, 8));
	        SeededRandom seededrandom = new SeededRandom();
	        long i1 = seededrandom.a(access.getSeed(), k, l);
	        WorldGenStage.Decoration[] aworldgenstage_decoration = WorldGenStage.Decoration.values();
	        int j1 = aworldgenstage_decoration.length;

	        for (int k1 = 0; k1 < j1; ++k1) {
	            WorldGenStage.Decoration worldgenstage_decoration = aworldgenstage_decoration[k1];

	            try {
	                biomebase.a(worldgenstage_decoration, this, access, i1, seededrandom, blockposition);
	            } catch (Exception exception) {
	                CrashReport crashreport = CrashReport.a(exception, "Biome decoration");

	                crashreport.a("Generation").a("CenterX", (Object) i).a("CenterZ", (Object) j).a("Step", (Object) worldgenstage_decoration).a("Seed", (Object) i1).a("Biome", (Object) IRegistry.BIOME.getKey(biomebase));
	                throw new ReportedException(crashreport);
	            }
	        }
	}

	@Override
	public void buildBase(IChunkAccess arg0) {
		parent.buildBase(arg0);
	}

	@Override
	public void buildNoise(GeneratorAccess arg0, IChunkAccess arg1) {
		parent.buildNoise(arg0, arg1);
	}

	@Override
	public int getBaseHeight(int arg0, int arg1, Type arg2) {
		return parent.getBaseHeight(arg0, arg1, arg2);
	}

	@Override
	public int getSpawnHeight() {
		return parent.getSpawnHeight();
	}

}
