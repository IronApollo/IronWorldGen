package me.ironapollo.implementations.v1_14_R1;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import net.minecraft.server.v1_14_R1.AxisAlignedBB;
import net.minecraft.server.v1_14_R1.BiomeBase;
import net.minecraft.server.v1_14_R1.Block;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.ChunkStatus;
import net.minecraft.server.v1_14_R1.DifficultyDamageScaler;
import net.minecraft.server.v1_14_R1.Entity;
import net.minecraft.server.v1_14_R1.EntityHuman;
import net.minecraft.server.v1_14_R1.EnumSkyBlock;
import net.minecraft.server.v1_14_R1.Fluid;
import net.minecraft.server.v1_14_R1.FluidType;
import net.minecraft.server.v1_14_R1.GeneratorAccess;
import net.minecraft.server.v1_14_R1.HeightMap.Type;
import net.minecraft.server.v1_14_R1.IBlockData;
import net.minecraft.server.v1_14_R1.IChunkAccess;
import net.minecraft.server.v1_14_R1.IChunkProvider;
import net.minecraft.server.v1_14_R1.ParticleParam;
import net.minecraft.server.v1_14_R1.SoundCategory;
import net.minecraft.server.v1_14_R1.SoundEffect;
import net.minecraft.server.v1_14_R1.TickList;
import net.minecraft.server.v1_14_R1.TileEntity;
import net.minecraft.server.v1_14_R1.World;
import net.minecraft.server.v1_14_R1.WorldBorder;
import net.minecraft.server.v1_14_R1.WorldData;
import net.minecraft.server.v1_14_R1.WorldProvider;

public class DummyGeneratorAccess implements GeneratorAccess{
	
	public static GeneratorAccess INSTANCE = new DummyGeneratorAccess();
	
	private DummyGeneratorAccess() {}

	public <T extends Entity> List<T> a(Class<? extends T> arg0, AxisAlignedBB arg1, Predicate<? super T> arg2) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public List<Entity> getEntities(Entity arg0, AxisAlignedBB arg1, Predicate<? super Entity> arg2) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public List<? extends EntityHuman> getPlayers() {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public int a(Type arg0, int arg1, int arg2) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public int c() {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public boolean e() {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public IChunkAccess getChunkAt(int arg0, int arg1, ChunkStatus arg2, boolean arg3) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public BlockPosition getHighestBlockYAt(Type arg0, BlockPosition arg1) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public int getLightLevel(BlockPosition arg0, int arg1) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public int getSeaLevel() {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public WorldBorder getWorldBorder() {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public WorldProvider getWorldProvider() {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public BiomeBase getBiome(BlockPosition arg0) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public int getBrightness(EnumSkyBlock arg0, BlockPosition arg1) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public Fluid getFluid(BlockPosition arg0) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public TileEntity getTileEntity(BlockPosition arg0) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public IBlockData getType(BlockPosition arg0) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public boolean a(BlockPosition arg0, Predicate<IBlockData> arg1) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public boolean a(BlockPosition arg0, boolean arg1) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public boolean b(BlockPosition arg0, boolean arg1) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public boolean setTypeAndData(BlockPosition arg0, IBlockData arg1, int arg2) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public void a(EntityHuman arg0, int arg1, BlockPosition arg2, int arg3) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public void addParticle(ParticleParam arg0, double arg1, double arg2, double arg3, double arg4, double arg5,
			double arg6) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public TickList<Block> getBlockTickList() {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public IChunkProvider getChunkProvider() {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public DifficultyDamageScaler getDamageScaler(BlockPosition arg0) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public TickList<FluidType> getFluidTickList() {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public World getMinecraftWorld() {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public Random getRandom() {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public long getSeed() {
		return 0;
	}

	public WorldData getWorldData() {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public void playSound(EntityHuman arg0, BlockPosition arg1, SoundEffect arg2, SoundCategory arg3, float arg4,
			float arg5) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	public void update(BlockPosition arg0, Block arg1) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	@Override
	public IChunkAccess getChunkIfLoadedImmediately(int arg0, int arg1) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	@Override
	public Fluid getFluidIfLoaded(BlockPosition arg0) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

	@Override
	public IBlockData getTypeIfLoaded(BlockPosition arg0) {
		throw new UnsupportedOperationException("DummyGeneratorAccess can not perform this operation!");
	}

}
