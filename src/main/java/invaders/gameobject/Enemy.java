package invaders.gameobject;

import invaders.engine.GameEngine;
import invaders.factory.EnemyProjectileFactory;
import invaders.factory.Projectile;
import invaders.factory.ProjectileFactory;
import invaders.mediator.ScoreUpdater;
import invaders.physics.Hostile;
import invaders.physics.Scoreable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.strategy.FastProjectileStrategy;
import invaders.strategy.ProjectileStrategy;
import invaders.strategy.SlowProjectileStrategy;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class Enemy implements GameObject, Renderable, Scoreable, Hostile {
    private Vector2D position;
    private int lives = 1;
    private Image image;
    private int xVel = -1;

    private ArrayList<Projectile> enemyProjectile;
    private ArrayList<Projectile> pendingToDeleteEnemyProjectile;
    private ProjectileStrategy projectileStrategy;
    private ProjectileFactory projectileFactory;
    private Image projectileImage;
    private Random random = new Random();
    private GameEngine e;

    public int getLives() {
        return lives;
    }

    public Random getRandom() {
        return random;
    }

    public int getxVel() {
        return xVel;
    }

    public void setxVel(int xVel) {
        this.xVel = xVel;
    }

    public ArrayList<Projectile> getEnemyProjectile() {
        return enemyProjectile;
    }

    public void setEnemyProjectile(ArrayList<Projectile> enemyProjectile) {
        this.enemyProjectile = enemyProjectile;
    }

    public ArrayList<Projectile> getPendingToDeleteEnemyProjectile() {
        return pendingToDeleteEnemyProjectile;
    }

    public void setPendingToDeleteEnemyProjectile(ArrayList<Projectile> pendingToDeleteEnemyProjectile) {
        this.pendingToDeleteEnemyProjectile = pendingToDeleteEnemyProjectile;
    }

    public ProjectileStrategy getProjectileStrategy() {
        return projectileStrategy;
    }

    public ProjectileFactory getProjectileFactory() {
        return projectileFactory;
    }

    public void setProjectileFactory(ProjectileFactory projectileFactory) {
        this.projectileFactory = projectileFactory;
    }

    public Image getProjectileImage() {
        return projectileImage;
    }

    public GameEngine getE() {
        return e;
    }

    public void setE(GameEngine e) {
        this.e = e;
    }

    public Enemy(Vector2D position) {
        this.position = position;
        this.projectileFactory = new EnemyProjectileFactory();
        this.enemyProjectile = new ArrayList<>();
        this.pendingToDeleteEnemyProjectile = new ArrayList<>();
    }

    @Override
    public void start() {
        ScoreUpdater fastScoreObserver = new ScoreUpdater(e,4);
        ScoreUpdater slowScoreObserver = new ScoreUpdater(e,3);

        e.getSubject().attach(fastScoreObserver);
        e.getSubject().attach(slowScoreObserver);
    }

    @Override
    public void update(GameEngine engine) {
        if (enemyProjectile.size()<3) {
            if(this.isAlive() &&  random.nextInt(120)==20){
                Projectile p = projectileFactory.createProjectile(new Vector2D(position.getX() + this.image.getWidth() / 2, position.getY() + image.getHeight() + 2),projectileStrategy, projectileImage);
                enemyProjectile.add(p);
                engine.getPendingToAddGameObject().add(p);
                engine.getPendingToAddRenderable().add(p);
            }
        }else{
            pendingToDeleteEnemyProjectile.clear();
            for(Projectile p : enemyProjectile){
                if(!p.isAlive()){
                    engine.getPendingToRemoveGameObject().add(p);
                    engine.getPendingToRemoveRenderable().add(p);
                    pendingToDeleteEnemyProjectile.add(p);
                }
            }

            for(Projectile p: pendingToDeleteEnemyProjectile){
                enemyProjectile.remove(p);
            }
        }

        if(this.position.getX()<=this.image.getWidth() || this.position.getX()>=(engine.getGameWidth()-this.image.getWidth()-1)){
            this.position.setY(this.position.getY()+25);
            xVel*=-1;
        }

        this.position.setX(this.position.getX() + xVel);

        if((this.position.getY()+this.image.getHeight())>=engine.getPlayer().getPosition().getY()){
            engine.getPlayer().takeDamage(Integer.MAX_VALUE);
        }

        /*
        Logic TBD
         */
        e = engine;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return this.image.getWidth();
    }

    @Override
    public double getHeight() {
       return this.image.getHeight();
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setProjectileImage(Image projectileImage) {
        this.projectileImage = projectileImage;
    }

    @Override
    public void takeDamage(double amount) {
        this.lives-=1;
    }

    @Override
    public double getHealth() {
        return this.lives;
    }

    @Override
    public String getRenderableObjectName() {
        return "Enemy";
    }

    @Override
    public boolean isAlive() {
        return this.lives>0;
    }

    public void setProjectileStrategy(ProjectileStrategy projectileStrategy) {
        this.projectileStrategy = projectileStrategy;
    }

    @Override
    public int getScore() {
        if (projectileStrategy instanceof FastProjectileStrategy) {
            return 4;
        } else if (projectileStrategy instanceof SlowProjectileStrategy) {
            return 3;
        } else {
            throw new RuntimeException("Unknown projectile strategy");
        }
    }
}
