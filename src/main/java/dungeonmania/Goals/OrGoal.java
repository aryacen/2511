package dungeonmania.Goals;

import java.util.ArrayList;

import dungeonmania.Entities.MovingEntities.PlayerEntity;
import dungeonmania.Entities.StaticEntities.StaticEntity;

public class OrGoal implements Goal {

    private Goal g1;
    private Goal g2;

    ArrayList<Goal> children = new ArrayList<Goal>();

    public OrGoal(Goal g1, Goal g2, ArrayList<StaticEntity> staticEntities, PlayerEntity playerEntity) {
        this.g1 = g1;
        this.g2 = g2;
        this.children.add(g1);
        this.children.add(g2);
    }

    @Override
    public String getGoal() {
        if (achievedGoal()) {
            return "";
        }
        return "(" + g1.getGoal() + " OR " + g2.getGoal() + ")";
    }

    @Override
    public boolean achievedGoal() {
        return g1.achievedGoal() || g2.achievedGoal();
    }

    public ArrayList<Goal> getChildren() {
        return children;
    }

    public boolean add(Goal child) {
        return children.add(child);
    }

    public boolean remove(Goal child) {
        return children.remove(child);
    }

    @Override
    public boolean setAchievedGoal(ArrayList<StaticEntity> staticEntities, PlayerEntity playerEntity) {
        return false;
    }
    
}
