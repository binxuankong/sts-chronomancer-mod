package chronoMod.powers;

import chronoMod.patches.AbstractPowerEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Iterator;

public abstract class RecallPower extends AbstractPower {
    public RecallPower(AbstractCreature owner) {
        super();
        this.owner = owner;
        this.type = AbstractPowerEnum.RECALL;
    }

    public void recallEffect() {}

    public void triggerRecall() {
        this.flash();
        this.recallEffect();

        // Tides of Time
        AbstractPower tidesOfTime = this.owner.getPower(TidesOfTimePower.POWER_ID);
        if (tidesOfTime != null) {
            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while (var3.hasNext()) {
                AbstractMonster mo = (AbstractMonster)var3.next();
                this.addToBot(new ApplyPowerAction(mo, this.owner, new WeakPower(mo, tidesOfTime.amount, false),
                        tidesOfTime.amount, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }
}
