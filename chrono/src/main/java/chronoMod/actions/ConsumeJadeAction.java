package chronoMod.actions;

import chronoMod.powers.ArcaneBlessingPower;
import chronoMod.powers.JadePower;
import chronoMod.relics.OldWine;
import chronoMod.relics.RubyAmulet;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ConsumeJadeAction extends AbstractGameAction {
    private AbstractPlayer p;

    public ConsumeJadeAction(AbstractPlayer player, int numJade) {
        this.p = player;
        this.amount = numJade;
    }

    public void update() {
        AbstractPower jade = p.getPower(JadePower.POWER_ID);
        if (jade != null) {
            this.addToBot(new LoseEnergyAction(jade.amount));
            this.addToBot(new ReducePowerAction(this.p, this.p, jade, this.amount));
            if (jade.amount == 0) {
                this.addToBot(new RemoveSpecificPowerAction(this.p, this.p, jade));
            }
        }

        // Arcane Blessing
        if (this.p.hasPower(ArcaneBlessingPower.POWER_ID)) {
            this.p.getPower(ArcaneBlessingPower.POWER_ID).onSpecificTrigger();
        }

        // Ruby Amulet
        AbstractRelic rubyAmulet = this.p.getRelic(RubyAmulet.ID);
        if (rubyAmulet != null) {
            rubyAmulet.onTrigger();
        }

        // Old Wine
        AbstractRelic oldWine = this.p.getRelic(OldWine.ID);
        if (oldWine != null) {
            oldWine.onTrigger();
        }

        this.isDone = true;
    }
}
