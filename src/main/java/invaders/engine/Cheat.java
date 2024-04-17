package invaders.engine;

import invaders.factory.EnemyProjectile;
import invaders.factory.PlayerProjectile;
import invaders.factory.Projectile;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.strategy.FastProjectileStrategy;
import invaders.strategy.NormalProjectileStrategy;
import invaders.strategy.ProjectileStrategy;
import invaders.strategy.SlowProjectileStrategy;

import java.util.ArrayList;
import java.util.List;

public class Cheat {
    public  static void playercheat(GameEngine gameEngine){
        int addscore =0;
        for (int i = 0; i < gameEngine.getRenderables().size(); i++) {
            if (gameEngine.getRenderables().get(i) instanceof Projectile) {
                Projectile projectile = (Projectile) gameEngine.getRenderables().get(i);
                Projectile projectile1;

                if (projectile instanceof EnemyProjectile) {

                    if (((EnemyProjectile) projectile).getStrategy() instanceof FastProjectileStrategy) {
                          addscore+=2;
                        projectile.setLives(0);
                    }
                }

                //player.setImage(new Image(new File("src/main/resources/player.png").toURI().toString(), player.getWidth(), player.getHeight(), true, true));
            }
        }

        gameEngine.addScore(addscore);
    }
    public  static void playercheat2(GameEngine gameEngine){
        int addscore =0;
        for (int i = 0; i < gameEngine.getRenderables().size(); i++) {
            if (gameEngine.getRenderables().get(i) instanceof Projectile) {
                Projectile projectile = (Projectile) gameEngine.getRenderables().get(i);
                Projectile projectile1;
                if (projectile instanceof EnemyProjectile) {


                    if (((EnemyProjectile) projectile).getStrategy() instanceof SlowProjectileStrategy) {
                        addscore+=1;
                        projectile.setLives(0);
                    }
                }

                //player.setImage(new Image(new File("src/main/resources/player.png").toURI().toString(), player.getWidth(), player.getHeight(), true, true));
            }
        }

        gameEngine.addScore(addscore);
    }
}
