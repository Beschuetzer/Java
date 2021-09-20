package major.adam;

import java.util.List;

public class Monster implements ISaveable{
    private int hp;
    private int mana;
    private int damage;
    private String weapon;

    public Monster(int hp, int mana, int damage, String weapon) {
        this.hp = hp;
        this.mana = mana;
        this.weapon = weapon;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "hp=" + hp +
                ", mana=" + mana +
                ", damage=" + damage +
                ", weapon='" + weapon + '\'' +
                '}';
    }

    @Override
    public List<String> save() {
        return null;
    }

    @Override
    public boolean load(List<String> valuesToLoad) {
        return false;
    }

}
