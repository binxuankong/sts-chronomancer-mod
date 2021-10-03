package chronoMod.powers;

import chronoMod.DefaultMod;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import chronoMod.patches.AbstractPowerEnum;

public class RecallEnergyPower extends AbstractPower {
    public static final String POWER_ID = DefaultMod.makeID("RecallEnergy");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public RecallEnergyPower(AbstractCreature owner, int energyAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = energyAmt;
        this.type = AbstractPowerEnum.RECALL;
        this.updateDescription();
        this.loadRegion("energized_blue");
    }

    @Override
    public void onEnergyRecharge() {
        this.flash();
        this.addToBot(new GainEnergyAction(this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new RecallEnergyPower(this.owner, this.amount);
    }
}
