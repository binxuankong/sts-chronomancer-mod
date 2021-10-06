package chronoMod.actions;

import chronoMod.powers.JadePower;
import chronoMod.powers.WillpowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BigBangAction extends AbstractGameAction {
    private AbstractPlayer p;

    public BigBangAction() {
        this.p = AbstractDungeon.player;
    }

    public void update() {
        int jadeAmount = 0;
        AbstractPower jade = this.p.getPower(JadePower.POWER_ID);
        if (jade != null) {
            jadeAmount += jade.amount;
        }
        int energyAmount = jadeAmount;

        // Willpower
        if (!this.p.hasPower(WillpowerPower.POWER_ID)) {
            energyAmount *= 2;
        }

        this.addToBot(new GainJadeAction(jadeAmount));
        this.addToBot(new GainEnergyAction(energyAmount));

        this.isDone = true;
    }
}
