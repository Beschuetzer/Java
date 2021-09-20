package major.adam;

import java.util.ArrayList;
import java.util.List;

public class Player implements ISaveable {
    private int hp;
    private int mana;
    private int damage;
    private String weapon;

    public Player(int hp, int mana, int damage, String weapon) {
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
        return "Player{" +
                "hp=" + hp +
                ", mana=" + mana +
                ", damage=" + damage +
                ", weapon='" + weapon + '\'' +
                '}';
    }

    @Override
    public List<String> save() {
        List<String> values = new ArrayList<String>();
        values.add(0, Integer.toString(this.hp));
        values.add(1, Integer.toString(this.mana));
        values.add(2, Integer.toString(this.damage));
        values.add(3, this.weapon);

        return values;
    }

    @Override
    public boolean load(List<String> valuesToLoad) {
        if (valuesToLoad == null || valuesToLoad.size() <= 0) return false;

        this.hp = Integer.parseInt(valuesToLoad.get(1));
        this.mana = Integer.parseInt(valuesToLoad.get(2));
        this.damage = Integer.parseInt(valuesToLoad.get(3));
        this.weapon = valuesToLoad.get(4);
        return true;
    }

}
