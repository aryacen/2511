package dungeonmania.Entities.Item;

import dungeonmania.Entities.Entity;

public interface Item {
    public abstract void collectItem(Entity entity);
    public abstract void buildItem(Entity entity);
}
