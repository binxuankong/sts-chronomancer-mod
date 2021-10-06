package chronoMod.actions;

import chronoMod.powers.JadePower;
import chronoMod.powers.WillpowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GainJadeAction extends AbstractGameAction {
    private AbstractPlayer p;

    public GainJadeAction( int numJade) {
        this.p = AbstractDungeon.player;
        this.amount = numJade;
    }

    public void update() {
        // Willpower
        AbstractPower willpower = this.p.getPower(WillpowerPower.POWER_ID);
        if (willpower != null) {
            this.addToBot(new ReducePowerAction(this.p, this.p, willpower, 1));
        } else {
            this.addToBot(new ApplyPowerAction(this.p, this.p, new JadePower(this.p, this.amount), this.amount));
        }
        this.isDone = true;
    }
}
