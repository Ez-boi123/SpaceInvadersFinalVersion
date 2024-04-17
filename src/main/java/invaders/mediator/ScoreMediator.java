package invaders.mediator;

import java.util.ArrayList;
import java.util.List;

public class ScoreMediator {
    private List<ScoreParticipant> observers = new ArrayList<>();

    public void attach(ScoreParticipant observer){
        observers.add(observer);
    }
    public void remove(ScoreParticipant observer) {observers.remove(observer);}

    public void notifyAllObservers(){
        for (ScoreParticipant observer : observers) {
            observer.update();
        }
    }
}
