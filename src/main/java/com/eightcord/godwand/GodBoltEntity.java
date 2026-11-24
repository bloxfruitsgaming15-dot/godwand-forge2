// GodBoltEntity — thrown projectile, detonates on entity or block impact
// 8.0f explosion power, Level.ExplosionInteraction.TNT = block destruction on
package com.eightcord.godwand;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

public class GodBoltEntity extends ThrowableItemProjectile {
    private static final float EXPLOSION_POWER = 8.0f;
    private static final float DIRECT_DAMAGE = 40.0f;

    public GodBoltEntity(EntityType<? extends GodBoltEntity> type, Level level) {
        super(type, level);
    }

    public GodBoltEntity(Level level, LivingEntity owner) {
        super(GodWandMod.GOD_BOLT.get(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return GodWandMod.GOD_WAND.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult hit) {
        super.onHitEntity(hit);
        if (!(this.level instanceof ServerLevel)) return;
        hit.getEntity().hurt(damageSources().magic(), DIRECT_DAMAGE);
        detonate(hit.getLocation());
    }

    @Override
    protected void onHitBlock(BlockHitResult hit) {
        super.onHitBlock(hit);
        if (!(this.level instanceof ServerLevel)) return;
        detonate(hit.getLocation());
    }

    private void detonate(Vec3 pos) {
        ServerLevel serverLevel = (ServerLevel) this.level;

        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
        if (bolt != null) {
            bolt.moveTo(pos.x, pos.y, pos.z);
            serverLevel.addFreshEntity(bolt);
        }

        serverLevel.explode(getOwner(), pos.x, pos.y, pos.z, EXPLOSION_POWER,
                Level.ExplosionInteraction.TNT);

        discard();
    }
}
