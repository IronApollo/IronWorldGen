package me.ironapollo.implementations.v1_14_R1.biomes;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import me.ironapollo.implementations.v1_14_R1.worldgen.IronWorldGenJungleTrees;
import net.minecraft.server.v1_14_R1.BiomeBase;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.Blocks;
import net.minecraft.server.v1_14_R1.ChunkGenerator;
import net.minecraft.server.v1_14_R1.CrashReport;
import net.minecraft.server.v1_14_R1.CrashReportSystemDetails;
import net.minecraft.server.v1_14_R1.GeneratorAccess;
import net.minecraft.server.v1_14_R1.GeneratorSettingsDefault;
import net.minecraft.server.v1_14_R1.IRegistry;
import net.minecraft.server.v1_14_R1.ReportedException;
import net.minecraft.server.v1_14_R1.SeededRandom;
import net.minecraft.server.v1_14_R1.StructureGenerator;
import net.minecraft.server.v1_14_R1.WorldGenCarverConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenCarverWrapper;
import net.minecraft.server.v1_14_R1.WorldGenDecorator;
import net.minecraft.server.v1_14_R1.WorldGenDecoratorFrequencyExtraChanceConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureCompositeConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureConfigured;
import net.minecraft.server.v1_14_R1.WorldGenFeatureEmptyConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenFeatureRandomChoiceConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenStage;
import net.minecraft.server.v1_14_R1.WorldGenStage.Decoration;
import net.minecraft.server.v1_14_R1.WorldGenSurface;
import net.minecraft.server.v1_14_R1.WorldGenSurfaceComposite;
import net.minecraft.server.v1_14_R1.WorldGenSurfaceConfiguration;
import net.minecraft.server.v1_14_R1.WorldGenerator;

public class IronBiomeBase extends BiomeBase{
	
	@Nullable private BiomeBase parent = null;
	
	private Map<WorldGenStage.Features, List<WorldGenCarverWrapper<?>>> carvers;
	private Map<WorldGenStage.Decoration, List<WorldGenFeatureConfigured<?>>> features;
	private List<WorldGenFeatureConfigured<?>> flowerFeatures;
	private Map<StructureGenerator<?>, WorldGenFeatureConfiguration> validFeatureStarts;	
		
	private final WorldGenerator<WorldGenFeatureEmptyConfiguration> ironTree = new IronWorldGenJungleTrees(WorldGenFeatureEmptyConfiguration::a, false, 4, Blocks.JUNGLE_LOG.getBlockData(), Blocks.JUNGLE_LEAVES.getBlockData());

	private final List<WorldGenerator<?>> blacklistedFeatures = Arrays.asList(
			WorldGenerator.PUMPKIN, 
			WorldGenerator.PUMPKIN_PILE,
			WorldGenerator.MELON,
			WorldGenerator.MELON_PILE,
			WorldGenerator.REED,
			WorldGenerator.SWEET_BERRY_BUSH);

	protected IronBiomeBase(BiomeBuilder builder) {
		super(builder);
		this.parent = builder.parentBase;
	}
	
	private IronBiomeBase(BiomeBuilder builder, String descriptionId, Map<WorldGenStage.Features, List<WorldGenCarverWrapper<?>>> carvers, Map<WorldGenStage.Decoration, List<WorldGenFeatureConfigured<?>>> features, List<WorldGenFeatureConfigured<?>> flowerFeatures, Map<StructureGenerator<?>, WorldGenFeatureConfiguration> validFeatureStarts) {
		this(builder);
		this.f = descriptionId;
		this.carvers = carvers;
		this.features = features;
		this.flowerFeatures = flowerFeatures;
		this.validFeatureStarts = validFeatureStarts;
	}
	
