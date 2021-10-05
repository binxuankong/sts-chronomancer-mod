package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Iterator;

public class TidesOfTimePower extends AbstractPower {
    public static final String POWER_ID = ChronoMod.makeID("TidesOfTime");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TidesOfTimePower(AbstractCreature owner, int numWeak) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = numWeak;
        this.priority = 0;
        this.updateDescription();
        this.loadRegion("wave_of_the_hand");
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && this.priority > 0) {
            this.flash();
            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while (var3.hasNext()) {
                AbstractMonster mo = (AbstractMonster)var3.next();
                this.addToBot(new ApplyPowerAction(mo, this.owner, new WeakPower(mo, this.amount, false),
                        this.amount, true, AbstractGameAction.AttackEffect.NONE));
            }
            this.priority = 0;
        }
    }

    @Override
    public void atEndOfRound() {
        this.priority = 0;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new TidesOfTimePower(this.owner, this.amount);
    }
}
