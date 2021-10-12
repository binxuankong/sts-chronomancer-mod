package chronoMod.powers;

import chronoMod.ChronoMod;
import chronoMod.actions.ConsumeJadeAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class JadePower extends AbstractPower {
    public static final String POWER_ID = ChronoMod.makeID("Jade");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public JadePower(AbstractCreature owner, int jadeAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = jadeAmt;
        this.priority = 1;
        this.updateDescription();
        this.loadRegion("reactive");
    }

    @Override
    public void onEnergyRecharge() {
        this.flash();
        ChronoMod.logger.info("Trigger Consume Jade effect.");
        this.addToBot(new LoseEnergyAction(this.amount));
        this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        this.addToBot(new ConsumeJadeAction());
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new JadePower(this.owner, this.amount);
    }
}
