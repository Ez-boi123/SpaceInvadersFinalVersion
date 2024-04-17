package invaders.engine;

import invaders.entities.EntityView;
import invaders.entities.Player;
import invaders.factory.EnemyProjectile;
import invaders.factory.EnemyProjectileFactory;
import invaders.factory.PlayerProjectile;
import invaders.factory.Projectile;
import invaders.gameobject.Bunker;
import invaders.gameobject.Enemy;
import invaders.gameobject.GameObject;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.state.BunkerState;
import invaders.state.GreenState;
import invaders.state.RedState;
import invaders.state.YellowState;
import invaders.strategy.FastProjectileStrategy;
import invaders.strategy.NormalProjectileStrategy;
import invaders.strategy.ProjectileStrategy;
import invaders.strategy.SlowProjectileStrategy;
import javafx.scene.image.Image;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Memo {

    private static List<GameObject> newgameObjects = new ArrayList<>();

    private static  List<Renderable> newrenderables =  new ArrayList<>();
    public static boolean isMemo=false;
    static int gameTime =0;
    static int score = 0;
    public static synchronized void save(GameEngine gameEngine) {
        gameTime =gameEngine.getGameTime();
        newrenderables.clear();
        newgameObjects.clear();
        isMemo =true;
        for (int i = 0; i < gameEngine.getRenderables().size(); i++) {
            if (gameEngine.getRenderables().get(i) instanceof Player) {
                Player player = (Player) gameEngine.getRenderables().get(i);
                Player player1 = new Player();
                player1.getPosition().setX(player.getPosition().getX());
                player1.getPosition().setY(player.getPosition().getY());
                player1.setHealth(player.getHealth());
                player1.setVelocity(player.getVelocity());
                newrenderables.add(player1);
            }
            if (gameEngine.getRenderables().get(i) instanceof Bunker) {
               Bunker bunker = (Bunker) gameEngine.getRenderables().get(i);
               Bunker bunker1 =new Bunker();
               bunker1.setImage(new Image(new File("src/main/resources/bunkerGreen.png").toURI().toString()));
               bunker1.setState(bunker.getState());
               bunker1.setPosition(bunker.getPosition());
               bunker1.setLives(bunker.getLives());
               bunker1.setHeight((int) bunker.getHeight());
               bunker1.setWidth((int)bunker.getWidth());
                newrenderables.add(bunker1);

            }
            if (gameEngine.getRenderables().get(i) instanceof Enemy) {
                Enemy enemy = (Enemy) gameEngine.getRenderables().get(i);
                Vector2D position = new Vector2D(enemy.getPosition().getX(),enemy.getPosition().getY());
                Enemy enemy1 =new Enemy(position);
                enemy1.setImage(enemy.getImage());
                EnemyProjectileFactory projectileFactory =new EnemyProjectileFactory();
                ProjectileStrategy projectileStrategy = new SlowProjectileStrategy();
                if(enemy.getProjectileStrategy() instanceof FastProjectileStrategy){
                    projectileStrategy = new FastProjectileStrategy();
                }else if (enemy.getProjectileStrategy() instanceof NormalProjectileStrategy){
                    projectileStrategy = new NormalProjectileStrategy();
                }else if(enemy.getProjectileStrategy() instanceof SlowProjectileStrategy){
                    projectileStrategy =new SlowProjectileStrategy();
                }
                enemy1.setProjectileStrategy(projectileStrategy);

                ArrayList<Projectile> enemyProjectiles =(ArrayList<Projectile>)enemy.getEnemyProjectile().clone();

                enemy1.setEnemyProjectile(enemyProjectiles);
                enemy1.setProjectileImage(enemy.getProjectileImage());
                enemy1.setLives(enemy.getLives());
                enemy1.setEnemyProjectile(enemy.getEnemyProjectile());
                enemy1.setE(enemy.getE());

                enemy1.setProjectileFactory(projectileFactory);
                enemy1.setxVel(enemy.getxVel());
                newrenderables.add(enemy1);
                newgameObjects.add(enemy1);
            }
            if (gameEngine.getRenderables().get(i) instanceof Projectile) {
                Projectile projectile = (Projectile) gameEngine.getRenderables().get(i);
                Projectile projectile1 ;
                if(projectile instanceof EnemyProjectile){
                    Vector2D position = new Vector2D(projectile.getPosition().getX(),projectile.getPosition().getY());
                    projectile1=new EnemyProjectile(position,((EnemyProjectile) projectile).getStrategy(),projectile.getImage());
                }else  {
                    Vector2D position = new Vector2D(projectile.getPosition().getX(),projectile.getPosition().getY());
                    projectile1=new PlayerProjectile(position,((PlayerProjectile) projectile).getStrategy());
                }

                newrenderables.add(projectile1);
                newgameObjects.add(projectile1);
            }
        }
    }

    public static synchronized void load(GameEngine gameEngine) {
        gameEngine.setGameTime(gameTime);
        gameEngine.setScore(score);
        gameEngine.getRenderables().clear();
        gameEngine.getGameObjects().clear();
        for(int i=0;i<newrenderables.size();i++){
            if (newrenderables.get(i) instanceof Player) {
                Player player = (Player) newrenderables.get(i);
                Player player1 = new Player();
                player1.getPosition().setX(player.getPosition().getX());
                player1.getPosition().setY(player.getPosition().getY());
                player1.setHealth(player.getHealth());
                player1.setVelocity(player.getVelocity());
                //player.setImage(new Image(new File("src/main/resources/player.png").toURI().toString(), player.getWidth(), player.getHeight(), true, true));
                gameEngine.getRenderables().add(player1);
                gameEngine.setPlayer(player1);
            }
            if (newrenderables.get(i) instanceof Bunker) {
                Bunker bunker = (Bunker) newrenderables.get(i);
                Bunker bunker1 =new Bunker();
                BunkerState state ;
                if(bunker.getState() instanceof GreenState){
                    state = new GreenState(bunker1);
                    bunker1.setImage(new Image(new File("src/main/resources/bunkerGreen.png").toURI().toString()));
                }else if(bunker.getState() instanceof RedState){
                    state = new RedState(bunker1);
                    bunker1.setImage(new Image(new File("src/main/resources/bunkerRed.png").toURI().toString()));
                }else {
                    state = new YellowState(bunker1);
                    bunker1.setImage(new Image(new File("src/main/resources/bunkerYellow.png").toURI().toString()));
                }
                bunker1.setState(state);
                bunker1.setPosition(bunker.getPosition());
                bunker1.setLives(bunker.getLives());
                bunker1.setHeight((int) bunker.getHeight());
                bunker1.setWidth((int)bunker.getWidth());
                //player.setImage(new Image(new File("src/main/resources/player.png").toURI().toString(), player.getWidth(), player.getHeight(), true, true));
                gameEngine.getRenderables().add(bunker1);

            }
            if (newrenderables.get(i) instanceof Enemy) {
                Enemy enemy = (Enemy) newrenderables.get(i);
                EnemyProjectileFactory projectileFactory =new EnemyProjectileFactory();
                Vector2D position = new Vector2D(enemy.getPosition().getX(),enemy.getPosition().getY());
                Enemy enemy1 =new Enemy(position);
                enemy1.setImage(enemy.getImage());
                enemy1.setProjectileStrategy(enemy.getProjectileStrategy());
                ArrayList<Projectile> enemyProjectiles =(ArrayList<Projectile>)enemy.getEnemyProjectile().clone();
                ArrayList<Projectile> pendingToDeleteEnemyProjectile =new ArrayList<>();
                    for(Projectile p : enemyProjectiles){
                        if(!p.isAlive()){
                            pendingToDeleteEnemyProjectile.add(p);
                        }
                    }
                enemy1.setEnemyProjectile(enemyProjectiles);
                enemy1.setProjectileImage(enemy.getProjectileImage());
                enemy1.setLives(enemy.getLives());
                enemy.getEnemyProjectile().clear();
                enemy1.setEnemyProjectile(enemy.getEnemyProjectile());
                enemy1.setE(enemy.getE());
                enemy1.setPendingToDeleteEnemyProjectile(pendingToDeleteEnemyProjectile);
                enemy1.setProjectileFactory(projectileFactory);
                enemy1.setxVel(enemy.getxVel());
                gameEngine.getRenderables().add(enemy1);
                gameEngine.getGameObjects().add(enemy1);
            }
            if (newrenderables.get(i) instanceof Projectile) {
                Projectile projectile = (Projectile) newrenderables.get(i);
                Projectile projectile1 ;
                if(projectile instanceof EnemyProjectile){
                    Vector2D position = new Vector2D(projectile.getPosition().getX(),projectile.getPosition().getY());

                    ProjectileStrategy projectileStrategy = new SlowProjectileStrategy();
                    if(((EnemyProjectile) projectile).getStrategy() instanceof FastProjectileStrategy){
                        projectileStrategy = new FastProjectileStrategy();
                    }else if (((EnemyProjectile) projectile).getStrategy() instanceof NormalProjectileStrategy){
                        projectileStrategy = new NormalProjectileStrategy();
                    }else if(((EnemyProjectile) projectile).getStrategy() instanceof SlowProjectileStrategy){
                        projectileStrategy =new SlowProjectileStrategy();
                    }
                    projectile1=new EnemyProjectile(position,projectileStrategy,projectile.getImage());
                    gameEngine.getGameObjects().add(projectile1);
                }else  {
                    Vector2D position = new Vector2D(projectile.getPosition().getX(),projectile.getPosition().getY());
                    projectile1=new PlayerProjectile(position,((PlayerProjectile) projectile).getStrategy());
                    gameEngine.getGameObjects().add(projectile1);
                }

                //player.setImage(new Image(new File("src/main/resources/player.png").toURI().toString(), player.getWidth(), player.getHeight(), true, true));
                gameEngine.getRenderables().add(projectile1);
            }
        }

        for (EntityView entityView : GameWindow.entityViews) {
            GameWindow.pane.getChildren().remove(entityView.getNode());
        }
    }

}
