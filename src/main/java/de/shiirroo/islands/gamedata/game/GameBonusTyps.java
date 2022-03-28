package de.shiirroo.islands.gamedata.game;

public enum GameBonusTyps {
    Money(100, "%"),
    Xp(100, "%"),
    Damage(100, "%"),
    DamageReduction(100, "%"),
    UpdateRate(100, "%"),
    Health(1 ,"");

    private final int deduction;
    private final String addon;

    GameBonusTyps(int deduction, String addon){
        this.deduction = deduction;
        this.addon = addon;
    }

    public String getAddon() {
        return addon;
    }

    public int getDeduction() {
        return deduction;
    }
}
