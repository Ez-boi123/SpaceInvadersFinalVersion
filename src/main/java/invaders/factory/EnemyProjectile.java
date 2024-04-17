package invaders.factory;

import invaders.engine.GameEngine;
import invaders.physics.Collider;
import invaders.physics.Hostile;
import invaders.physics.Scoreable;
import invaders.physics.Vector2D;
import invaders.strategy.FastProjectileStrategy;
import invaders.strategy.ProjectileStrategy;
import invaders.strategy.SlowProjectileStrategy;
import javafx.scene.image.Image;

public class EnemyProjectile extends Projectile implements Scoreable, Hostile, Cloneable {
    public ProjectileStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ProjectileStrategy strategy) {
        this.strategy = strategy;
    }

    private ProjectileStrategy strategy;

    public EnemyProjectile(Vector2D position, ProjectileStrategy strategy, Image image) {
        super(position,image);
        this.strategy = strategy;
    }

    @Override
    public void update(GameEngine model) {
        strategy.update(this);

        if(this.getPosition().getY()>= model.getGameHeight() - this.getImage().getHeight()){
            this.takeDamage(1);
        }
    }



    @Override
    public String getRenderableObjectName() {
        return "EnemyProjectile";
    }

    @Override
    public int getScore() {
        if (strategy instanceof FastProjectileStrategy) {
            return 2;
        } else if (strategy instanceof SlowProjectileStrategy) {
            return 1;
        } else {
            throw new RuntimeException("Unknown projectile strategy");
        }
    }
    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