	@SuppressWarnings("unchecked")
	public static IronBiomeBase wrap(net.minecraft.server.v1_14_R1.BiomeBase base) {
		if(IronBiomeCache.cachedBiomeConversions.containsKey(base)) {
			return IronBiomeCache.cachedBiomeConversions.get(base);
		}
//		this.f = base.j(); //descriptionId
//		this.n = base.p(); //surfaceBuilder
//		this.p = base.b(); //precipitation
//		this.o = base.o(); //biomeCategory
//		this.g = base.g(); //depth
//		this.h = base.k(); //scale
//		this.i = base.getTemperature(); //temperature
//		this.j = base.getHumidity(); //downfall
//		this.k = base.m(); //waterColor
//		this.l = base.n(); //waterFogColor
//		this.m = base.r(); //parent
		
		Field field;
		Object carvers = null, features = null, flowerFeatures = null, validFeatureStarts = null;
		try {
			field = BiomeBase.class.getDeclaredField("q");
			field.setAccessible(true);
			carvers = field.get(base);
			field = BiomeBase.class.getDeclaredField("r");
			field.setAccessible(true);
			features = field.get(base);
			field = BiomeBase.class.getDeclaredField("s");
			field.setAccessible(true);
			flowerFeatures = field.get(base);
			field = BiomeBase.class.getDeclaredField("t");
			field.setAccessible(true);
			validFeatureStarts = field.get(base);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		BiomeBuilder builder = new BiomeBuilder()
				.surfaceBuilder(base.p())
				.precipitation(base.b())
				.biomeCategory(base.o())
				.depth(base.g())
				.scale(base.k())
				.temperature(base.getTemperature())
				.downfall(base.getHumidity())
				.waterColor(base.m())
				.waterFogColor(base.n())
				.parent(base.r())
				.parentBase(base);

		IronBiomeBase iBase = new IronBiomeBase(builder, base.j() /*descriptionId*/, (Map<WorldGenStage.Features, List<WorldGenCarverWrapper<?>>>) carvers, (Map<WorldGenStage.Decoration, List<WorldGenFeatureConfigured<?>>>) features, (List<WorldGenFeatureConfigured<?>>) flowerFeatures, (Map<StructureGenerator<?>, WorldGenFeatureConfiguration>) validFeatureStarts);
		iBase.filterFeatures();
		IronBiomeCache.cachedBiomeConversions.put(base, iBase);
		return iBase;
	}
		
	public void addFeature(WorldGenStage.Decoration decoration, WorldGenFeatureConfigured<?> feature) { this.a(decoration, feature); }
	@Override
	public void a(WorldGenStage.Decoration decoration, WorldGenFeatureConfigured<?> feature) {
        if (feature.a == WorldGenerator.DECORATED_FLOWER) {
            this.getFlowerFeatures().add(feature);
        }else if(this.blacklistedFeatures.contains(feature.a)) {
        	return;
        }else if(feature.a == WorldGenerator.JUNGLE_TREE) {
        	this.addJungleTree();
        	return;
        }
        

        this.getFeatures().get(decoration).add(feature);
    }
	
	private Map<WorldGenStage.Features, List<WorldGenCarverWrapper<?>>> getCarvers(){
		return this.carvers;
	}
	
	private Map<WorldGenStage.Decoration, List<WorldGenFeatureConfigured<?>>> getFeatures(){
		return this.features;
	}
	
	private List<WorldGenFeatureConfigured<?>> getFlowerFeatures(){
		return this.flowerFeatures;
	}
	
	private Map<StructureGenerator<?>, WorldGenFeatureConfiguration> getValidFeatureStarts(){
		return this.validFeatureStarts;
	}
	
	/**
	 * Filters the features of the IronBiomeBase to remove blacklisted or other unwanted
	 * NMS features and, if necessary, replace them with overridden versions.
	 */
	protected void filterFeatures() {
		Map<WorldGenStage.Decoration, List<WorldGenFeatureConfigured<?>>> copy = new ConcurrentHashMap<WorldGenStage.Decoration, List<WorldGenFeatureConfigured<?>>>(this.getFeatures());
		boolean addTree = false;
		Iterator<Map.Entry<Decoration, List<WorldGenFeatureConfigured<?>>>> iterator = copy.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<Decoration, List<WorldGenFeatureConfigured<?>>> entry = iterator.next();
			Iterator<WorldGenFeatureConfigured<?>> iterator2 = entry.getValue().iterator();
			while(iterator2.hasNext()) {
				WorldGenFeatureCompositeConfiguration next = ((WorldGenFeatureCompositeConfiguration)iterator2.next().b);
				WorldGenerator<?> feature = next.a.a;
				if(this.blacklistedFeatures.contains(feature)) {
					iterator2.remove();
				}else if(feature == WorldGenerator.RANDOM_SELECTOR) {
					feature = ((WorldGenFeatureRandomChoiceConfiguration)next.a.b).b.a;
					if(feature == WorldGenerator.JUNGLE_TREE) {
						iterator2.remove();
						addTree = true;
					}
				}
			}
		}
		
		this.features = copy;
		
		if(addTree) {
			this.addJungleTree();
		}
	}
	
	/**
	 * Adds the overridden jungle tree feature to the IronBiomeBase's features.
	 * This jungle tree feature is designed to generate without cocoa beans.
	 */
	protected void addJungleTree() {
		this.addFeature(Decoration.VEGETAL_DECORATION, BiomeBase.a(WorldGenerator.RANDOM_SELECTOR, new WorldGenFeatureRandomChoiceConfiguration(new WorldGenerator[]{WorldGenerator.FANCY_TREE, WorldGenerator.JUNGLE_GROUND_BUSH, WorldGenerator.MEGA_JUNGLE_TREE}, new WorldGenFeatureConfiguration[]{WorldGenFeatureConfiguration.e, WorldGenFeatureConfiguration.e, WorldGenFeatureConfiguration.e}, new float[]{0.1F, 0.5F, 0.33333334F}, ironTree, WorldGenFeatureConfiguration.e), WorldGenDecorator.m, new WorldGenDecoratorFrequencyExtraChanceConfiguration(50, 0.1F, 1)));
	}
	
