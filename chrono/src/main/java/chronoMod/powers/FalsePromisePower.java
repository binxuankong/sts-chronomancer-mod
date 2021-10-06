package chronoMod.powers;

import chronoMod.ChronoMod;
import chronoMod.actions.GainJadeAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FalsePromisePower extends AbstractPower {
    public static final String POWER_ID = ChronoMod.makeID("FalsePromise");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int CARD_AMT = 4;
    private int drawEnergyAmount;

    public FalsePromisePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = CARD_AMT;
        this.drawEnergyAmount = amount;
        this.updateDescription();
        this.loadRegion("panache");
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.drawEnergyAmount += stackAmount;
        this.updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.amount--;
        if (this.amount == 0) {
            this.flash();
            this.amount = CARD_AMT;
            this.addToBot(new GainEnergyAction(this.drawEnergyAmount));
            this.addToBot(new DrawCardAction(this.owner, this.drawEnergyAmount));
            this.addToBot(new GainJadeAction(this.drawEnergyAmount));
        }
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.amount = CARD_AMT;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
        if (this.drawEnergyAmount == 1) {
            this.description = this.description + this.drawEnergyAmount + DESCRIPTIONS[3] + this.drawEnergyAmount +
                    DESCRIPTIONS[4] + this.drawEnergyAmount + DESCRIPTIONS[5];
        } else {
            this.description = this.description + this.drawEnergyAmount + DESCRIPTIONS[3] + this.drawEnergyAmount +
                    DESCRIPTIONS[4] + this.drawEnergyAmount + DESCRIPTIONS[6];
        }
    }

    public AbstractPower makeCopy() {
        return new FalsePromisePower(this.owner, this.amount);
    }
}
