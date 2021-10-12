package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NightmarePower extends AbstractPower {
    public static final String POWER_ID = ChronoMod.makeID("Nightmare");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int hp_lost;

    public NightmarePower(AbstractCreature owner, int numTurns, int amt) {
        this.name = NAME;
        this.ID = POWER_ID + amt;
        this.owner = owner;
        this.amount = numTurns;
        this.hp_lost = amt;
        this.isTurnBased = true;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        this.updateDescription();
        this.loadRegion("nightmare");
        this.type = PowerType.DEBUFF;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new LoseHPAction(this.owner, (AbstractCreature)null, this.hp_lost,
                AbstractGameAction.AttackEffect.POISON));
        if (this.amount <= 1) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.hp_lost + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    public AbstractPower makeCopy() {
        return new NightmarePower(this.owner, this.amount, this.hp_lost);
    }
}
