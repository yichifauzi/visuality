package visuality.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import visuality.VisualityMod;
import visuality.registry.VisualityParticles;
import visuality.util.ParticleUtils;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntity implements Monster {
	@Shadow
	@Final
	private static TrackedData<Integer> SLIME_SIZE;

	protected SlimeEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	@Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
	void addParticle(World world, ParticleEffect particle, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		if(world.isClient && this.getType().equals(EntityType.SLIME) && VisualityMod.config.slimeEnabled) {
			spawnSlimeParticle(x, y, z);
		}
		else {
			this.getWorld().addParticle(particle, x, y, z, velocityX, velocityY, velocityZ);
		}
	}

	@Unique
	private void spawnSlimeParticle(double x, double y, double z) {
		if(getDataTracker().get(SLIME_SIZE) == 1) {
			ParticleUtils.add(this.getWorld(), VisualityParticles.SMALL_SLIME_BLOB, x, y, z, VisualityMod.config.slimeColor.getRgb(), 1.0D);
		}
		else if(getDataTracker().get(SLIME_SIZE) == 2) {
			ParticleUtils.add(this.getWorld(), VisualityParticles.MEDIUM_SLIME_BLOB, x, y, z, VisualityMod.config.slimeColor.getRgb(), 1.0D);
		}
		else {
			ParticleUtils.add(this.getWorld(), VisualityParticles.BIG_SLIME_BLOB, x, y, z, VisualityMod.config.slimeColor.getRgb(), 2.0D);
		}
	}

}