	public static class BiomeBuilder extends a{
		
		public BiomeBase parentBase;
		
		public BiomeBuilder() {}
		
	      public <SC extends WorldGenSurfaceConfiguration> BiomeBuilder surfaceBuilder(WorldGenSurface<SC> worldgensurface, SC sc) {
	            this.a(worldgensurface, sc);
	            return this;
	        }

	        public BiomeBuilder surfaceBuilder(WorldGenSurfaceComposite<?> worldgensurfacecomposite) {
	            this.a(worldgensurfacecomposite);
	            return this;
	        }

	        public BiomeBuilder precipitation(BiomeBase.Precipitation biomebase_precipitation) {
	            this.a(biomebase_precipitation);
	            return this;
	        }

	        public BiomeBuilder biomeCategory(BiomeBase.Geography biomebase_geography) {
	            this.a(biomebase_geography);
	            return this;
	        }

	        public BiomeBuilder depth(float f) {
		        this.a(f);
		        return this;
	        }

	        public BiomeBuilder scale(float f) {
	            this.b(f);
	            return this;
	        }

	        public BiomeBuilder temperature(float f) {
	            this.c(f);
	            return this;
	        }

	        public BiomeBuilder downfall(float f) {
	            this.d(f);
	            return this;
	        }

	        public BiomeBuilder waterColor(int i) {
	            this.a(i);
	            return this;
	        }

	        public BiomeBuilder waterFogColor(int i) {
	            this.b(i);
	            return this;
	        }

	        public BiomeBuilder parent(@Nullable String s) {
	            this.a(s);
	            return this;
	        }
	        
	        public BiomeBuilder parentBase(@Nullable BiomeBase parentBase) {
	        	this.parentBase = parentBase;
	        	return this;
	        }
	
	}
	
	//QRST OVERRIDES
	@Override
    public <C extends WorldGenCarverConfiguration> void a(WorldGenStage.Features worldgenstage_features, WorldGenCarverWrapper<C> worldgencarverwrapper) {
        this.getCarvers().computeIfAbsent(worldgenstage_features, (worldgenstage_features1) -> {
            return Lists.newArrayList();
        }).add(worldgencarverwrapper);
    }

	@Override
    public List<WorldGenCarverWrapper<?>> a(WorldGenStage.Features worldgenstage_features) {
        return this.getCarvers().computeIfAbsent(worldgenstage_features, (worldgenstage_features1) -> {
            return Lists.newArrayList();
        });
    }
	
    public List<WorldGenFeatureConfigured<?>> a(WorldGenStage.Decoration worldgenstage_decoration) {
        return this.getFeatures().get(worldgenstage_decoration);
    }

    public void a(WorldGenStage.Decoration worldgenstage_decoration, ChunkGenerator<? extends GeneratorSettingsDefault> chunkgenerator, GeneratorAccess generatoraccess, long i, SeededRandom seededrandom, BlockPosition blockposition) {
        int j = 0;

        for (Iterator<WorldGenFeatureConfigured<?>> iterator = this.getFeatures().get(worldgenstage_decoration).iterator(); iterator.hasNext(); ++j) {
            WorldGenFeatureConfigured<?> worldgenfeatureconfigured = iterator.next();

            seededrandom.b(i, j, worldgenstage_decoration.ordinal());

            try {
                worldgenfeatureconfigured.a(generatoraccess, chunkgenerator, seededrandom, blockposition);
            } catch (Exception exception) {
                CrashReport crashreport = CrashReport.a(exception, "Feature placement");
                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Feature").a("Id", (Object) IRegistry.FEATURE.getKey(worldgenfeatureconfigured.a));
                WorldGenerator<?> worldgenerator = worldgenfeatureconfigured.a;

                worldgenfeatureconfigured.a.getClass();
                crashreportsystemdetails.a("Description", worldgenerator::toString);
                throw new ReportedException(crashreport);
            }
        }
    }
    
    public List<WorldGenFeatureConfigured<?>> e() {
        return this.getFlowerFeatures();
    }
    
    public <C extends WorldGenFeatureConfiguration> void a(StructureGenerator<C> structuregenerator, C c0) {
        this.getValidFeatureStarts().put(structuregenerator, c0);
    }

    public <C extends WorldGenFeatureConfiguration> boolean a(StructureGenerator<C> structuregenerator) {
        return this.getValidFeatureStarts().containsKey(structuregenerator);
    }

    @SuppressWarnings("unchecked")
	@Nullable
    public <C extends WorldGenFeatureConfiguration> C b(StructureGenerator<C> structuregenerator) {
        return (C) this.getValidFeatureStarts().get(structuregenerator); 
    }

}
